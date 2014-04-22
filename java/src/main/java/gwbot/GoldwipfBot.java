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
		ownPositionMessage.getPieceIndex();
		Piece currentPiece = track.getPiece(ownPositionMessage.getPieceIndex());
		Piece nextPiece = track.getPiece((ownPositionMessage.getPieceIndex() + 1) % track.getPieceCount());

		if (currentPiece.isCurve()) {
			send(new ThrottleMessage(0.4));
		} else {
			send(new ThrottleMessage(0.8));
		}
	}

}
