package cs3500.pa04.model;

import cs3500.pa04.Coord;
import cs3500.pa04.CoordSet;
import cs3500.pa04.GameResult;
import cs3500.pa04.Ship;
import cs3500.pa04.ShipType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents a player implementation
 */
public abstract class PlayerImpl implements Player {
  public List<Ship> shipList;
  public String[][] playerBoard;
  private Random rand;
  private String name;


  /**
   * initializes playerImpl with seed
   *
   * @param seed random seed
   */
  public PlayerImpl(long seed) {
    this.rand = new Random(seed);
  }

  /**
   * initializes a PlayerImpl
   */
  public PlayerImpl() {
    this.rand = new Random();
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return this.name;
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.playerBoard = new String[height][width];
    ArrayList<Ship> shipList = new ArrayList<>();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.playerBoard[i][j] = "0";
      }
    }
    shipList.addAll(generateShip(ShipType.CARRIER, specifications.get(ShipType.CARRIER)));
    shipList.addAll(generateShip(ShipType.BATTLESHIP, specifications.get(ShipType.BATTLESHIP)));
    shipList.addAll(generateShip(ShipType.DESTROYER, specifications.get(ShipType.DESTROYER)));
    shipList.addAll(generateShip(ShipType.SUBMARINE, specifications.get(ShipType.SUBMARINE)));

    this.shipList = shipList;
    return shipList;
  }

  /**
   * Generates a list of random ships
   *
   * @param key   type of ship
   * @param value amount of ship
   * @return a list of ships in random locations on the board
   */
  private List<Ship> generateShip(ShipType key, int value) {
    List<Ship> shipList = new ArrayList<>();
    for (int i = 0; i < value; i++) {
      CoordSet currCoords = getRandomCord(key);
      Ship currShip = new Ship(key, currCoords);
      currShip.generateHitMap();
      shipList.add(currShip);
    }
    return shipList;
  }

  /**
   * Marks the board horizontally
   *
   * @param s  string being marked on the board
   * @param x1 starting x coordinate
   * @param x2 ending x coordinate
   * @param y  row being marked
   */
  private void markHorBoard(String s, int x1, int x2, int y) {
    for (int i = x1; i <= x2; i++) {
      playerBoard[y][i] = s;
    }
  }

  /**
   * Marks the board vertically
   *
   * @param s  string marked on the board
   * @param y1 starting y coordinate
   * @param y2 ending y coordinate
   * @param x  column being marked
   */
  private void markVertBoard(String s, int y1, int y2, int x) {
    for (int i = y1; i <= y2; i++) {
      playerBoard[i][x] = s;
    }
  }

  /**
   * Generates a random CoordSet for a ship that doesn't overlap with any other ship
   *
   * @param ship type of ship
   * @return CoordSet of a ship that doesn't overlap other ships
   */
  private CoordSet getRandomCord(ShipType ship) {
    int shipSize = ship.size - 1;
    while (true) {
      boolean isHorizontal = rand.nextBoolean();
      int counter = 0;
      if (isHorizontal) {
        int randX = rand.nextInt(playerBoard[0].length - shipSize);
        int randY = rand.nextInt(playerBoard.length);
        for (int i = randX; i < randX + shipSize + 1; i++) {
          if (!playerBoard[randY][i].equals("0")) {
            break;
          } else {
            counter++;
          }
          if (counter == shipSize + 1) {
            Coord start = new Coord(randX, randY);
            Coord end = new Coord((randX + shipSize), randY);
            markHorBoard(ship.type, start.getX(), end.getX(), start.getY());
            CoordSet set = new CoordSet(start, end);
            return set;
          }
        }
      } else {
        int randX = rand.nextInt(playerBoard[0].length);
        int randY = rand.nextInt(playerBoard.length - shipSize);
        for (int i = randY; i < randY + shipSize + 1; i++) {
          if (!playerBoard[i][randX].equals("0")) {
            break;
          } else {
            counter++;
          }
          if (counter == shipSize + 1) {
            Coord start = new Coord(randX, randY);
            Coord end = new Coord(randX, (randY + shipSize));
            markVertBoard(ship.type, start.getY(), end.getY(), start.getX());
            CoordSet set = new CoordSet(start, end);
            return set;
          }
        }
      }
    }
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public abstract List<Coord> takeShots();

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *     ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> coordsHit = new ArrayList<>();
    for (Coord c : opponentShotsOnBoard) {
      for (Ship s : shipList) {
        if (s.containCoord(c)) {
          coordsHit.add(c);
        }
      }
    }
    updateHitMap(opponentShotsOnBoard);
    return coordsHit;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {

  }

  /**
   * Updates the hitmap
   *
   * @param shotsThatHitOpponentShips list of shots that hit opponent ships
   */
  private void updateHitMap(List<Coord> shotsThatHitOpponentShips) {
    for (Coord c : shotsThatHitOpponentShips) {
      for (Ship s : shipList) {
        Map<Coord, Boolean> currShipMap = s.getHitmap();
        if (currShipMap.containsKey(c)) {
          currShipMap.put(c, true);
        }
      }
    }
  }

  /**
   * Gets the count of the amount of sunken ship this player has
   *
   * @return count of sunken ship
   */
  public int sunkenShipCount() {
    int counter = 0;
    for (Ship s : shipList) {
      if (s.sunkenShip()) {
        counter++;
      }
    }
    return counter;
  }

  private void updateShiplist() {
    List<Ship> newShipList = new ArrayList<>();
    for (Ship s : shipList) {
      if (!s.sunkenShip()) {
        newShipList.add(s);
      }
    }
    shipList = newShipList;
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    System.out.println(reason);
  }
}