package gwbot.message;

import com.google.gson.annotations.SerializedName;
import gwbot.car.Car;
import gwbot.car.PiecePosition;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class CarPositionMessage extends Message {

	private final double angle;
	private final PiecePosition piecePosition;
	@SerializedName("id")
	private final Car car;

	public CarPositionMessage(double angle, PiecePosition piecePosition, Car car) {
		this.angle = angle;
		this.piecePosition = piecePosition;
		this.car = car;
	}

	@Override
	protected String msgType() {
		return "carPositions";
	}

	/**
	 * Returns the current angle of the car relative to the track.
	 *
	 * @return the current angle
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * Returns the index of the track piece the car is currently on.
	 *
	 * @return the piece index
	 */
	public int getPieceIndex() {
		return piecePosition.getPieceIndex();
	}

	/**
	 * Returns the distance the car has traveled on the current piece.
	 *
	 * @return
	 */
	public double getInPieceDistance() {
		return piecePosition.getInPieceDistance();
	}

	/**
	 * Returns the index of the lane the car will be in at the end of the current piece.
	 *
	 * @return the lane index
	 */
	public int getEndLaneIndex() {
		return piecePosition.getEndLaneIndex();
	}

	/**
	 * Returns the index of the lane the car was at the end of the current piece.
	 *
	 * @return the lane index
	 */
	public int getStartLaneIndex() {
		return piecePosition.getStartLaneIndex();
	}

	/**
	 * Returns the lap the car is currently in.
	 *
	 * @return -1 before starting line pass, >= 0 for subsequent laps
	 */
	public int getLap() {
		return piecePosition.getLap();
	}

	public Car getCar() {
		return car;
	}
}
