package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Response to what server's join JSON
 */
public record JoinResponseJson(
    @JsonProperty("method-name") String name,
    @JsonProperty("arguments") Arguments arguments) {
  /**
   * Arguments to send back
   *
   * @param name our username
   * @param gameType type of game
   */
  public record Arguments(
      @JsonProperty("name") String name,
      @JsonProperty("game-type") String gameType) {

  }

}

