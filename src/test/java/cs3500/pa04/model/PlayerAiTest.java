package cs3500.pa04.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.Coord;
import cs3500.pa04.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerAiTest {
  private AiPlayer ai = new AiPlayer(500);
  private Player playerAi = new PlayerAi(ai);
  private Map<ShipType, Integer> ships = new HashMap<>();

  @BeforeEach
  void beforeEach() {
  }

  private void setup() {
    ships.put(ShipType.BATTLESHIP, 1);
    ships.put(ShipType.CARRIER, 1);
    ships.put(ShipType.DESTROYER, 1);
    ships.put(ShipType.SUBMARINE, 1);
  }

  @Test
  void takeShots() {
    setup();
    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(1, 0);
    Coord coord3 = new Coord(2, 0);
    Coord coord4 = new Coord(3, 0);

    List<Coord> expectedList = new ArrayList<>(Arrays.asList(coord1, coord2, coord3, coord4));

    ai.generateAiPlayerBoard(6, 6);
    playerAi.setup(6, 6, ships);
    List<Coord> outList = playerAi.takeShots();
    for (int i = 0; i < 4; i++) {
      assertEquals(expectedList.get(i).getY(), outList.get(i).getY());
      assertEquals(i, expectedList.get(i).getX());
    }

  }
}