package cs3500.pa04.controller;


import cs3500.pa04.Coord;
import cs3500.pa04.ShipType;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.Model;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.PlayerAi;
import cs3500.pa04.model.PlayerHuman;
import cs3500.pa04.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents a controller
 */
public class Controller {
  private final Scanner scanner;
  private final View view = new View();
  private Model model;
  private int maxHeight;
  private int maxWidth;
  private final AiPlayer ai = new AiPlayer();
  private Player player1;
  private Player player2;


  /**
   * constructs a controller
   *
   * @param read a readable
   */
  public Controller(Readable read) {
    this.scanner = new Scanner(read);
  }

  /**
   * Runs game
   */
  public void run() {
    setup();
    game();
    end();
  }

  /**
   * Ends the game
   */
  private void end() {
    view.endGame(model.checkGameState());
  }

  /**
   * Setup the game
   */
  private void setup() {
    view.printBoardSetup();
    ArrayList<Integer> parsedIntsBoard = readInts(2);
    while ((parsedIntsBoard.get(0) > 15) || (parsedIntsBoard.get(0) < 6)
        || (parsedIntsBoard.get(1) > 15) || (parsedIntsBoard.get(1) < 6)) {
      view.printErrorMessage();
      parsedIntsBoard = readInts(2);
    }

    view.printShipSetup();
    ArrayList<Integer> parsedIntsShips = readInts(4);
    while (checkInputsShips(parsedIntsShips)) {
      view.printErrorMessage();
      parsedIntsShips = readInts(4);
    }
    int width = parsedIntsBoard.get(0);
    int height = parsedIntsBoard.get(1);
    final Map<ShipType, Integer> specifications = makeShipList(parsedIntsShips);

    ai.generateAiPlayerBoard(height, width);

    Player player1 = new PlayerHuman(this);
    Player player2 = new PlayerAi(ai);
    this.player1 = player1;
    this.player2 = player2;
    model = new Model(player1, player2);

    model.initBoard(height, width);
    model.generateBoardAndShip(height, width, specifications);

    maxHeight = height - 1;
    maxWidth = width - 1;
  }

  /**
   * Makes a map of a specification of ships
   *
   * @param parsedShips amount of a specific ship type
   * @return a map of the ships in a specification
   */
  private Map<ShipType, Integer> makeShipList(ArrayList<Integer> parsedShips) {
    Map<ShipType, Integer> map = new HashMap<>();
    map.put(ShipType.CARRIER, parsedShips.get(0));
    map.put(ShipType.BATTLESHIP, parsedShips.get(1));
    map.put(ShipType.DESTROYER, parsedShips.get(2));
    map.put(ShipType.SUBMARINE, parsedShips.get(3));

    return map;
  }

  /**
   * Checks user input to determine if the user input a correct number of ships
   *
   * @param amountOfShipsList number of ships the user entered
   * @return boolean which determines whether the input was valid
   */
  private boolean checkInputsShips(ArrayList<Integer> amountOfShipsList) {
    int boatAmount = 0;
    for (Integer i : amountOfShipsList) {
      boatAmount += i;
    }
    return ((!(amountOfShipsList.size() == 4)) || (boatAmount > 8));
  }

  /**
   * Runs the game
   */
  private void game() {
    view.printBoard(model.viewOpponentMap(), model.viewPlayerMap());
    int roundNumber = 1;
    boolean game = true;
    while (game) {
      view.printShotCount(model.getPlayer1ShipSize(), "Player 1");
      List<Coord> player1Shots = player1.takeShots();
      view.printShotCount(model.getPlayer2ShipSize(), "Player 2");
      System.out.println("Please enter your shots P2");
      List<Coord> player2Shots = player2.takeShots();

      model.updateShots(player1Shots, player2Shots);

      List<Coord> player1Damage = player1.reportDamage(player2Shots);
      List<Coord> player2Damage = player2.reportDamage(player1Shots);

      player1.successfulHits(player1Damage);
      player2.successfulHits(player2Damage);

      model.updateDamage(player1Damage, player2Damage);

      view.printReport(player1Shots, player2Shots, player1Damage, player2Damage);
      System.out.println("Player1 Damage: " + player1Damage.size());
      System.out.println("Player2 Damage: " + player2Damage.size());

      model.updateShipList();
      game = model.checkGameState();

      view.printBoard(model.viewOpponentMap(), model.viewPlayerMap());

      System.out.println("Round " + roundNumber);
      roundNumber++;
    }
  }

  /**
   * Gets a shot from a player
   *
   * @return Coord of a shot
   */
  public ArrayList<Integer> getShot() {
    ArrayList<Integer> coordList = readInts(2);
    while (!validShot(coordList)) {
      view.printErrorMessage();
      coordList = readInts(2);
    }
    return coordList;
  }

  /**
   * Checks to see if this shot is a valid shot
   *
   * @param coordList a coord broken up as a list
   * @return whether the coordinate is valid
   */
  private boolean validShot(ArrayList<Integer> coordList) {
    return ((coordList.get(0) >= 0) || (coordList.get(0) <= maxWidth)
        || (coordList.get(1) >= 0) || (coordList.get(1) <= maxHeight));
  }


  /**
   * Reads the first 2 integers seperated by a space in between them to generate a coord
   *
   * @return Coord from the input stream
   */
  private ArrayList<Integer> readInts(int amount) {
    ArrayList<Integer> myInts = new ArrayList<>();
    for (int i = 0; i < amount; i++) {
      if (scanner.hasNextInt()) {
        myInts.add(scanner.nextInt());
      }
    }
    return myInts;
  }
}
