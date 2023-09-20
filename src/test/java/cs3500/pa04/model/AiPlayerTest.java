package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.Coord;
import java.util.LinkedList;
import org.junit.jupiter.api.Test;

class AiPlayerTest {

  @Test
  void generateShot() {
    LinkedList<Coord> list = new LinkedList<>();

    Coord x0y0 = new Coord(0, 0);
    Coord x1y0 = new Coord(1, 0);
    Coord x0y1 = new Coord(0, 1);
    Coord x1y1 = new Coord(1, 1);

    list.add(x0y0);
    list.add(x1y0);
    list.add(x0y1);
    list.add(x1y1);

    String[][] board = {{"0", "0"}, {"0", "0"}};

    AiPlayer ai = new AiPlayer(200);
    ai.generateAiPlayerBoard(2, 2);

    AiPlayer ai2 = new AiPlayer(list, 200);

    assertEquals(ai.generateShot(), ai2.generateShot());
  }
}