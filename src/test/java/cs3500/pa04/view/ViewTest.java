package cs3500.pa04.view;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ViewTest {
  View view = new View();
  String[][] board1 = {{"0", "0"}, {"0", "0"}};
  String[][] board2 = {{"1", "1"}, {"1", "1"}};
  private final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  String boardDiagram() {
    return "Opponent's Board: "
        + System.lineSeparator()
        + "0 0 "
        + System.lineSeparator()
        + "0 0 "
        + System.lineSeparator()
        + "----"
        + System.lineSeparator()
        + "Your board: "
        + System.lineSeparator()
        + "1 1 "
        + System.lineSeparator()
        + "1 1";
  }

  @BeforeEach
  void setup() {
    view = new View();
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @AfterEach
  public void tearDown() {
    System.setOut(standardOut);
  }

  @Test
  void printBoard() {
    view.printBoard(board1, board2);
    assertEquals(boardDiagram(), outputStreamCaptor.toString().trim());
  }

  @Test
  void printShotCount() {
    view.printShotCount(2, "Player 1");
    assertEquals("Player 1" + ", you have " + "2" + " shots" + System.lineSeparator()
            + "For every coordinate pair, please go to the next line to enter the new pair. "
            + "For every coordinate, please keep a space in between the x and y coordinate",
        outputStreamCaptor.toString().trim());
  }

  @Test
  void printReport() {
  }
}