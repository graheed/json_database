package server;

/**
 * Represents a utility class for generating JSON response strings.
 */
public class JsonResponse {

    /**
     * JSON response string for indicating an error with a reason.
     */
    public static final String ERROR = "{\"response\":\"ERROR\",\"reason\":\"No such key\"}";

    /**
     * JSON response string for indicating a successful response.
     */
    public static final String OK = "{\"response\":\"OK\"}";

    /**
     * Formats a JSON response string for a successful response with an optional value.
     *
     * @param value The value to be included in the response (can be null).
     * @param map   A flag indicating whether the response should be formatted as a map.
     * @return A formatted JSON response string.
     */
    public static String formatOk(final String value, final boolean map) {
        if (map) {
            StringBuilder response = new StringBuilder("{\"response\":\"OK\"");
            response.append(",");
            response.append("\"value\":");
            response.append(value + "}");
            return response.toString();
        }
        StringBuilder response = new StringBuilder("{\"response\":\"OK\"");
        response.append(",");
        response.append("\"value\":\"");
        response.append(value + "\"}");
        return response.toString();
    }
}
