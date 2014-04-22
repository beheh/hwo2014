package gwbot;

import gwbot.bot.GenericBot;
import gwbot.message.CarPositionsMessage;
import gwbot.message.GameInitMessage;
import gwbot.message.JoinMessage;
import gwbot.message.ThrottleMessage;
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
	private double lastProgression;
	private double speed;

	public GoldwipfBot(Main main) {
		super(main);
	}

	@Override
	public void onGameInitMessage(GameInitMessage gameInitMessage) {
		Race race = gameInitMessage.getRace();
		this.track = race.getTrack();
	}

	@Override
	public void onJoinMessage(JoinMessage joinMessage) {
	}

	@Override
	public void onYourCarMessage(YourCarMessage yourCarMessage) {
	}

	@Override
	public void onCarPositions(List<CarPositionsMessage> carPositionsMessages) {
		CarPositionsMessage ownPositionMessage = carPositionsMessages.get(0);

		Piece currentPiece = track.getPiece(ownPositionMessage.getPieceIndex());
		Piece nextPiece = track.getPiece((ownPositionMessage.getPieceIndex() + 1) % track.getPieceCount());
		ownPositionMessage.getInPieceDistance();

		// calculate current speed
		if (lastProgression < ownPositionMessage.getInPieceDistance()) {
			speed = ownPositionMessage.getInPieceDistance() - lastProgression;
		} else {
			//last piece length - progression from last piece + progressio
		}
		lastProgression = ownPositionMessage.getInPieceDistance();

		// distance to next piece
		double distance = currentPiece.getLength() - ownPositionMessage.getInPieceDistance();

		if (!currentPiece.isCurve() && nextPiece.isCurve() && distance < speed * 150 && speed > 3) {
			send(new ThrottleMessage(0));
		} else if (currentPiece.isCurve()) {
			send(new ThrottleMessage(0.9d / 30d * Math.max(1, 30 - Math.abs(ownPositionMessage.getAngle()))));
		} else {
			send(new ThrottleMessage(1));
		}
	}

}
