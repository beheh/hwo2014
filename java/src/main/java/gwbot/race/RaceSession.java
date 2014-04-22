package gwbot.race;

/**
 *
 * @author benedict
 */
public class RaceSession {

	private final int laps;
	private final long maxLapTimeMs;
	private final boolean quickRace;

	public RaceSession(int laps, long maxLapTimeMs, boolean quickRace) {
		this.laps = laps;
		this.maxLapTimeMs = maxLapTimeMs;
		this.quickRace = quickRace;
	}

	public int getLapCount() {
		return laps;
	}

	public long getMaxLapTimeMs() {
		return maxLapTimeMs;
	}

	public boolean isQuickRace() {
		return quickRace;
	}

}
