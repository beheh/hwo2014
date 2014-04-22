package gwbot.message;

import gwbot.GoldwipfBot;

/**
 *
 * @author Goldwipf <goldwipf@beheh.de>
 */
public class MessageWrapper {

	public final String msgType;
	public final Object data;

	MessageWrapper(final String msgType, final Object data) {
		this.msgType = msgType;
		this.data = data;
	}

	public MessageWrapper(final Message sendMsg) {
		this(sendMsg.msgType(), sendMsg.msgData());
	}
}
