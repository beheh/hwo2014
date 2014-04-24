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
import gwbot.message.SwitchLaneMessage;
import gwbot.message.ThrottleMessage;
import gwbot.message.TurboAvailableMessage;
import gwbot.message.YourCarMessage;
import gwbot.race.Race;
import gwbot.track.Piece;
import java.util.List;

/**
 *
 * @author Team Goldwipf <goldwipf@beheh.de>
 */
public class GoldwipfBot extends GenericBot {

	public GoldwipfBot() {
	}

	public GoldwipfBot(Main main) {
		super(main);
	}

	private Race race;

	@Override
	public void onGameInitMessage(GameInitMessage gameInitMessage) {
		race = gameInitMessage.getRace();
	}

	private Car ownCar = null;

	@Override
	public void onYourCarMessage(YourCarMessage yourCarMessage) {
		ownCar = yourCarMessage.getCar();
	}

	private boolean gameRunning = false;

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

	private TurboAvailableMessage turboAvailableMessage = null;

	@Override
	public void onTurboAvailable(TurboAvailableMessage turboAvailableMessage) {
		this.turboAvailableMessage = turboAvailableMessage;
	}

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

		// update current speed, friction coefficient
		// check if we should switch lanes for minimum distance
		if (checkForSwitch(race.getTrack().getPiece(ownPositionMessage.getPieceIndex()))) {
			System.out.println("Switched lane.");
			return;
		}

		// check for turbo
		// with other cars:
		// check if car is just ahead (look for next switch to overtake, or use it to break next curve)
		// check for car approaching faster from behind (brake in advance)
		// do actual speed calculation, braking in curve, ABS for drifting...
		send(new ThrottleMessage(0.4));
	}

	private Piece sentFor = null;

	private boolean checkForSwitch(Piece currentPiece) {

		Piece nextPiece = currentPiece.next();
		if (nextPiece != sentFor && nextPiece.isSwitch()) {
			double angToNextSwitch = 0;
			// add all curve angles until next switch
			Piece piece = nextPiece.next();
			while (!piece.isSwitch()) {
				angToNextSwitch += piece.getAngle();
				piece = piece.next();
			}
			// if next switch is also a curve, add half of the angle
			if (nextPiece.isCurve()) {
				angToNextSwitch += (nextPiece.getAngle() / 2);
			}
			// if necessary, send the switch
			if (angToNextSwitch == 0) {
				return false;
			}
			if (angToNextSwitch > 0) {
				send(new SwitchLaneMessage(SwitchLaneMessage.Direction.RIGHT));
			} else {
				send(new SwitchLaneMessage(SwitchLaneMessage.Direction.LEFT));
			}
			sentFor = nextPiece;
			return true;
		}
		return false;
	}
}
