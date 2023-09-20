package cs3500.pa04;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipTest {
  Coord start = new Coord(1, 1);
  Coord end = new Coord(1, 5);
  CoordSet set = new CoordSet(start, end);
  Ship battleship1 = new Ship(ShipType.BATTLESHIP, set);

  Coord start2 = new Coord(1, 1);
  Coord end2 = new Coord(5, 1);
  CoordSet set2 = new CoordSet(start2, end2);
  Ship battleship2 = new Ship(ShipType.BATTLESHIP, set);



  @BeforeEach
  void setup() {
    battleship1 = new Ship(ShipType.BATTLESHIP, set);
    battleship2 = new Ship(ShipType.BATTLESHIP, set2);
  }

  @Test
  void generateHitMap() {
  }

  @Test
  void getShipType() {
    assertEquals(battleship1.getShipType(), ShipType.BATTLESHIP);
    assertEquals(battleship2.getShipType(), ShipType.BATTLESHIP);
  }

  @Test
  void getStartToEnd() {
    Coord start = new Coord(1, 1);
    Coord end = new Coord(1, 5);
    CoordSet set = new CoordSet(start, end);

    assertEquals(set, battleship1.getStartToEnd());

  }

  @Test
  void getHitmap() {
    Map<Coord, Boolean> map = new HashMap<>();
    map.put(new Coord(1, 1), false);
    map.put(new Coord(1, 2), false);
    map.put(new Coord(1, 3), false);
    map.put(new Coord(1, 4), false);
    map.put(new Coord(1, 5), false);

    battleship1.generateHitMap();
    Map<Coord, Boolean> output = battleship1.getHitmap();

    assertEquals(map, output);

    Map<Coord, Boolean> map2 = new HashMap<>();
    map2.put(new Coord(1, 1), false);
    map2.put(new Coord(2, 1), false);
    map2.put(new Coord(3, 1), false);
    map2.put(new Coord(4, 1), false);
    map2.put(new Coord(5, 1), false);

    battleship2.generateHitMap();
    Map<Coord, Boolean> output2 = battleship2.getHitmap();

    assertEquals(map2, output2);


  }

  @Test
  void sunkenShip() {
    assertTrue(battleship1.sunkenShip());
    battleship1.generateHitMap();
    assertFalse(battleship1.sunkenShip());
  }

  @Test
  void containCoord() {
    battleship1.generateHitMap();
    assertTrue(battleship1.containCoord(new Coord(1, 1)));
    assertFalse(battleship1.containCoord(new Coord(0, 0)));

    battleship2.generateHitMap();
    assertTrue(battleship2.containCoord(new Coord(1, 1)));
    assertFalse(battleship2.containCoord(new Coord(0, 0)));
  }

  @Test
  void getDirection() {
    assertEquals(ShipDirection.VERTICAL, battleship1.getDirection());
    assertEquals(ShipDirection.HORIZONTAL, battleship2.getDirection());
  }

  @Test
  void getLength() {
    assertEquals(5, battleship1.getLength());
    assertEquals(5, battleship2.getLength());
  }
}