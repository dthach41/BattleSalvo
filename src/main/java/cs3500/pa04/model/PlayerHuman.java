package cs3500.pa04.model;


import cs3500.pa04.Coord;
import cs3500.pa04.controller.Controller;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a human player
 */
public class PlayerHuman extends PlayerImpl {
  private final Controller controller;

  /**
   * Instantiates a human player
   *
   * @param seed       random seed
   * @param controller this controller
   */
  public PlayerHuman(long seed, cs3500.pa04.controller.Controller controller) {
    super(seed);
    this.controller = controller;
  }

  /**
   * initializes playerHuman
   */
  public PlayerHuman(Controller controller) {
    this.controller = controller;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    List<Coord> coordList = new ArrayList<>();
    int shots = shipList.size() - sunkenShipCount();
    for (int i = 0; i < shots; i++) {
      ArrayList<Integer> coordInts = controller.getShot();
      Coord currCord = new Coord(coordInts.get(0), coordInts.get(1));
      coordList.add(currCord);
    }
    return coordList;
  }
}
