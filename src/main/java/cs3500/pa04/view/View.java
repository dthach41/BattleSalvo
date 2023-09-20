package cs3500.pa04.view;

import cs3500.pa04.Coord;
import java.util.List;

/**
 * Represents a view
 */
public class View {

  /**
   * Instantiates a View
   */
  public View() {
  }

  /**
   * Takes in both player's boards a prints the board in the console
   *
   * @param board1 opponent board
   * @param board2 user board
   */
  public void printBoard(String[][] board1, String[][] board2) {
    System.out.println("Opponent's Board: ");
    for (int i = 0; i < board1.length; i++) {
      for (int j = 0; j < board1[0].length; j++) {
        System.out.print(board1[i][j] + " ");
      }
      System.out.println();
    }

    for (int i = 0; i < board1.length * 2; i++) {
      System.out.print("-");
    }

    System.out.println();

    System.out.println("Your board: ");
    for (int i = 0; i < board2.length; i++) {
      for (int j = 0; j < board2[0].length; j++) {
        System.out.print(board2[i][j] + " ");
      }
      System.out.println();
    }
  }

  /**
   * Prints the board setup message for the battle salvo gmae
   */
  public void printBoardSetup() {
    System.out.println("Welcome to BattleSalvo!");
    System.out.println("Please enter all integers in this game separated by a space");
    System.out.println();
    System.out.println("Please enter the size of the board between [6, 15]"
        + " inclusive in this manner (only the first two ints entered will be taken): 8 8");

  }

  /**
   * Prints the ship setup
   */
  public void printShipSetup() {
    System.out.println("The ships are [Carrier, Battleship, Destroyer, Submarine].");
    System.out.println(
        "You cannot have more than 4 of a single type of ship"
            + " and you cannot have more than 8 ships");
    System.out.println("Only the first 4 ints you enter will be taken");
    System.out.println();
    System.out.println(
        "Please enter the amount of ships you want in the following format: 1 2 1 3");

  }

  /**
   * Outputs a generic error message to the user
   */
  public void printErrorMessage() {
    System.out.println("The input was invalid, please reenter your values: ");
  }

  /**
   * Prints the users shot count given the shot and the player
   *
   * @param shots  shot count
   * @param player player
   */
  public void printShotCount(int shots, String player) {
    System.out.println(player + ", you have " + shots + " shots");
    System.out.println("For every coordinate pair, please go to the next line to enter the new "
        + "pair. For every coordinate, please keep a space in between the x and y coordinate");
  }

  /**
   * Prints a report of damage done to player 1 and player 2
   *
   * @param player1Damage damage done to player 1
   * @param player2Damage damage done to player 2
   * @param player1Shots player1's shots
   * @param player2Shots player 2's shots
   */
  public void printReport(List<Coord> player1Shots, List<Coord> player2Shots,
                          List<Coord> player1Damage, List<Coord> player2Damage) {

    System.out.println("Player 1 Shots: ");
    for (Coord c : player1Shots) {
      c.printCoords();
    }
    System.out.println("-------------------");
    System.out.println("Player 2 Shots: ");
    for (Coord c : player2Shots) {
      c.printCoords();
    }
    if (player1Damage.size() > 0) {
      System.out.println("-------------------");
      System.out.println("Player 1 Damage: ");
      for (Coord c : player1Damage) {
        c.printCoords();
      }
    }
    if (player2Damage.size() > 0) {
      System.out.println("-------------------");
      System.out.println("Player 2 Damage: ");
      for (Coord c : player2Damage) {
        c.printCoords();
      }
    }
  }

  /**
   * Message to end game
   *
   * @param gameEnd checks the game state
   */
  public void endGame(boolean gameEnd) {
    if (!gameEnd) {
      System.out.println("Game has ended");
    }
  }
}
