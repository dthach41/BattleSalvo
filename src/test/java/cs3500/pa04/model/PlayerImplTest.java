package cs3500.pa04.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.Coord;
import cs3500.pa04.CoordSet;
import cs3500.pa04.GameResult;
import cs3500.pa04.Ship;
import cs3500.pa04.ShipType;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerImplTest {
  private cs3500.pa04.controller.Controller controller =
      new cs3500.pa04.controller.Controller(new java.io.InputStreamReader(System.in));
  private Player player1 = new PlayerHuman(500, controller);
  private Map<ShipType, Integer> ships = new HashMap<>();

  private final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @BeforeEach
  void beforeEach() {
    player1 = new PlayerHuman(500, controller);
    setupShip();
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @AfterEach
  public void tearDown() {
    System.setOut(standardOut);
  }

  private void setupShip() {
    ships.put(ShipType.BATTLESHIP, 1);
    ships.put(ShipType.CARRIER, 1);
    ships.put(ShipType.DESTROYER, 1);
    ships.put(ShipType.SUBMARINE, 1);
  }

  @Test
  void name() {
    assertEquals(player1.name(), null);
  }

  @Test
  void setup() {

    Coord coordStart1 = new Coord(0, 2);
    Coord coordStart2 = new Coord(1, 3);
    Coord coordStart3 = new Coord(0, 0);
    Coord coordStart4 = new Coord(1, 1);

    Coord coordEnd1 = new Coord(5, 2);
    Coord coordEnd2 = new Coord(5, 3);
    Coord coordEnd3 = new Coord(3, 0);
    Coord coordEnd4 = new Coord(3, 1);

    CoordSet coordSet1 = new CoordSet(coordStart1, coordEnd1);
    CoordSet coordSet2 = new CoordSet(coordStart2, coordEnd2);
    CoordSet coordSet3 = new CoordSet(coordStart3, coordEnd3);
    CoordSet coordSet4 = new CoordSet(coordStart4, coordEnd4);

    Ship ship1 = new Ship(ShipType.CARRIER, coordSet1);
    Ship ship2 = new Ship(ShipType.BATTLESHIP, coordSet2);
    Ship ship3 = new Ship(ShipType.DESTROYER, coordSet3);
    Ship ship4 = new Ship(ShipType.SUBMARINE, coordSet4);

    List<Ship> expectedList = new ArrayList<>(Arrays.asList(ship1, ship2, ship3, ship4));

    List<Ship> outputList = player1.setup(6, 6, ships);

    for (int i = 0; i < outputList.size(); i++) {
      assertEquals(expectedList.get(i).getShipType(), outputList.get(i).getShipType());
      assertEquals(expectedList.get(i).getStartToEnd().getStart(),
          outputList.get(i).getStartToEnd().getStart());
      assertEquals(expectedList.get(i).getStartToEnd().getEnd(),
          outputList.get(i).getStartToEnd().getEnd());
    }

  }

  @Test
  void reportDamage() {
    player1.setup(6, 6, ships);

    List<Coord> shots = new ArrayList<>();
    Coord coord1 = new Coord(6, 6);
    Coord coord2 = new Coord(0, 2);
    Coord coord3 = new Coord(1, 2);

    shots.add(coord1);
    shots.add(coord2);
    shots.add(coord3);

    List<Coord> outList = player1.reportDamage(shots);
    List<Coord> expectedList = new ArrayList<>(Arrays.asList(coord2, coord3));

    for (int i = 0; i < outList.size(); i++) {
      assertEquals(expectedList.get(i), outList.get(i));
    }
  }

  @Test
  void endGame() {
    player1.endGame(GameResult.WIN, "Player 1 wins");
    assertEquals("Player 1 wins", outputStreamCaptor.toString().trim());
  }
}

