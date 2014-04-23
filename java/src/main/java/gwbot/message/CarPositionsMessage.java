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

	/**
	 * Returns the current angle of the car relative to the track.
	 * @return the current angle
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * Returns the index of the track piece the car is currently on.
	 * @return the piece index
	 */
	public int getPieceIndex() {
		return piecePosition.getPieceIndex();
	}

	/**
	 * Returns the distance the car has traveled on the current piece.
	 * @return 
	 */
	public double getInPieceDistance() {
		return piecePosition.getInPieceDistance();
	}

	public int getLap() {
		return piecePosition.getLap();
	}

}
