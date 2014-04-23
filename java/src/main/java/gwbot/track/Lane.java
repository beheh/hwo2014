package gwbot.track;

/**
 *
 * @author benedict
 */
public class Lane {

	public final int distanceFromCenter;
	public final int index;

	/**
	 * Creates a new lane with specified distance from center and index.
	 * @param distanceFromCenter the distance from the centerline
	 * @param index the index of this lane
	 */
	public Lane(int distanceFromCenter, int index) {
		this.distanceFromCenter = distanceFromCenter;
		this.index = index;
	}

}
