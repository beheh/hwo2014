package gwbot;

import gwbot.bot.GenericBot;
import gwbot.message.CarPositionsMessage;
import gwbot.message.GameEndMessage;
import gwbot.message.GameInitMessage;
import gwbot.message.GameStartMessage;
import gwbot.message.JoinMessage;
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
public class GoldwipfBot extends GenericBot {

	private Track track;

	public GoldwipfBot(Main main) {
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
	private double lastAngle;

	@Override
	public void onCarPositions(List<CarPositionsMessage> carPositionsMessages) {
		CarPositionsMessage ownPositionMessage = carPositionsMessages.get(0);

		Piece currentPiece = track.getPiece(ownPositionMessage.getPieceIndex());
		Piece nextPiece = track.getPiece((ownPositionMessage.getPieceIndex() + 1) % track.getPieceCount());
		ownPositionMessage.getInPieceDistance();

		// update speed
		if(lastProgression < ownPositionMessage.getInPieceDistance()) {
			speed = ownPositionMessage.getInPieceDistance() - lastProgression;
		} else {
			//last piece length - progression from last piece + progressio
		}
		lastProgression = ownPositionMessage.getInPieceDistance();

		// distance to next piece
		double distance = currentPiece.getLength() - ownPositionMessage.getInPieceDistance();

		// calculate angledifference
		double angleDifference = 0.0d;
		if(currentPiece.isCurve()) {
			angleDifference = Math.abs(ownPositionMessage.getAngle()) - Math.abs(lastAngle);
		}
		double angle = lastAngle = ownPositionMessage.getAngle();

		double throttle = 1;
		if(!currentPiece.isCurve() && nextPiece.isCurve() && distance < speed * 150 && speed > 3) {
			throttle = 0;
		} else if(currentPiece.isCurve()) {
			// 2/3 of the way
			if(ownPositionMessage.getInPieceDistance() > (currentPiece.getLength() * 1 / 2)) {
				if(angleDifference > 0) {
					throttle = 0.5d * (30d / (30d - angleDifference));
				} else {
					throttle = 1;
				}
			} else {
				throttle = 1d / 30d * Math.max(1, 30 - Math.abs(ownPositionMessage.getAngle()));
			}
		} else {
			throttle = 1;
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
