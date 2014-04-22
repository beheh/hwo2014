package gwbot.bot;

import gwbot.Main;
import gwbot.message.Message;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public abstract class GenericBot implements BotInterface {

	private Main main;

	public GenericBot(Main main) {
		this.main = main;
	}

	protected void send(Message message) {
		main.send(message);
	}
}
