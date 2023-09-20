package cs3500.pa04.model;

import cs3500.pa04.Coord;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an AI Player
 */
public class PlayerAi extends PlayerImpl {
  private AiPlayer ai;

  /**
   * Instantiates an AI Player
   *
   * @param ai AI
   */
  public PlayerAi(AiPlayer ai) {
    super();
    this.ai = ai;
  }

  /**
   * Instantiates an AiPlayer with random seed
   *
   * @param seed random seed
   * @param ai AI
   */
  public PlayerAi(long seed, AiPlayer ai) {
    super(seed);
    this.ai = ai;
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
      Coord cuurCord = ai.generateShot();
      coordList.add(cuurCord);

    }

    return coordList;
  }
}
