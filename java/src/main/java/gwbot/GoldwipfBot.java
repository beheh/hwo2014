package gwbot;

import gwbot.track.Track;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import gwbot.message.JoinMessage;
import gwbot.message.ThrottleMessage;
import gwbot.message.PingMessage;

import com.google.gson.Gson;
import gwbot.message.GameInitMessage;
import gwbot.message.Message;
import gwbot.message.MessageWrapper;
import gwbot.message.YourCarMessage;

/**
 *
 * @author Goldwipf <goldwipf@beheh.de>
 */
public class GoldwipfBot {

	public static void main(String... args) {
		System.out.println("This is GoldwipfBot, ready to go");

		String host = args[0];
		int port = Integer.parseInt(args[1]);
		String botName = args[2];
		String botKey = args[3];

		System.out.print("Connecting to " + host + ":" + port + " as " + botName + "/" + botKey + "...");

		try {
			final Socket socket = new Socket(host, port);
			System.out.println(" connected");

			final PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));
			final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

			new GoldwipfBot(reader, writer, new JoinMessage(botName, botKey));
		} catch(IOException ex) {
			System.out.println("failed");
			ex.printStackTrace(System.err);
			System.exit(1);
		}
	}

	public final static Gson gson = new Gson();
	private final PrintWriter writer;

	public GoldwipfBot(final BufferedReader reader, final PrintWriter writer, final JoinMessage join) throws IOException {
		this.writer = writer;
		String line = null;

		// send join message
		System.out.print("Joining game...");
		send(join);

		// success, if we receive answer
		while((line = reader.readLine()) != null) {
			final MessageWrapper msgFromServer = gson.fromJson(line, MessageWrapper.class);

			if(msgFromServer.msgType.equals("join")) {
				System.out.println(" accepted");
				continue;
			}

			if(!msgFromServer.msgType.equals("carPositions")) {
				System.out.println("*" + msgFromServer.msgType);
			}

			if(msgFromServer.msgType.equals("yourCar")) {
				YourCarMessage yourCarMessage = gson.fromJson(msgFromServer.data.toString(), YourCarMessage.class);
				System.out.println("we are the car " + yourCarMessage.getName() + " with color " + yourCarMessage.getColor());
				String color = msgFromServer.data.toString();
			} else if(msgFromServer.msgType.equals("gameInit")) {
				GameInitMessage gameInitMessage = gson.fromJson(msgFromServer.data.toString(), GameInitMessage.class);
				Track track = gameInitMessage.getRace().getTrack();
				System.out.println("track is " + track.getName() + " with " + track.getLanes().size() + " lanes");
			} else if(msgFromServer.msgType.equals("gameStart")) {
				// we are now running the game
			} else if(msgFromServer.msgType.equals("carPositions")) {
				// main game tick
				send(new ThrottleMessage(0.5));
			} else if(msgFromServer.msgType.equals("lapFinished")) {
				// we completed a lap
			} else if(msgFromServer.msgType.equals("crash")) {
				// we crashed because we went too fast
			} else if(msgFromServer.msgType.equals("spawn")) {
				// we respawned after crashing
			} else if(msgFromServer.msgType.equals("gameEnd")) {
				// game is over
			} else {
				// do nothing, return ping to acknowledge
				send(new PingMessage());
			}
		}
	}

	private void send(final Message message) {
		writer.println(message.toJson());
		writer.flush();
	}
}
