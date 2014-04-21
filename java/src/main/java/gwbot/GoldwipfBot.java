package gwbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import gwbot.messages.JoinMessage;
import gwbot.messages.ThrottleMessage;
import gwbot.messages.PingMessage;

import com.google.gson.Gson;
import gwbot.messages.Message;
import gwbot.messages.MessageWrapper;

/**
 *
 * @author Goldwipf <goldwipf@beheh.de>
 */
public class GoldwipfBot {

	public static void main(String... args) throws IOException {
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		String botName = args[2];
		String botKey = args[3];

		System.out.println("Connecting to " + host + ":" + port + " as " + botName + "/" + botKey);

		final Socket socket = new Socket(host, port);
		final PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));

		final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

		new GoldwipfBot(reader, writer, new JoinMessage(botName, botKey));
	}

	final Gson gson = new Gson();
	private PrintWriter writer;

	public GoldwipfBot(final BufferedReader reader, final PrintWriter writer, final JoinMessage join) throws IOException {
		this.writer = writer;
		String line = null;

		send(join);

		while ((line = reader.readLine()) != null) {
			final MessageWrapper msgFromServer = gson.fromJson(line, MessageWrapper.class);
			if (msgFromServer.msgType.equals("carPositions")) {
				send(new ThrottleMessage(0.5));
			} else if (msgFromServer.msgType.equals("join")) {
				System.out.println("Joined");
			} else if (msgFromServer.msgType.equals("gameInit")) {
				System.out.println("Race init");
			} else if (msgFromServer.msgType.equals("gameEnd")) {
				System.out.println("Race end");
			} else if (msgFromServer.msgType.equals("gameStart")) {
				System.out.println("Race start");
			} else {
				send(new PingMessage());
			}
		}
	}

	private void send(final Message message) {
		writer.println(message.toJson());
		writer.flush();
	}
}
