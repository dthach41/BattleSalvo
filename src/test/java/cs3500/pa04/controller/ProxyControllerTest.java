package cs3500.pa04.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.GameResult;
import cs3500.pa04.GameType;
import cs3500.pa04.Mocket;
import cs3500.pa04.ShipDirection;
import cs3500.pa04.ShipType;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JoinResponseJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.ReportDamageJson;
import cs3500.pa04.json.ReportDamageResponseJson;
import cs3500.pa04.json.SetupJson;
import cs3500.pa04.json.SetupResponseJson;
import cs3500.pa04.json.ShipJson;
import cs3500.pa04.json.SuccessfulHitsJson;
import cs3500.pa04.json.TakeShotResponseJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProxyControllerTest {
  private ProxyController proxyController;
  private ByteArrayOutputStream testLog;
  private ObjectMapper mapper;

  @BeforeEach
  void initData() {
    testLog = new ByteArrayOutputStream(2048);
    assertEquals("", testLog.toString());

    mapper = new ObjectMapper();

  }



  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson = new MessageJson(messageName,
        JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      jsonParser.readValueAs(classRef);
      // No error thrown when parsing to a GuessJson, test passes!
    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      fail();
    }
  }

  @Test
  void run() {
    EndGameJson endGameJson = new EndGameJson(GameResult.WIN,
        "Player 1 sank all of Player 2's ships");
    JsonNode message = createSampleMessage("end-game", endGameJson);
    Mocket socket = new Mocket(this.testLog, List.of(message.toString()));
    try {
      proxyController = new ProxyController(socket);
      socket.close();
    } catch (Exception e) {
      fail();
    }

    proxyController.run();

  }

  @Test
  void runException() {
    EndGameJson endGameJson = new EndGameJson(GameResult.WIN,
        "Player 1 sank all of Player 2's ships");
    JsonNode message = createSampleMessage("failed", endGameJson);
    Mocket socket = new Mocket(this.testLog, List.of(message.toString()));

    try {
      proxyController = new ProxyController(socket);
    } catch (Exception e) {
      fail();
    }

    assertThrows(
        RuntimeException.class, () -> {
          proxyController.run();
        });

  }

  @Test
  void handleJoin() {
    JoinJson joinJson = new JoinJson("join");

    JsonNode message = createSampleMessage("join", joinJson);
    Mocket socket = new Mocket(this.testLog, List.of(message.toString()));

    try {
      proxyController = new ProxyController(socket);
    } catch (Exception e) {
      fail();
    }

    proxyController.run();

    JoinResponseJson responseJson = new JoinResponseJson("join",
        new JoinResponseJson.Arguments("yerp159", GameType.SINGLE.toString()));
    String expected = JsonUtils.serializeRecord(responseJson).toString() + System.lineSeparator();
    assertEquals(expected, logToString());
    this.testLog.reset();
  }


  @Test
  void handleSetup() {
    Map<ShipType, Integer> fleet = new HashMap<>();
    fleet.put(ShipType.CARRIER, 1);
    fleet.put(ShipType.BATTLESHIP, 1);
    fleet.put(ShipType.DESTROYER, 1);
    fleet.put(ShipType.SUBMARINE, 1);

    SetupJson setupJson = new SetupJson(6, 6, fleet);

    JsonNode message = createSampleMessage("setup", setupJson);
    Mocket socket = new Mocket(this.testLog, List.of(message.toString()));

    Random rand = new Random(0);
    try {
      proxyController = new ProxyController(socket, rand);
    } catch (Exception e) {
      fail();
    }

    proxyController.run();

    List<ShipJson> fleetJsons = new ArrayList<>();

    CoordJson coord1 = new CoordJson(1, 0);
    fleetJsons.add(new ShipJson(coord1, 6, ShipDirection.VERTICAL.toString()));

    CoordJson coord2 = new CoordJson(3, 0);
    fleetJsons.add(new ShipJson(coord2, 5, ShipDirection.VERTICAL.toString()));

    CoordJson coord3 = new CoordJson(4, 2);
    fleetJsons.add(new ShipJson(coord3, 4, ShipDirection.VERTICAL.toString()));

    CoordJson coord4 = new CoordJson(5, 1);
    fleetJsons.add(new ShipJson(coord4, 3, ShipDirection.VERTICAL.toString()));

    SetupResponseJson responseJson = new SetupResponseJson("setup",
        new SetupResponseJson.Arguments(fleetJsons));

    String expected = JsonUtils.serializeRecord(responseJson).toString() + System.lineSeparator();
    assertEquals(expected, logToString());

  }

  /**
   * Sets up Player in ProxyController and resets testLog
   */
  void setUp() {
    Map<ShipType, Integer> fleet = new HashMap<>();
    fleet.put(ShipType.CARRIER, 1);
    fleet.put(ShipType.BATTLESHIP, 1);
    fleet.put(ShipType.DESTROYER, 1);
    fleet.put(ShipType.SUBMARINE, 1);

    SetupJson setupJson = new SetupJson(6, 6, fleet);
    JsonNode args1 = JsonUtils.serializeRecord(setupJson);

    proxyController.handleSetup(args1);
    this.testLog.reset();
  }

  @Test
  void handleTakeShots() {
    JsonNode args = mapper.createObjectNode();
    MessageJson messageJson = new MessageJson("take-shots", args);
    JsonNode message = JsonUtils.serializeRecord(messageJson);

    Mocket socket = new Mocket(this.testLog, List.of(message.toString()));

    Random rand = new Random(1);
    try {
      proxyController = new ProxyController(socket, rand);
    } catch (Exception e) {
      fail();
    }

    setUp();

    proxyController.run();

    List<CoordJson> coordinates = new ArrayList<>();
    coordinates.add(new CoordJson(0, 0));
    coordinates.add(new CoordJson(1, 0));
    coordinates.add(new CoordJson(2, 0));
    coordinates.add(new CoordJson(3, 0));

    TakeShotResponseJson responseJson = new TakeShotResponseJson("take-shots",
        new TakeShotResponseJson.Arguments(coordinates));

    String expected = JsonUtils.serializeRecord(responseJson).toString() + System.lineSeparator();

    assertEquals(expected, logToString());
  }

  @Test
  void handleReportDamage() {
    List<CoordJson> coordinates = new ArrayList<>();
    coordinates.add(new CoordJson(0, 0));

    ReportDamageJson reportDamageJson = new ReportDamageJson(coordinates);
    JsonNode message = createSampleMessage("report-damage", reportDamageJson);
    Mocket socket = new Mocket(this.testLog, List.of(message.toString()));

    Random rand = new Random(2);

    try {
      proxyController = new ProxyController(socket, rand);
    } catch (Exception e) {
      fail();
    }

    setUp();
    proxyController.run();

    List<CoordJson> coords = new ArrayList<>();
    coords.add(new CoordJson(0, 0));
    ReportDamageResponseJson responseJson = new ReportDamageResponseJson("report-damage",
        new ReportDamageResponseJson.Arguments(coords));

    String expected = JsonUtils.serializeRecord(responseJson).toString() + System.lineSeparator();

    assertEquals(expected, logToString());
  }

  @Test
  void handleSuccessfulShots() {
    List<CoordJson> coordinates = new ArrayList<>();
    coordinates.add(new CoordJson(0, 0));

    SuccessfulHitsJson successfulHitsJson = new SuccessfulHitsJson(coordinates);
    JsonNode message = createSampleMessage("successful-hits", successfulHitsJson);
    Mocket socket = new Mocket(this.testLog, List.of(message.toString()));

    try {
      proxyController = new ProxyController(socket);
    } catch (Exception e) {
      fail();
    }

    setUp();
    proxyController.run();

    String expected = "{\"method-name\":\"successful-hits\",\"arguments\":{}}"
        + System.lineSeparator();

    assertEquals(expected, logToString());
  }


  @Test
  void handleEndGame() {
    EndGameJson endGameJson = new EndGameJson(GameResult.WIN,
        "Player 1 sank all of Player 2's ships");
    JsonNode message = createSampleMessage("end-game", endGameJson);
    Mocket socket = new Mocket(this.testLog, List.of(message.toString()));

    Random rand = new Random(4);
    try {
      proxyController = new ProxyController(socket, rand);
    } catch (Exception e) {
      fail();
    }

    setUp();
    proxyController.run();


    String expected = "{\"method-name\":\"end-game\",\"arguments\":{}}"
        + System.lineSeparator();

    assertEquals(expected, logToString());
  }
}