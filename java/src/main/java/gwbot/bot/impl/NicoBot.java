package gwbot.bot.impl;

import gwbot.Main;
import gwbot.bot.GenericBot;
import gwbot.message.CarPositionsMessage;
import gwbot.message.GameEndMessage;
import gwbot.message.GameInitMessage;
import gwbot.message.GameStartMessage;
import gwbot.message.JoinMessage;
import gwbot.message.SwitchLaneMessage;
import gwbot.message.SwitchLaneMessage.Direction;
import gwbot.message.ThrottleMessage;
import gwbot.message.TurboAvailableMessage;
import gwbot.message.YourCarMessage;
import gwbot.race.Race;
import gwbot.track.Piece;
import gwbot.track.Track;
import gwbot.track.extendet.ExtendetPiece;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Benedict Etzel <developer@beheh.de>
 */
public class NicoBot extends GenericBot {

	private Track _track;

	public NicoBot(Main main) {
		super(main);
	}

	@Override
	public void onGameInitMessage(GameInitMessage gameInitMessage) {

		Race race = gameInitMessage.getRace();
		this._track = race.getTrack();
		List<Piece> extendetPieces = new ArrayList<>();
		for (int index = 0; index < _track.getPieceCount(); index++) {
			extendetPieces.add(new ExtendetPiece(_track.getPiece(index)));
		}
		for (int index = 0; index < _track.getPieceCount(); index++) {
			ExtendetPiece piece = (ExtendetPiece) extendetPieces.get(index);
			piece.setNext((ExtendetPiece) extendetPieces.get((index + 1 + extendetPieces.size()) % extendetPieces.size()));
			piece.setPrev((ExtendetPiece) extendetPieces.get((index - 1 + extendetPieces.size()) % extendetPieces.size()));
		}
		_track = new Track(_track.getId(), _track.getName(), extendetPieces, _track.getLanes(), _track.getStartingPoint());
	}

	@Override
	public void onJoinMessage(JoinMessage joinMessage) {
	}

	@Override
	public void onYourCarMessage(YourCarMessage yourCarMessage) {
	}

	private Piece sendFor = null;

	@Override
	public void onCarPositions(List<CarPositionsMessage> carPositionsMessages) {
		CarPositionsMessage ownPositionMessage = carPositionsMessages.get(0);

		ExtendetPiece currentPiece = (ExtendetPiece) _track.getPiece(ownPositionMessage.getPieceIndex());
		ownPositionMessage.getInPieceDistance();

		if (currentPiece.next() != sendFor && currentPiece.next().isSwitch()) {
			double angToNextSwitch = 0;
			// wenn der Switch auch kurve ist, so die hälfte des winkels mit
			// einrechnen
			if (currentPiece.next().isCurve()) {
				angToNextSwitch += (currentPiece.next().getAngle() / 2);
			}
			// gesamtkurvenwinkel bis zum nächsten Switch berechnen
			ExtendetPiece piece = currentPiece.next().next();
			while (!piece.isSwitch()) {
				angToNextSwitch += piece.getAngle();
				piece = piece.next();
			}
			if (angToNextSwitch == 0) {
				send(new ThrottleMessage(0.5));
			}
			else if (angToNextSwitch > 0) {
				send(new SwitchLaneMessage(Direction.RIGHT));
			}
			else {
				send(new SwitchLaneMessage(Direction.LEFT));
			}
			sendFor = currentPiece.next();
		}
		else {
			send(new ThrottleMessage(0.5));
		}
	}

	@Override
	public void onTurboAvailable(TurboAvailableMessage turboAvailableMessage) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gwbot.bot.BotInterface#onGameStartMessage(gwbot.message.GameStartMessage)
	 */
	@Override
	public void onGameStartMessage(GameStartMessage pGameStartMessage) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gwbot.bot.BotInterface#onGameEndMessage(gwbot.message.GameEndMessage)
	 */
	@Override
	public void onGameEndMessage(GameEndMessage pGameEndMessage) {
		// TODO Auto-generated method stub

	}

}
