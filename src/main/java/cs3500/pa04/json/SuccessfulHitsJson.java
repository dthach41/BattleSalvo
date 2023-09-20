package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents a successful-hits json
 *
 * @param coordinates list of coord jsons
 */
public record SuccessfulHitsJson(
    @JsonProperty("coordinates") List<CoordJson> coordinates) {
}
