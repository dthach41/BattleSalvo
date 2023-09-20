package cs3500.pa04.model;

import cs3500.pa04.Coord;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents an AI
 */
public class AiPlayer {
  private List<Coord> availableShots = new ArrayList<>();
  private final Random rand;

  /**
   * Constructs aiPlayer
   */
  public AiPlayer() {
    this.rand = new Random();
  }

  /**
   * Instantiates an AI Player for testing
   *
   * @param seed set random to generate a shot
   */
  public AiPlayer(long seed) {
    this.rand = new Random(seed);
  }

  /**
   * Constructs an AiPlayer with given seed
   *
   * @param availableShots list of available shots
   * @param seed random seed
   */
  public AiPlayer(List<Coord> availableShots, long seed) {
    this.availableShots = availableShots;
    this.rand = new Random(seed);
  }


  /**
   * Generates all available shots and a board for the AI
   *
   * @param height height of the board
   * @param width  width of the board
   */
  public void generateAiPlayerBoard(int height, int width) {
    List<Coord> shots = new ArrayList<>();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        shots.add(new Coord(j, i));
      }
    }
    rand.nextInt();
    this.availableShots = shots;
  }

  /**
   * Generates a shot for the AI
   *
   * @return a valid shot
   */
  public Coord generateShot() {
    int shotAmount = availableShots.size();
    Coord shot;
    if (shotAmount > 0) {
      shot = availableShots.remove(0);
    } else {
      shot = null;
    }
    return shot;
  }
}
