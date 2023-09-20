package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Coord as a JSON
 */
public record CoordJson(
    @JsonProperty("x") int x,
    @JsonProperty("y") int y) {
}
