package gwbot.bot.impl;

import gwbot.Main;
import gwbot.bot.GenericBot;
import gwbot.message.CarPositionMessage;
import gwbot.message.GameEndMessage;
import gwbot.message.GameInitMessage;
import gwbot.message.GameStartMessage;
import gwbot.message.JoinMessage;
import gwbot.message.SwitchLaneMessage;
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

	public BehEhBot(Main main) {
		super(main);
	}

	@Override
	public void onYourCarMessage(YourCarMessage yourCarMessage) {
	}

	@Override
	public void onGameInitMessage(GameInitMessage gameInitMessage) {
		Race race = gameInitMessage.getRace();
		this.track = race.getTrack();
	}

	@Override
	public void onGameStartMessage(GameStartMessage gameStartMessage) {
	}

	@Override
	public void onJoinMessage(JoinMessage joinMessage) {
	}

	private double lastProgression;
	private double speed;
	private double lastSpeed;
	private double lastAngle;
	private boolean switched = false;

	@Override
	public void onCarPositions(List<CarPositionMessage> carPositionMessages) {
		CarPositionMessage ownPositionMessage = carPositionMessages.get(0);

		Piece currentPiece = track.getPiece(ownPositionMessage.getPieceIndex());
		Piece nextPiece = track.getPiece((ownPositionMessage.getPieceIndex() + 1) % track.getPieceCount());
		ownPositionMessage.getInPieceDistance();

		// update speedl
		if (lastProgression < ownPositionMessage.getInPieceDistance()) {
			lastSpeed = speed;
			speed = ownPositionMessage.getInPieceDistance() - lastProgression;
		} else {
			// last piece length - progression from last piece + progressio
		}
		lastProgression = ownPositionMessage.getInPieceDistance();

		// distance to next piece
		double distance = currentPiece.getLength() - ownPositionMessage.getInPieceDistance();

		// calculate angledifference
		double angleDifference = 0.0d;
		if (currentPiece.isCurve()) {
			angleDifference = Math.abs(ownPositionMessage.getAngle()) - Math.abs(lastAngle);
		}
		double angle = lastAngle = ownPositionMessage.getAngle();

		if (currentPiece.isCurve()) {
			//System.out.println("speed " + speed + ", angle " + angle);
		}

		if (!switched && nextPiece.isSwitch()) {
			send(new SwitchLaneMessage(SwitchLaneMessage.Direction.RIGHT));
			switched = true;
			return;
		}

		double throttle = 0.6d;
		if (currentPiece.isCurve()) {
			// throttle = 1 * Math.sin(Math.PI / currentPiece.getLength() *
			// ownPositionMessage.getInPieceDistance());
			// System.out.println(Math.PI / currentPiece.getLength() *
			// ownPositionMessage.getInPieceDistance() + "throttle "+throttle);
		}
		send(new ThrottleMessage(throttle));
	}

	private boolean turboAvailable = false;

	@Override
	public void onTurboAvailable(TurboAvailableMessage turboAvailableMessage) {
		turboAvailable = true;
	}

	@Override
	public void onGameEndMessage(GameEndMessage gameEndMessage) {
	}

}
