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

	public double getLength() {
		return length;
	}

	public boolean isSwitch() {
		return isSwitch;
	}

	public boolean isCurve() {
		return radius != 0 || angle != 0;
	}

	public boolean isLeftCurve() {
		return angle < 0;
	}

	public boolean isRightCurve() {
		return angle > 0;
	}

	public int getRadius() {
		return radius;
	}

	public double getAngle() {
		return angle;
	}

	@Override
	public String toString() {
		String msg = "piece with length " + length;
		if (angle != 0) {
			msg += ", angle " + angle;
		}
		if (radius != 0) {
			msg += ", radius " + radius;
		}
		if (isSwitch) {
			msg += " (is a switch)";
		}
		return msg;
	}

}
