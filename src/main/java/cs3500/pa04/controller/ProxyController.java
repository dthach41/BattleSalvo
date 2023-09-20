package cs3500.pa04.controller;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.BattleSalvoJsonMsg;
import cs3500.pa04.Coord;
import cs3500.pa04.GameResult;
import cs3500.pa04.GameType;
import cs3500.pa04.Ship;
import cs3500.pa04.ShipType;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.EndGameResponseJson;
import cs3500.pa04.json.JoinResponseJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.ReportDamageJson;
import cs3500.pa04.json.ReportDamageResponseJson;
import cs3500.pa04.json.SetupJson;
import cs3500.pa04.json.SetupResponseJson;
import cs3500.pa04.json.ShipJson;
import cs3500.pa04.json.SuccessfulHitsJson;
import cs3500.pa04.json.SuccessfulHitsResponseJson;
import cs3500.pa04.json.TakeShotResponseJson;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.PlayerAi;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This class uses the Proxy Pattern to talk to the Server and dispatch methods to the Player.
 */
public class ProxyController {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final ObjectMapper mapper = new ObjectMapper();
  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");
  private Player player;
  private AiPlayer aiPlayer;
  private Random rand;


  /**
   * Constructs a ProxyController
   *
   * @param server connection to server
   * @throws IOException an IO exception
   */
  public ProxyController(Socket server) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.rand = new Random();
    this.aiPlayer = new AiPlayer(rand.nextInt());
    //    this.player = player;
  }

  /**
   * Constructs a ProxyController for testing
   *
   * @param server connection to server
   * @param rand random object
   * @throws IOException exception
   */
  public ProxyController(Socket server, Random rand) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.rand = rand;
    this.aiPlayer = new AiPlayer(rand.nextInt());
    //    this.player = player;
  }



  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an exception is thrown.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (JsonParseException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      System.out.println();
    }
  }


  /**
   * Determines the type of request the server has sent and delegates to the
   * corresponding helper method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  private void delegateMessage(MessageJson message) {
    String name = message.messageName();
    JsonNode arguments = message.arguments();
    name = name.replace('-', '_');

    switch (BattleSalvoJsonMsg.valueOf(name.toUpperCase())) {
      case JOIN -> handleJoin();
      case SETUP -> handleSetup(arguments);
      case TAKE_SHOTS -> handleTakeShots();
      case REPORT_DAMAGE -> handleReportDamage(arguments);
      case SUCCESSFUL_HITS -> handleSuccessfulShots(arguments);
      case END_GAME -> handleEndGame(arguments);
      default -> System.out.println("default");
    }
  }


  /**
   * Sends a message that contains username and game type (single or multiplayer)
   */
  public void handleJoin() {
    // GitHub username
    String name = "yerp159";
    GameType type = GameType.SINGLE;
    JoinResponseJson response = new JoinResponseJson("join",
        new JoinResponseJson.Arguments(name, type.toString()));
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    out.println(jsonResponse);
  }


  /**
   * Parses arguments and calls setup with those arguments to get a list of ships
   * and sends the list back to the server
   *
   * @param arguments JSON representations of arguments for setup
   */
  public void handleSetup(JsonNode arguments) {
    SetupJson setupArgs = this.mapper.convertValue(arguments, SetupJson.class);
    int height = setupArgs.height();
    int width = setupArgs.width();
    this.aiPlayer.generateAiPlayerBoard(width, height); // temporary ai fix
    this.player = new PlayerAi(rand.nextInt(), aiPlayer); // temporary ai fix
    Map<ShipType, Integer> specification = setupArgs.fleetSpec();
    List<Ship> shipPlacements = player.setup(height, width, specification);

    List<ShipJson> fleetJson = new ArrayList<>();
    for (Ship ship : shipPlacements) {
      Coord coord = ship.getStartToEnd().getStart();
      CoordJson coordJson = new CoordJson(coord.getX(), coord.getY());
      int length = ship.getLength();
      String direction = ship.getDirection().toString();
      ShipJson shipJson = new ShipJson(coordJson, length, direction);
      fleetJson.add(shipJson);
    }

    SetupResponseJson response = new SetupResponseJson("setup",
        new SetupResponseJson.Arguments(fleetJson));
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    out.println(jsonResponse);
  }


  /**
   * Sends a message with list of coordinates of the shots we are taking
   */
  public void handleTakeShots() {
    List<Coord> shots = player.takeShots();
    List<Coord> newShots = new ArrayList<>();
    for (Coord c : shots) {
      if (c != null) {
        newShots.add(c);
      }
    }
    shots = newShots;
    System.out.println(shots.size());
    List<CoordJson> coordinates = new ArrayList<>();

    for (Coord coord : shots) {
      CoordJson coordJson = new CoordJson(coord.getX(), coord.getY());
      coordinates.add(coordJson);
    }

    TakeShotResponseJson response = new TakeShotResponseJson("take-shots",
        new TakeShotResponseJson.Arguments(coordinates));
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    out.println(jsonResponse);
  }

  /**
   * Sends a response back to server containing list Coord where our AI ship was hit
   *
   * @param arguments arguments of message received
   */
  public void handleReportDamage(JsonNode arguments) {
    ReportDamageJson damageResponseJson =
        this.mapper.convertValue(arguments, ReportDamageJson.class);

    List<CoordJson> coordinates = damageResponseJson.coordinates();
    List<Coord> coordList = convertCoordJsonToCoord(coordinates);
    List<Coord> reportDamageList = player.reportDamage(coordList);

    List<CoordJson> coordJsonReturnList = convertCoordToCoordJson(reportDamageList);
    ReportDamageResponseJson response = new ReportDamageResponseJson("report-damage",
        new ReportDamageResponseJson.Arguments(coordJsonReturnList));

    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    out.println(jsonResponse);
  }


  /**
   * Sends successful hits to server
   *
   * @param arguments arguments of message
   */
  public void handleSuccessfulShots(JsonNode arguments) {
    SuccessfulHitsJson successfulHitsJson =
        this.mapper.convertValue(arguments, SuccessfulHitsJson.class);

    List<CoordJson> coordinates = successfulHitsJson.coordinates();
    List<Coord> coordList = convertCoordJsonToCoord(coordinates);
    player.successfulHits(coordList);

    SuccessfulHitsResponseJson response = new SuccessfulHitsResponseJson("successful-hits",
        new SuccessfulHitsResponseJson.Arguments());

    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    out.println(jsonResponse);
  }

  /**
   * Ends the battlesalvo game
   *
   * @param arguments arguments of message received
   */
  public void handleEndGame(JsonNode arguments) {
    EndGameJson endGameJson = this.mapper.convertValue(arguments, EndGameJson.class);
    GameResult gameResult = endGameJson.result();
    String reason = endGameJson.reason();
    player.endGame(gameResult, reason);

    EndGameResponseJson response = new EndGameResponseJson("end-game",
        new EndGameResponseJson.Arguments());

    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    out.println(jsonResponse);

  }

  /**
   * Converts CoordJson to Coord
   *
   * @param coordJsonList list of CoordJson to convert
   * @return converted list
   */
  private List<Coord> convertCoordJsonToCoord(List<CoordJson> coordJsonList) {
    List<Coord> coordList = new ArrayList<>();
    for (CoordJson c : coordJsonList) {
      Coord currCord = new Coord(c.x(), c.y());
      coordList.add(currCord);
    }
    return coordList;
  }

  /**
   * Converts Coord to CoordJson
   *
   * @param coordList list of Coord to convert
   * @return converted list
   */
  private List<CoordJson> convertCoordToCoordJson(List<Coord> coordList) {
    List<CoordJson> coordJsonList = new ArrayList<>();
    for (Coord c : coordList) {
      CoordJson currCoordJson = new CoordJson(c.getX(), c.getY());
      coordJsonList.add(currCoordJson);
    }
    return coordJsonList;
  }

}
