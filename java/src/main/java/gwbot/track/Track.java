package gwbot.track;

import java.util.ArrayList;

/**
 *
 * @author benedict
 */
public class Track {

	private final String id;
	private final String name;
	private final ArrayList<Lane> lanes;
	private final StartingPoint startingPoint;

	public Track(String id, String name, ArrayList<Lane> lanes, StartingPoint startingPoint) {
		this.id = id;
		this.name = name;
		this.lanes = lanes;
		this.startingPoint = startingPoint;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Lane> getLanes() {
		return lanes;
	}

	public StartingPoint getStartingPoint() {
		return startingPoint;
	}

}
