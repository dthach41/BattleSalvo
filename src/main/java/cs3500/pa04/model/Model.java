package cs3500.pa04.model;

import cs3500.pa04.Coord;
import cs3500.pa04.CoordSet;
import cs3500.pa04.Ship;
import cs3500.pa04.ShipType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a model
 */
public class Model {
  private Player player1;
  private Player player2;
  private List<Ship> player1Ships;
  private List<Ship> player2Ships;
  private String[][] player1Board;
  private String[][] opponentHitmap;

  /**
   * Instantiates a model object
   *
   * @param player1 player 1 of the game
   * @param player2 player 2 of the game
   */
  public Model(Player player1, Player player2) {
    this.player1 = player1;
    this.player2 = player2;
  }

  /**
   * Initializes all boards with the height and width
   *
   * @param height height of the board
   * @param width  width of the board
   */
  public void initBoard(int height, int width) {
    player1Board = new String[height][width];
    opponentHitmap = new String[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        player1Board[i][j] = "0";
        opponentHitmap[i][j] = "0";
      }
    }
  }

  /**
   * Generates both boards and ships of both players
   *
   * @param height        height of the board
   * @param width         width of the board
   * @param specification specifications of ships given from user
   */
  public void generateBoardAndShip(int height, int width, Map<ShipType, Integer> specification) {
    List<Ship> player1Board = player1.setup(height, width, specification);
    List<Ship> player2Board = player2.setup(height, width, specification);
    this.player1Ships = player1Board;
    this.player2Ships = player2Board;
    markBoard(player1Board);
  }

  /**
   * Marks the current board
   *
   * @param player1 list of ships and marks the board with these ships
   */
  private void markBoard(List<Ship> player1) {
    for (Ship s : player1) {
      CoordSet currCoordSet = s.getStartToEnd();
      Coord currCoordStart = currCoordSet.getStart();
      Coord currCoordEnd = currCoordSet.getEnd();
      if (currCoordStart.getX() == currCoordEnd.getX()) {
        markVertBoard(s.getShipType().type, currCoordStart.getY(), currCoordEnd.getY(),
            currCoordStart.getX());
      } else {
        markHorBoard(s.getShipType().type, currCoordStart.getX(), currCoordEnd.getX(),
            currCoordStart.getY());
      }
    }
  }

  /**
   * Marks the board horizontally with the given string
   *
   * @param s  string to mark board with
   * @param x1 start coord for x position
   * @param x2 end coord for x position
   * @param y  row that will be marked
   */
  private void markHorBoard(String s, int x1, int x2, int y) {
    for (int i = x1; i <= x2; i++) {
      player1Board[y][i] = s;
    }
  }

  /**
   * Marks the board vertically with the given string
   *
   * @param s  string to mark board with
   * @param y1 start coord for y position
   * @param y2 end coord for y position
   * @param x  column that will be marked
   */
  private void markVertBoard(String s, int y1, int y2, int x) {
    for (int i = y1; i <= y2; i++) {
      player1Board[i][x] = s;
    }
  }

  /**
   * Takes both players shots and updates the board
   *
   * @param p1ShotList list of shots from player 1
   * @param p2ShotList list of shots from player 2
   */
  public void updateShots(List<Coord> p1ShotList, List<Coord> p2ShotList) {
    for (Coord c : p2ShotList) {
      int x = c.getX();
      int y = c.getY();
      player1Board[y][x] = "1";
    }

    for (Coord c : p1ShotList) {
      int x = c.getX();
      int y = c.getY();
      opponentHitmap[y][x] = "1";
    }
  }

  /**
   * Updates the damage on the board given the damage list
   *
   * @param p1DamageList the damage done to player 1
   * @param p2DamageList the damage done to player 2
   */
  public void updateDamage(List<Coord> p1DamageList, List<Coord> p2DamageList) {
    for (Coord c : p1DamageList) {
      int x = c.getX();
      int y = c.getY();
      player1Board[y][x] = "H";
    }

    for (Coord c : p2DamageList) {
      int x = c.getX();
      int y = c.getY();
      opponentHitmap[y][x] = "H";
    }

    for (Coord c : p1DamageList) {
      for (Ship s : player1Ships) {
        Map<Coord, Boolean> currShipMap = s.getHitmap();
        if (currShipMap.containsKey(c)) {
          currShipMap.put(c, true);
        }
      }
    }

    for (Coord c : p2DamageList) {
      for (Ship s : player2Ships) {
        Map<Coord, Boolean> currShipMap = s.getHitmap();
        if (currShipMap.containsKey(c)) {
          currShipMap.put(c, true);
        }
      }
    }
  }

  /**
   * Updates both players lists of ships
   */
  public void updateShipList() {
    List<Ship> saveListPlayer1 = new ArrayList<>();
    List<Ship> saveListPlayer2 = new ArrayList<>();

    for (Ship s : player1Ships) {
      if (!s.sunkenShip()) {
        saveListPlayer1.add(s);
      }
    }

    for (Ship s : player2Ships) {
      if (!s.sunkenShip()) {
        saveListPlayer2.add(s);
      }
    }

    player1Ships = saveListPlayer1;
    player2Ships = saveListPlayer2;
  }

  /**
   * Checks the game state
   *
   * @return returns false if the game has ended, true if the game is still ongoing
   */
  public boolean checkGameState() {
    if (player1Ships.size() == 0) {
      return false;
    }
    if (player2Ships.size() == 0) {
      return false;
    }
    return true;
  }

  /**
   * gets this player map
   *
   * @return this player 1 map
   */
  public String[][] viewPlayerMap() {
    return this.player1Board;
  }

  /**
   * gets opponents map
   *
   * @return opponent map
   */
  public String[][] viewOpponentMap() {
    return this.opponentHitmap;
  }

  /**
   * gets player 1 ship size
   *
   * @return player 1 ship size
   */
  public int getPlayer1ShipSize() {
    return player1Ships.size();
  }

  /**
   * gets player 2 ship size
   *
   * @return player 2 ship size
   */
  public int getPlayer2ShipSize() {
    return player2Ships.size();
  }


}
