package gwbot.message;

import gwbot.result.Result;
import java.util.ArrayList;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class GameEndMessage extends Message {

	private final ArrayList<Result> results;

	public GameEndMessage(ArrayList<Result> results) {
		this.results = results;
	}

	@Override
	protected String msgType() {
		return "gameEnd";
	}

	public ArrayList<Result> getResults() {
		return results;
	}

}
