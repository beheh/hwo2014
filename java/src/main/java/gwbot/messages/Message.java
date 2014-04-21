package gwbot.messages;

import com.google.gson.Gson;

/**
 *
 * @author Goldwipf <goldwipf@beheh.de>
 */
public abstract class Message {

	public String toJson() {
		return new Gson().toJson(new MessageWrapper(this));
	}

	protected Object msgData() {
		return this;
	}

	protected abstract String msgType();
}
