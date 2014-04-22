package gwbot.message;

/**
 *
 * @author Goldwipf <goldwipf@beheh.de>
 */
public class MessageWrapper {

	public final String msgType;
	public final Object data;
	public final String gameId;
	public final int gameTick;

	public MessageWrapper(String msgType, Object data, String gameId, int gameTick) {
		this.msgType = msgType;
		this.data = data;
		this.gameId = gameId;
		this.gameTick = gameTick;
	}

	public MessageWrapper(final Message sendMsg) {
		this(sendMsg.msgType(), sendMsg.msgData(), null, 0);
	}

	public MessageWrapper(String msgType, Object data) {
		this(msgType, data, null, 0);
	}

}
