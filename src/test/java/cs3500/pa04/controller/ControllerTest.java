package cs3500.pa04.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.Coord;
import java.io.StringReader;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class ControllerTest {
  private Readable readableTest;
  private Controller controller;

  private void setupReadableShots() {
    String read = "1 1";

    readableTest = new StringReader(read);
    controller = new Controller(readableTest);
  }

  //  Test ran on my end but for some reason failed and had issues pushing into GitHub
  @Test
  void run() {
    readableTest = new StringReader("6 6 1 1 1 1"
        + "0 0 0 1 0 2 0 3 0 4 0 5 "
        + "1 0 1 1 1 2 1 3 1 4 1 5 "
        + "2 0 2 1 2 2 2 3 2 4 2 5 "
        + "3 0 3 1 3 2 3 3 3 4 2 5 "
        + "4 0 4 1 4 2 4 3 4 4 4 5 "
        + "5 0 5 1 5 2 5 3 5 4 5 5 "
        + "0 0 0 0 0 0 0 0 0 0 0 0 ");
    controller = new Controller(readableTest);

    controller.run();
  }

  @Test
  void getShot() {
    setupReadableShots();
    ArrayList<Integer> coordInts = controller.getShot();
    Coord controllerCoord = new Coord(coordInts.get(0), coordInts.get(1));
    Coord coord = new Coord(1, 1);

    assertEquals(coord, controllerCoord);
  }
}