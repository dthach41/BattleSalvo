package cs3500.pa04;

/**
 * Represents a type of ship
 */
public enum ShipType {
  // refactored shiptype to capital letters
  /**
   * represents carrier
   */
  CARRIER(6, "C"),
  /**
   * represents battleship
   */
  BATTLESHIP(5, "B"),
  /**
   * represents destroyer
   */
  DESTROYER(4, "D"),
  /**
   * represents submarine
   */
  SUBMARINE(3, "S");

  public final int size;
  public final String type;

  /**
   * Instantiates a ship type with the size and the letter for the board
   *
   * @param size size of the ship
   * @param type representation of ship on the board
   */
  private ShipType(int size, String type) {
    this.size = size;
    this.type = type;
  }
}
