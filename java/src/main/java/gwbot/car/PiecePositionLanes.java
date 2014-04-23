package gwbot.car;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class PiecePositionLanes {

	private final int startLaneIndex;
	private final int endLaneIndex;

	public PiecePositionLanes(int startLaneIndex, int endLaneIndex) {
		this.startLaneIndex = startLaneIndex;
		this.endLaneIndex = endLaneIndex;
	}

	public int getEndLaneIndex() {
		return endLaneIndex;
	}

	public int getStartLaneIndex() {
		return startLaneIndex;
	}

}
