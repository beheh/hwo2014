package gwbot.message;

import gwbot.car.PiecePosition;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class CarPositionsMessage extends Message {

	private final double angle;
	private final PiecePosition piecePosition;

	public CarPositionsMessage(double angle, PiecePosition piecePosition) {
		this.angle = angle;
		this.piecePosition = piecePosition;
	}

	@Override
	protected String msgType() {
		return "carPositions";
	}

	public int getPieceIndex() {
		return piecePosition.getPieceIndex();
	}

	public double getInPieceDistance() {
		return piecePosition.getInPieceDistance();
	}

	public int getLap() {
		return piecePosition.getLap();
	}

}
