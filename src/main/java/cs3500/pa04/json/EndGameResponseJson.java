package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an end-game response json
 *
 * @param name method name
 * @param arguments arguments
 */
public record EndGameResponseJson(
    @JsonProperty("method-name") String name,
    @JsonProperty("arguments") Arguments arguments) {
  /**
   * Represents arguments
   */
  public record Arguments(){}
}
