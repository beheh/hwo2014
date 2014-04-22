package gwbot.track;

import java.util.List;

/**
 *
 * @author benedict
 */
public class Track {

	private final String id;
	private final String name;
	private final List<Piece> pieces;
	private final List<Lane> lanes;
	private final StartingPoint startingPoint;

	public Track(String id, String name, List<Piece> pieces, List<Lane> lanes, StartingPoint startingPoint) {
		this.id = id;
		this.name = name;
		this.lanes = lanes;
		this.pieces = pieces;
		this.startingPoint = startingPoint;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getPieceCount() {
		return pieces.size();
	}

	public List<Piece> getPieces() {
		return pieces;
	}
	
	public Piece getPiece(int index) {
		return pieces.get(index);
	}

	public int getLaneCount() {
		return lanes.size();
	}

	public List<Lane> getLanes() {
		return lanes;
	}

	public StartingPoint getStartingPoint() {
		return startingPoint;
	}

}
