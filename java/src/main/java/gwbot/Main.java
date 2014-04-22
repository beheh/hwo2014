package gwbot;

import gwbot.track.Track;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import gwbot.message.JoinMessage;
import gwbot.message.PingMessage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import gwbot.bot.GenericBot;
import gwbot.message.CarPositionsMessage;
import gwbot.message.GameInitMessage;
import gwbot.message.Message;
import gwbot.message.MessageWrapper;
import gwbot.message.MessageWrapperExclStrat;
import gwbot.message.YourCarMessage;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 *
 * @author Goldwipf <goldwipf@beheh.de>
 */
public final class Main {

	private static Socket socket;

	public static void main(String... args) {

		System.out.println("This is GoldwipfBot, ready to go");

		String host = args[0];
		int port = Integer.parseInt(args[1]);
		String botName = args[2];
		String botKey = args[3];

		System.out.print("Connecting to " + host + ":" + port + " as " + botName + "/" + botKey + "...");

		try {
			socket = new Socket(host, port);
			System.out.println(" connected");

			final PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));
			final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

			new Main(reader, writer, new JoinMessage(botName, botKey));
		} catch (IOException ex) {
			System.out.println("failed");
			ex.printStackTrace(System.err);
			System.exit(1);
		}
	}

	public final Gson gson;
	private final PrintWriter writer;

	public Main(final BufferedReader reader, final PrintWriter writer, final JoinMessage join) throws IOException {

		gson = new GsonBuilder()
				.setExclusionStrategies(new MessageWrapperExclStrat())
				.create();

		this.writer = writer;
		String line = null;

		GenericBot bot = new GoldwipfBot(this);

		// send join message
		System.out.print("Joining game...");
		send(join);

		// success, if we receive answer
		while ((line = reader.readLine()) != null) {
			final MessageWrapper msgFromServer = gson.fromJson(line, MessageWrapper.class);

			if (msgFromServer.msgType.equals("join")) {
				System.out.println(" accepted");
				continue;
			}

			if (!msgFromServer.msgType.equals("carPositions")) {
				System.out.println("*" + msgFromServer.msgType);
			}

			boolean disconnect = false;
			switch (msgFromServer.msgType) {
				case "yourCar":
					YourCarMessage yourCarMessage = gson.fromJson(msgFromServer.data.toString(), YourCarMessage.class);
					System.out.println("we are the car " + yourCarMessage.getName() + " with color " + yourCarMessage.getColor());
					bot.onYourCarMessage(yourCarMessage);
					break;
				case "gameInit":
					GameInitMessage gameInitMessage = gson.fromJson(msgFromServer.data.toString(), GameInitMessage.class);
					Track track = gameInitMessage.getRace().getTrack();
					System.out.println("track is " + track.getName() + " with " + track.getLanes().size() + " lanes");
					bot.onGameInitMessage(gameInitMessage);
					break;
				case "gameStart":
					break;
				case "turboAvailable":
					break;
				case "carPositions":
					Type carPositionsCollectionType = new TypeToken<Collection<CarPositionsMessage>>() {
					}.getType();
					Collection<CarPositionsMessage> carPositions = gson.fromJson(msgFromServer.data.toString(), carPositionsCollectionType);
					bot.onCarPositions(carPositions);
					break;
				case "lapFinished":
					System.out.println("lap finished");
					break;
				case "crash":
					break;
				case "spawn":
					break;
				case "gameEnd":
					break;
				case "tournamentEnd":
					disconnect = true;
					break;
				default:
					// do nothing, return ping to acknowledge
					send(new PingMessage());
					break;
			}

			if (disconnect) {
				break;
			}
		}

		System.out.println("disconnecting from server");
		socket.close();
	}

	public void send(final Message message) {
		writer.println(message.toJson(gson));
		writer.flush();
	}
}
