package cs3500.pa04;

/**
 * Represents a type of JSON message expected to be received for battle salvo
 */
public enum BattleSalvoJsonMsg {
  /**
   * Represents join message
   */
  JOIN,
  /**
   * Represents setup message
   */
  SETUP,
  /**
   * represents take-shots method
   */
  TAKE_SHOTS,
  /**
   * represents take-shots method
   */
  REPORT_DAMAGE,
  /**
   * represents successful-hits message
   */
  SUCCESSFUL_HITS,
  /**
   * represents end-game message
   */
  END_GAME

}
