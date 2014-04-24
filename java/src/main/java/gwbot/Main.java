package gwbot;

import gwbot.bot.GenericBot;
import gwbot.bot.impl.GoldwipfBot;
import gwbot.bot.impl.NicoBot;
import gwbot.gson.HwoJsonExclusionStrategy;
import gwbot.message.CarPositionMessage;
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
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import gwbot.bot.impl.BehEhBot;
import gwbot.message.CrashMessage;
import gwbot.message.DnfMessage;
import gwbot.message.FinishMessage;
import gwbot.message.SpawnMessage;

/**
 *
 * @author Goldwipf <goldwipf@beheh.de>
 */
public final class Main {

	private static Socket socket;

	public static void main(String... args) {

		System.out.println("This is Team Goldwipf, ready to go");

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

			GenericBot bot;
			switch (System.getProperty("user.name")) {
				case "Nico Smeenk":
					bot = new NicoBot();
					break;
				case "benedict":
					bot = new BehEhBot();
					break;
				default:
					bot = new GoldwipfBot();
					break;
			}

			new Main(reader, writer, new JoinMessage(bot.getClass().getSimpleName(), botKey), bot);
		}
		catch (IOException ex) {
			System.out.println("failed");
			ex.printStackTrace(System.err);
			System.exit(1);
		}
	}

	public final Gson gson;
	private final PrintWriter writer;

	int ciDebug = 0;

	public Main(final BufferedReader reader, final PrintWriter writer, final Message join, final GenericBot bot) throws IOException {

		bot.setMain(this);

		gson = new GsonBuilder().setExclusionStrategies(new HwoJsonExclusionStrategy()).create();

		this.writer = writer;
		String line = null;

		System.out.println("Using " + bot.getClass().getSimpleName() + " as backend");

		// send join message
		System.out.print("Joining game...");
		send(join);

		// success, if we receive answer
		while ((line = reader.readLine()) != null) {
			final MessageWrapper msgFromServer = gson.fromJson(line, MessageWrapper.class);

			if (msgFromServer.msgType.equals("join") || msgFromServer.msgType.equals("joinRace")) {
				System.out.println(" accepted");
				continue;
			}

			boolean disconnect = false;
			switch (msgFromServer.msgType) {
				case "yourCar":
					// receive details about your car
					YourCarMessage yourCarMessage = gson.fromJson(msgFromServer.data.toString(), YourCarMessage.class);
					System.out.print("Receiving car data...");
					System.out.println(" \"" + yourCarMessage.getName() + "\", color is " + yourCarMessage.getColor());
					bot.onYourCarMessage(yourCarMessage);
					break;
				case "gameInit":
					// receive initial details about the following game
					GameInitMessage gameInitMessage = gson.fromJson(msgFromServer.data.toString(), GameInitMessage.class);
					Track track = gameInitMessage.getRace().getTrack();
					List<Piece> pieces = track.getPieces();
					System.out.print("Receiving track data...");
					for (int i = 0; i < pieces.size(); i++) {
						Piece piece = pieces.get(i);
						piece.setNext(pieces.get((i + 1 + pieces.size()) % pieces.size()));
						piece.setPrevious(pieces.get((i - 1 + pieces.size()) % pieces.size()));
					}
					/*for (Piece piece : pieces) {
					 System.out.println(piece);
					 }*/
					System.out.println(" track is \"" + track.getName() + "\" with " + track.getLanes().size() + " lanes");
					bot.onGameInitMessage(gameInitMessage);
					break;
				case "gameStart":
					// start the current game with details specified in gameInit
					GameStartMessage gameStartMessage = new GameStartMessage();
					System.out.println("Starting game!");
					bot.onGameStartMessage(gameStartMessage);
					break;
				case "carPositions":
					// receive
					Type carPositionsCollectionType = new TypeToken<ArrayList<CarPositionMessage>>() {
					}.getType();
					List<CarPositionMessage> carPositions = gson.fromJson(msgFromServer.data.toString(), carPositionsCollectionType);
					currentTick = msgFromServer.gameTick;
					bot.onCarPositions(carPositions);
					currentTick = null;
					ciDebug++;
					break;
				case "turboAvailable":
					// turbo is available for a certain length
					TurboAvailableMessage turboAvailableMessage = gson.fromJson(msgFromServer.data.toString(), TurboAvailableMessage.class);
					System.out.println("Turbo is available.");
					bot.onTurboAvailable(turboAvailableMessage);
					break;
				case "lapFinished":
					// somebody completed a lap
					System.out.println("Somebody completed a lap.");
					// @todo
					break;
				case "finish":
					// somebody finished the race
					FinishMessage finishMessage = gson.fromJson(msgFromServer.data.toString(), FinishMessage.class);
					System.out.println(finishMessage.getCar() + " finished the race.");
					// @todo
					break;
				case "crash":
					// somebody crashed
					CrashMessage crashMessage = gson.fromJson(msgFromServer.data.toString(), CrashMessage.class);
					System.out.println(crashMessage.getCar() + " crashed.");
					// @todo
					break;
				case "spawn":
					// somebody respawned after crashing
					SpawnMessage spawnMessage = gson.fromJson(msgFromServer.data.toString(), SpawnMessage.class);
					System.out.println(spawnMessage.getCar() + " respawned after a crash.");
					// @todo
					break;
				case "dnf":
					// somebody was removed from the game
					DnfMessage dnfMessage = gson.fromJson(msgFromServer.data.toString(), DnfMessage.class);
					System.out.println(dnfMessage.getCar() + " was disqualified.");
					// ̍todo
					break;
				case "gameEnd":
					// current game has ended
					GameEndMessage gameEndMessage = gson.fromJson(msgFromServer.data.toString(), GameEndMessage.class);
					System.out.println("Game has ended.");
					bot.onGameEndMessage(gameEndMessage);
					break;
				case "tournamentEnd":
					disconnect = true;
					// @todo
					System.out.println("Tournament has ended.");
					break;
				case "error":
					System.err.println("Error received: \"" + msgFromServer.data.toString() + "\"");
					System.err.println("  Last received was " + gson.toJson(lastReceived));
					System.err.println("  Last sent was " + lastSent.toJson(gson));
					break;
				default:
					// do nothing, return ping to acknowledge
					send(new PingMessage());
					System.out.println("Unknown message received: *" + msgFromServer.msgType);
					break;
			}

			lastReceived = msgFromServer;

			if (ciDebug <= 5) {
				System.err.println("  CI-Debug: Received " + gson.toJson(lastReceived));
				if (lastSent != null) {
					System.err.println("  CI-Debug: Sent " + lastSent.toJson(gson));
				}
				lastSent = null;
			}

			if (disconnect) {
				break;
			}
		}

		System.out.println("Disconnecting from server.");
		socket.close();
	}

	private Message lastSent = null;
	private MessageWrapper lastReceived = null;
	private Integer currentTick = null;

	public void send(final Message message) {
		if (currentTick != null) {
			message.setGameTick(currentTick);
		}
		lastSent = message;
		writer.println(message.toJson(gson));
		writer.flush();
	}
}
