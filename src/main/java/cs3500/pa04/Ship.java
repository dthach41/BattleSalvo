package cs3500.pa04;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a ship
 */
public class Ship {
  private ShipType ship;
  private CoordSet startToEnd;
  private Coord start;
  private Coord end;
  private Map<Coord, Boolean> hitmap;

  /**
   * Instantiates a ship object
   *
   * @param ship type of ship
   * @param startToEnd a set of coords from start to end
   */
  public Ship(ShipType ship, CoordSet startToEnd) {
    this.ship = ship;
    this.startToEnd = startToEnd;
    this.start = startToEnd.getStart();
    this.end = startToEnd.getEnd();
    this.hitmap = new HashMap<>();
  }

  /**
   * Generates a hitmap of this ship
   */
  public void generateHitMap() {
    if (start.getX() == end.getX()) {
      for (int i = start.getY(); i <= end.getY(); i++) {
        Coord currCord = new Coord(start.getX(), i);
        hitmap.put(currCord, false);
      }
    } else if (start.getY() == end.getY()) {
      for (int i = start.getX(); i <= end.getX(); i++) {
        Coord currCord = new Coord(i, start.getY());
        hitmap.put(currCord, false);
      }
    }
  }

  /**
   * Gets the ship type
   *
   * @return ship type
   */
  public ShipType getShipType() {
    return this.ship;
  }

  /**
   * Gets the coordset of this ship
   *
   * @return coordset of ship
   */
  public CoordSet getStartToEnd() {
    return this.startToEnd;
  }

  /**
   * Gets the hitmap of the ship
   *
   * @return map of the ship
   */
  public Map<Coord, Boolean> getHitmap() {
    return this.hitmap;
  }

  /**
   * Checks to see if this ship is sunken
   *
   * @return boolean whether the ship is sunken
   */
  public boolean sunkenShip() {
    for (Map.Entry e : hitmap.entrySet()) {
      boolean hit = Boolean.TRUE.equals(e.getValue());
      if (!hit) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks to see whether the hitmap contains a certain coordinate
   *
   * @param coord coordinate to search the hitmap
   * @return whether coord is contained in the hitmap
   */
  public boolean containCoord(Coord coord) {
    if (hitmap.containsKey(coord)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Returns the direction of the ship
   *
   * @return direction of ship
   */
  public ShipDirection getDirection() {
    if (start.getX() == end.getX()) {
      return ShipDirection.VERTICAL;
    } else {
      return ShipDirection.HORIZONTAL;
    }
  }

  /**
   * Gets the length of ship
   *
   * @return length of ship
   */
  public int getLength() {
    int x1 = start.getX();
    int y1 = start.getY();
    int x2 = end.getX();
    int y2 = end.getY();

    if (x1 == x2) {
      return Math.abs(y1 - y2) + 1;
    } else {
      return Math.abs(x1 - x2) + 1;
    }
  }

}
