package cs3500.pa04;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoordTest {
  Coord coord1 = new Coord(0, 0);
  Coord coord2 = new Coord(0, 0);
  Coord coord3 = new Coord(1, 1);
  Coord coord4 = new Coord(0, 1);
  Coord coord5 = new Coord(1, 0);
  Coord coord6 = new Coord(5, 6);

  private final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @BeforeEach
  void setup() {
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @AfterEach
  public void tearDown() {
    System.setOut(standardOut);
  }

  @Test
  void getX() {
    assertEquals(0, coord1.getX());
  }

  @Test
  void getY() {
    assertEquals(0, coord1.getY());
  }

  @Test
  void printCoords() {
    coord1.printCoords();
    assertEquals("(0, 0)", outputStreamCaptor.toString().trim());
  }

  @Test
  void testEquals() {
    assertTrue(coord1.equals(coord2));
    assertFalse(coord1.equals(coord3));
    assertFalse(coord1.equals("hello"));
    assertTrue(coord1.equals(coord1));
    assertFalse(coord4.equals(coord5));
    assertFalse(coord6.equals(coord4));
  }
}