package cs3500.pa04;

import java.util.Objects;

/**
 * Represents a Coordinate
 */
public class Coord {
  private final int x;
  private final int y;

  /**
   * Constructs a coord
   *
   * @param x x coordinate
   * @param y y coordinate
   */
  public Coord(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the x coordinate
   *
   * @return x coordinate
   */
  public int getX() {
    return this.x;
  }

  /**
   * Gets the y coordinate
   *
   * @return y coordinate
   */
  public int getY() {
    return this.y;
  }

  /**
   * Gives a coordinate as a string representation
   */
  public void printCoords() {
    System.out.println("(" + this.x + ", " + this.y + ")");
  }

  /**
   * Overrides equals to determine if two coordinates are equal
   *
   * @param o object to determine equality
   * @return boolean that determines in two coordinates are equal
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof cs3500.pa04.Coord c)) {
      return false;
    }

    return ((this.x == c.x) && (this.y == c.y));
  }

  /**
   * Overrides the hashcode
   *
   * @return an object hash code for the coordinate
   */
  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
