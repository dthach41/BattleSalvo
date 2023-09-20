package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.Coord;
import java.util.List;

/**
 * Represents a report-damage response JSON to send back to server
 */
public record ReportDamageResponseJson(
    @JsonProperty("method-name") String name,
    @JsonProperty("arguments") Arguments arguments) {
  /**
   * Represents arguments
   *
   * @param coordinates list of coord jsons
   */
  public record Arguments(
      @JsonProperty("coordinates") List<CoordJson> coordinates) {

  }

}
