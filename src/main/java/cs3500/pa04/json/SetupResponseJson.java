package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.Ship;
import java.util.List;


/**
 * Represents a JSON response to server's setup message
 */
public record SetupResponseJson(
    @JsonProperty("method-name") String name,
    @JsonProperty("arguments") Arguments arguments) {
  /**
   * Represents arguments
   *
   * @param fleet list of ship jsons
   */
  public record Arguments(
      @JsonProperty("fleet") List<ShipJson> fleet) {
  }
}
