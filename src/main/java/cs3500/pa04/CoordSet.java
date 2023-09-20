package cs3500.pa04;

import java.util.Objects;

/**
 * Represents a set of coordinates
 */
public class CoordSet {
  private Coord start;
  private Coord end;

  /**
   * Instantiates a set of coordinates
   *
   * @param start a ships starting coordinate
   * @param end a ships ending coordinate
   */
  public CoordSet(Coord start, Coord end) {
    this.start = start;
    this.end = end;
  }

  /**
   * Gets the start coordinate of the ship
   *
   * @return start coordinate
   */
  public Coord getStart() {
    return this.start;
  }

  /**
   * Gets the end coordinate of the ship
   *
   * @return end coordinate
   */
  public Coord getEnd() {
    return this.end;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof CoordSet)) {
      return false;
    }

    CoordSet c = (CoordSet) o;

    return ((this.start.equals(c.start)) && (this.end.equals(c.end)));
  }

  /**
   * Overrides the hashcode
   *
   * @return an object hash code for the coordinate
   */
  @Override
  public int hashCode() {
    return Objects.hash(start, end);
  }
}
