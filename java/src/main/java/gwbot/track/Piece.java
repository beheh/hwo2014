package gwbot.track;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class Piece {

	private final double length;
	@SerializedName("switch")
	private final boolean isSwitch;
	private final int radius;
	private final double angle;

	public Piece(double length, boolean isSwitch, int radius, double angle) {
		this.length = length;
		this.isSwitch = isSwitch;
		this.radius = radius;
		this.angle = angle;
	}

	private Piece next;

	/**
	 * Returns the next piece.
	 *
	 * @return the next piece.
	 */
	public Piece next() {
		return next;
	}

	public void setNext(Piece next) {
		this.next = next;
	}

	private Piece previous;

	/**
	 * Returns the previous piece.
	 *
	 * @return the previous piece.
	 */
	public Piece previous() {
		return previous;
	}

	public void setPrevious(Piece previous) {
		this.previous = previous;
	}

	/**
	 * Returns the track length of this piece on the centerline.
	 *
	 * @return the piece length
	 * @deprecated
	 */
	public double getLength() {
		return getLength(new Lane(0, 0));
	}

	/**
	 * Returns the track length of this piece on the current lane.
	 *
	 * @return the piece length
	 */
	public double getLength(Lane lane) {
		if (length == 0 && (radius != 0 || angle != 0)) {
			double curveRadius = radius + lane.distanceFromCenter;
			return (2d * Math.PI * curveRadius) * Math.abs(angle) / 360;
		}
		return length;
	}

	/**
	 * Returns whether this piece is a switch.
	 *
	 * @return true, if piece is a switch
	 */
	public boolean isSwitch() {
		return isSwitch;
	}

	/**
	 * Returns whether this piece is a curve.
	 *
	 * @return true, if piece is a curve
	 */
	public boolean isCurve() {
		return radius != 0 || angle != 0;
	}

	/**
	 * Returns whether this piece is a curve.
	 *
	 * @return true, if piece is a left curve
	 */
	public boolean isLeftCurve() {
		return angle < 0;
	}

	/**
	 * Returns whether this xpiece is a curve.
	 *
	 * @return true, if piece is a right curve
	 */
	public boolean isRightCurve() {
		return angle > 0;
	}

	/**
	 * Returns the radius of this piece (only applies if this piece is a curve)
	 *
	 * @return the curve radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Returns the angle of this piece (only applies if this piece is a curve)
	 *
	 * @return the curve angle
	 */
	public double getAngle() {
		return angle;
	}

	@Override
	public String toString() {
		String msg = "piece with length " + getLength();
		if (angle != 0) {
			msg += ", angle " + getAngle();
		}
		if (radius != 0) {
			msg += ", radius " + getRadius();
		}
		if (isSwitch) {
			msg += " (is a switch)";
		}
		return msg;
	}

}
