package gwbot.car;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class PiecePosition {

	private final int pieceIndex;
	private final double inPieceDistance;
	private final int lap;

	public PiecePosition(int pieceIndex, double inPieceDistance, int lap) {
		this.pieceIndex = pieceIndex;
		this.inPieceDistance = inPieceDistance;
		this.lap = lap;
	}

	public int getPieceIndex() {
		return pieceIndex;
	}

	public double getInPieceDistance() {
		return inPieceDistance;
	}

	public int getLap() {
		return lap;
	}

}
