package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.ShipType;
import java.util.Map;

/**
 * Record to represent a Json message from server for set-up
 *
 * @param width width of board
 * @param height height of board
 * @param fleetSpec list of ships
 */
public record SetupJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") Map<ShipType, Integer> fleetSpec) {
}
