package gwbot;

import gwbot.bot.GenericBot;
import gwbot.bot.impl.GoldwipfBot;
import gwbot.bot.impl.NicoBot;
import gwbot.gson.MessageWrapperExclStrat;
import gwbot.message.CarPositionsMessage;
import gwbot.message.GameEndMessage;
import gwbot.message.GameInitMessage;
import gwbot.message.GameStartMessage;
import gwbot.message.JoinMessage;
import gwbot.message.Message;
import gwbot.message.MessageWrapper;
import gwbot.message.PingMessage;
import gwbot.message.TurboAvailableMessage;
import gwbot.message.YourCarMessage;
import gwbot.track.Piece;
import gwbot.track.Track;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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
		}
		catch (IOException ex) {
			System.out.println("failed");
			ex.printStackTrace(System.err);
			System.exit(1);
		}
	}

	public final Gson gson;
	private final PrintWriter writer;

	public Main(final BufferedReader reader, final PrintWriter writer, final JoinMessage join) throws IOException {

		gson = new GsonBuilder().setExclusionStrategies(new MessageWrapperExclStrat()).create();

		this.writer = writer;
		String line = null;

		GenericBot bot = null;
		switch (System.getProperty("user.name")) {
			case "Nico Smeenk":
				System.out.println("Use NicoBot");
				bot = new NicoBot(this);
				break;
			default:
				System.out.println("Use GoldwipfBot");
				bot = new GoldwipfBot(this);
				break;
		}

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
					// receive details about your car
					YourCarMessage yourCarMessage = gson.fromJson(msgFromServer.data.toString(), YourCarMessage.class);
					System.out.println("we are the car " + yourCarMessage.getName() + " with color " + yourCarMessage.getColor());
					bot.onYourCarMessage(yourCarMessage);
					break;
				case "gameInit":
					// receive initial details about the following game
					GameInitMessage gameInitMessage = gson.fromJson(msgFromServer.data.toString(), GameInitMessage.class);
					Track track = gameInitMessage.getRace().getTrack();
					Collection<Piece> pieces = track.getPieces();
					for (Piece piece : pieces) {
						System.out.println(piece);
					}
					System.out.println("track is " + track.getName() + " with " + track.getLanes().size() + " lanes");
					bot.onGameInitMessage(gameInitMessage);
					break;
				case "gameStart":
					// start the current game with details specified in gameInit
					GameStartMessage gameStartMessage = new GameStartMessage();
					bot.onGameStartMessage(gameStartMessage);
					break;
				case "carPositions":
					// receive
					Type carPositionsCollectionType = new TypeToken<ArrayList<CarPositionsMessage>>() {
					}.getType();
					List<CarPositionsMessage> carPositions = gson.fromJson(msgFromServer.data.toString(), carPositionsCollectionType);
					bot.onCarPositions(carPositions);
					break;
				case "turboAvailable":
					// turbo is available for a certain length
					TurboAvailableMessage turboAvailableMessage = gson.fromJson(msgFromServer.data.toString(), TurboAvailableMessage.class);
					bot.onTurboAvailable(turboAvailableMessage);
					break;
				case "lapFinished":
					// completed a lap
					// @todo
					break;
				case "crash":
					// crashing
					// @todo
					break;
				case "spawn":
					// respawn after crashing
					// @todo
					break;
				case "gameEnd":
					// current game has ended
					GameEndMessage gameEndMessage = gson.fromJson(msgFromServer.data.toString(), GameEndMessage.class);
					bot.onGameEndMessage(gameEndMessage);
					break;
				case "tournamentEnd":
					disconnect = true;
					// @todo
					break;
				case "error":
					System.err.println("received error: " + msgFromServer.data.toString());
					System.err.println("last message was: " + lastMessage.toJson(gson));
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

	private Message lastMessage;

	public void send(final Message message) {
		lastMessage = message;
		writer.println(message.toJson(gson));
		writer.flush();
	}
}
