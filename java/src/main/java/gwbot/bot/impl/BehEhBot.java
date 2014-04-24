package gwbot.bot.impl;

import gwbot.Main;
import gwbot.bot.GenericBot;
import gwbot.car.Car;
import gwbot.message.CarPositionMessage;
import gwbot.message.GameEndMessage;
import gwbot.message.GameInitMessage;
import gwbot.message.GameStartMessage;
import gwbot.message.JoinMessage;
import gwbot.message.PingMessage;
import gwbot.message.ThrottleMessage;
import gwbot.message.TurboAvailableMessage;
import gwbot.message.YourCarMessage;
import gwbot.race.Race;
import gwbot.track.Piece;
import gwbot.track.Track;

import java.util.List;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class BehEhBot extends GenericBot {

	private Track track;

	public BehEhBot() {
	}

	public BehEhBot(Main main) {
		super(main);
	}

	private Car ownCar;

	@Override
	public void onYourCarMessage(YourCarMessage yourCarMessage) {
		ownCar = yourCarMessage.getCar();
	}

	@Override
	public void onGameInitMessage(GameInitMessage gameInitMessage) {
		Race race = gameInitMessage.getRace();
		this.track = race.getTrack();
	}

	boolean gameRunning = false;

	@Override
	public void onGameStartMessage(GameStartMessage gameStartMessage) {
		gameRunning = true;
	}

	@Override
	public void onGameEndMessage(GameEndMessage gameEndMessage) {
		gameRunning = false;
	}

	@Override
	public void onJoinMessage(JoinMessage joinMessage) {
	}

	private double lastProgression;
	private double speed;
	private double lastSpeed;
	private double lastAngle;
	private boolean switched = false;

	double angle1;
	double angle2;
	double angle3;
	double curveTick = 0;

	@Override
	public void onCarPositions(List<CarPositionMessage> carPositionMessages) {
		// ignore if no game is running
		if (!gameRunning) {
			send(new PingMessage());
			return;
		}

		// find our position message
		CarPositionMessage ownPositionMessage = null;
		for (CarPositionMessage carPositionMessage : carPositionMessages) {
			if (ownCar.equals(carPositionMessage.getCar())) {
				ownPositionMessage = carPositionMessage;
			}
		}
		if (ownPositionMessage == null) {
			System.out.println("could not find own car position message");
			return;
		}
		Piece currentPiece = track.getPiece(ownPositionMessage.getPieceIndex());

		double throttle = 1.1d;

		if (currentPiece.isCurve()) {
			double absAngle = Math.abs(ownPositionMessage.getAngle());
			if (curveTick == 0) {
				if (absAngle > 0) {
					// start curve angle calculation
					curveTick++;
					System.out.println("Entered curve with angle " + absAngle);
					angle1 = absAngle;
				}
			} else {
				curveTick++;
				if (curveTick == 2) {
					System.out.println("Captured 2. tick with angle " + absAngle);
					angle2 = absAngle;
				}
				if (curveTick == 3) {
					angle3 = absAngle;
					System.out.println("Captured 3. tick with angle " + absAngle);
					double diff1 = angle2 - angle1;
					double diff2 = angle3 - angle2;
					double diff3 = diff1 - diff2;
					System.out.println("differences are " + diff1 + " and " + diff2 + " (changed by -" + diff3 + ")");
					System.out.println("Regressing in 100 steps");
					double angle = angle1;
					double difference = diff1;
					for (int i = 0; i <= 100; i++) {
						angle += difference;
						difference -= diff3;
					}
					System.out.println("Concluded at angle " + angle);
					lastAngle = absAngle;
				}
				if (curveTick > 3) {
					System.out.println("Captured " + curveTick + ". tick with angle " + absAngle + " (" + (absAngle - lastAngle) +")");
					lastAngle = absAngle;
				}
			}
		} else {
			if (curveTick != 0) {
				curveTick = 0;
				angle1 = 0;
				angle2 = 0;
				angle3 = 0;
			}
		}

		send(new ThrottleMessage(throttle));
	}

	private boolean turboAvailable = false;

	@Override
	public void onTurboAvailable(TurboAvailableMessage turboAvailableMessage) {
		turboAvailable = true;
	}
}
