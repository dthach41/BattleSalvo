package cs3500.pa04;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CoordSetTest {
  Coord coord1 = new Coord(0, 0);
  Coord coord2 = new Coord(0, 0);
  Coord coord3 = new Coord(0, 0);
  Coord coord4 = new Coord(0, 0);
  Coord coord5 = new Coord(1, 0);
  Coord coord6 = new Coord(5, 6);
  CoordSet coordSet1 = new CoordSet(coord1, coord2);
  CoordSet coordSet2 = new CoordSet(coord3, coord4);
  CoordSet coordSet3 = new CoordSet(coord5, coord6);

  @Test
  void getStart() {
    assertEquals(coord1, coordSet1.getStart());
    assertEquals(coord3, coordSet2.getStart());
  }

  @Test
  void getEnd() {
    assertEquals(coord2, coordSet1.getEnd());
    assertEquals(coord4, coordSet2.getEnd());
  }

  @Test
  void testEquals() {
    assertTrue(coordSet1.equals(coordSet2));
    assertFalse(coordSet2.equals(coordSet3));
    assertTrue(coordSet1.equals(coordSet1));
    assertFalse(coordSet1.equals("hello"));
  }

  @Test
  void testHashCode() {
    assertFalse(coordSet1.hashCode() == coord1.hashCode() + coord2.hashCode());
    assertTrue(coordSet1.hashCode() == coordSet1.hashCode());
  }
}