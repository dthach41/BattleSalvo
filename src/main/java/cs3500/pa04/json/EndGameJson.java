package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.GameResult;

/**
 * Represents an end-game json
 *
 * @param result result of game
 * @param reason reason for game ending
 */
public record EndGameJson(
      @JsonProperty("result") GameResult result,
      @JsonProperty("reason") String reason) {
}
