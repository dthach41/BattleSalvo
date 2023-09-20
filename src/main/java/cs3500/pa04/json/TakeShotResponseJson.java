package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.Coord;
import java.util.List;

/**
 * Represents a take-shot response JSON to send back to server
 */
public record TakeShotResponseJson(
    @JsonProperty("method-name") String name,
    @JsonProperty("arguments") Arguments arguments) {
  /**
   * represents arguments
   *
   * @param coordinates list of coord jsons
   */
  public record Arguments(
      @JsonProperty("coordinates") List<CoordJson> coordinates) {

  }

}
