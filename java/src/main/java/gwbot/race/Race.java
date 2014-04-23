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

	/**
	 * Returns the track of this race.
	 *
	 * @return the race track
	 */
	public Track getTrack() {
		return track;
	}

	/**
	 * Returns all participating cars in this race.
	 *
	 * @return the cars
	 */
	public ArrayList<Car> getCars() {
		return cars;
	}

	/**
	 * Returns the amount of laps required this race.
	 *
	 * @return the lap count, or 0 if no specific lap count
	 */
	public int getLapCount() {
		return raceSession.getLapCount();
	}

	/**
	 * Returns the maximum allowed time for a single lap.
	 *
	 * @return the maximum lap time, or 0 if no maximum time
	 */
	public long maxLapTimeMs() {
		return raceSession.getMaxLapTimeMs();
	}

	/**
	 * Returns, whether the current race is a quick race or not.
	 * A quick race is a single timed race, whereas it would otherwise usually feature multiple cars.
	 *
	 * @return whether the race is a quick race
	 */
	public boolean isQuickRace() {
		return raceSession.isQuickRace();
	}

}
