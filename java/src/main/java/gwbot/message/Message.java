package gwbot.message;

import com.google.gson.Gson;

/**
 *
 * @author Goldwipf <goldwipf@beheh.de>
 */
public abstract class Message {

	private Integer gameTick = 0;

	public void setGameTick(Integer gameTick) {
		this.gameTick = gameTick;
	}

	public String toJson(Gson gson) {
		return gson.toJson(new MessageWrapper(this, gameTick));
	}

	protected Object msgData() {
		return this;
	}

	protected abstract String msgType();

	@Override
	public String toString() {
		return this.msgType();
	}
}
