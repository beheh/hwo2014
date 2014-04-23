package gwbot.car;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class PiecePosition {

	private final int pieceIndex;
	private final double inPieceDistance;
	private final int lap;
	private final PiecePositionLanes lane;

	public PiecePosition(int pieceIndex, double inPieceDistance, int lap, PiecePositionLanes lane) {
		this.pieceIndex = pieceIndex;
		this.inPieceDistance = inPieceDistance;
		this.lap = lap;
		this.lane = lane;
	}

	public int getPieceIndex() {
		return pieceIndex;
	}

	public double getInPieceDistance() {
		return inPieceDistance;
	}

	public int getEndLaneIndex() {
		return lane.getEndLaneIndex();
	}

	public int getStartLaneIndex() {
		return lane.getStartLaneIndex();
	}

	public int getLap() {
		return lap;
	}

}
