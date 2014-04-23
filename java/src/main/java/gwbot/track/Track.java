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

	/**
	 * Returns the id of the current track.
	 *
	 * @return the track id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the name of the current track.
	 *
	 * @return the track name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the piece count of the current track.
	 *
	 * @return the amount of track pieces
	 */
	public int getPieceCount() {
		return pieces.size();
	}

	/**
	 * Returns all pieces of this track.
	 *
	 * @return the track pieces
	 */
	public List<Piece> getPieces() {
		return pieces;
	}

	/**
	 * Returns a specific piece, identified via its index.
	 *
	 * @param index the piece index in the piece list
	 * @return the track pieces
	 */
	public Piece getPiece(int index) {
		return pieces.get(index);
	}

	/**
	 * Returns the lane count of the current track.
	 *
	 * @return the amount of track lanes
	 */
	public int getLaneCount() {
		return lanes.size();
	}

	/**
	 * Returns all lanes of this track.
	 *
	 * @return the track lanes
	 */
	public List<Lane> getLanes() {
		return lanes;
	}

	/**
	 * Returns the starting point of this track.
	 *
	 * @return the starting point
	 */
	public StartingPoint getStartingPoint() {
		return startingPoint;
	}

}
