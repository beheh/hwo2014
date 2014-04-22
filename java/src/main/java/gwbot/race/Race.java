package gwbot.race;

import gwbot.car.Car;
import gwbot.track.Track;
import java.util.ArrayList;

/**
 *
 * @author benedict
 */
public class Race {

	private final Track track;
	private final ArrayList<Car> cars;
	private final RaceSession raceSession;

	public Race(Track track, ArrayList<Car> cars, RaceSession raceSession) {
		this.track = track;
		this.cars = cars;
		this.raceSession = raceSession;
	}

	public Track getTrack() {
		return track;
	}

	public ArrayList<Car> getCars() {
		return cars;
	}

	public RaceSession getRaceSession() {
		return raceSession;
	}

}
