package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents a report-damage json
 *
 * @param coordinates list of coord jsons
 */
public record ReportDamageJson(
    @JsonProperty("coordinates")List<CoordJson> coordinates) {
}
