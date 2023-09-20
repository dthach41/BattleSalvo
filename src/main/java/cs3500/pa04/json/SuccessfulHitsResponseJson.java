package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a success-hit response json
 *
 * @param name name of method
 * @param arguments arguments of method
 */
public record SuccessfulHitsResponseJson(
    @JsonProperty("method-name") String name,
    @JsonProperty("arguments") Arguments arguments) {
  /**
   * Represents arguments
   */
  public record Arguments(){}
}
