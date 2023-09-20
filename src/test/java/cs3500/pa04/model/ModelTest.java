package cs3500.pa04.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.Coord;
import cs3500.pa04.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModelTest {
  private AiPlayer ai = new AiPlayer();

  // both sides should have the same random placement
  private cs3500.pa04.controller.Controller controller =
      new cs3500.pa04.controller.Controller(new java.io.InputStreamReader(System.in));
  private Player player1 = new PlayerHuman(500, controller);
  private Player player2 = new PlayerAi(500, ai);
  private Model model;
  private Map<ShipType, Integer> ships = new HashMap<>();

  private void setupShip() {
    ships.put(ShipType.BATTLESHIP, 1);
    ships.put(ShipType.CARRIER, 1);
    ships.put(ShipType.DESTROYER, 1);
    ships.put(ShipType.SUBMARINE, 1);
  }

  @BeforeEach
  void beforeEach() {
    model = new Model(player1, player2);
    setupShip();
    model.initBoard(6, 6);
  }

  @Test
  void initBoard() {
    String[][] expectedBoard = {{"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"}};

    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedBoard[i][j], model.viewPlayerMap()[i][j]);
      }
    }
  }

  @Test
  void updateShots() {
    Coord coord1 = new Coord(1, 1);
    Coord coord2 = new Coord(3, 4);
    List<Coord> p2Shots = new ArrayList<>(Arrays.asList(coord1, coord2));

    String[][] expectedBoardP1 = {{"0", "0", "0", "0", "0", "0"},
        {"0", "1", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "1", "0", "0"},
        {"0", "0", "0", "0", "0", "0"}};

    Coord coord3 = new Coord(5, 5);
    List<Coord> p1Shots = new ArrayList<>(Arrays.asList(coord3));

    String[][] expectedBoardP2 = {{"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "1"}};

    model.updateShots(p1Shots, p2Shots);

    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(model.viewPlayerMap()[j][i], expectedBoardP1[j][i]);
      }
    }
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(model.viewOpponentMap()[j][i], expectedBoardP2[j][i]);
      }
    }
  }

  @Test
  void updateDamage() {
    model.generateBoardAndShip(6, 6, ships);
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        System.out.print(model.viewPlayerMap()[i][j] + " ");
      }
      System.out.println();
    }

    Coord coord1 = new Coord(1, 1);
    Coord coord2 = new Coord(3, 4);
    List<Coord> p2Shots = new ArrayList<>(Arrays.asList(coord1, coord2));

    String[][] expectedBoardP1 = {{"D", "D", "D", "D", "0", "0"},
        {"0", "H", "S", "S", "0", "0"},
        {"C", "C", "C", "C", "C", "C"},
        {"0", "B", "B", "B", "B", "B"},
        {"0", "0", "0", "1", "0", "0"},
        {"0", "0", "0", "0", "0", "0"}};

    Coord coord3 = new Coord(5, 5);
    List<Coord> p1Shots = new ArrayList<>(Arrays.asList(coord3));

    String[][] expectedBoardP2 = {{"0", "0", "0", "0", "0", "0"},
        {"0", "H", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "1"}};

    model.updateShots(p1Shots, p2Shots);

    List<Coord> p1Damage = new ArrayList<>();
    p1Damage.add(coord1);

    List<Coord> p2Damage = new ArrayList<>();
    p2Damage.add(coord1);

    model.updateDamage(p1Damage, p2Damage);

    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(model.viewPlayerMap()[j][i], expectedBoardP1[j][i]);
      }
    }

    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(model.viewOpponentMap()[j][i], expectedBoardP2[j][i]);
      }
    }
  }

  @Test
  void viewPlayerMap() {
    String[][] expectedBoard = {{"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"}};

    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedBoard[i][j], model.viewPlayerMap()[i][j]);
      }
    }
  }

  @Test
  void viewOpponentMap() {
    String[][] expectedBoard = {{"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"},
        {"0", "0", "0", "0", "0", "0"}};

    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(expectedBoard[i][j], model.viewOpponentMap()[i][j]);
      }
    }
  }

  @Test
  void getPlayer1ShipSize() {
    model.generateBoardAndShip(6, 6, ships);
    assertEquals(4, model.getPlayer1ShipSize());
  }

  @Test
  void getPlayer2ShipSize() {
    model.generateBoardAndShip(6, 6, ships);
    assertEquals(4, model.getPlayer2ShipSize());
  }
}

