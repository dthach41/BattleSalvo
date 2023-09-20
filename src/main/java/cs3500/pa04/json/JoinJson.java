package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a join json
 *
 * @param methodName name of method
 */
public record JoinJson(
    @JsonProperty("method-name") String methodName) {
}
