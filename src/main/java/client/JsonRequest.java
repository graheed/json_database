package client;



/**
 * This class represents a JSON request with a type, key, and value.
 */
public class JsonRequest {
    private String type;
    private String[] key;
    private Object value;

    /**
     * Constructs a new JsonRequest with the specified type, key, and value.
     *
     * @param type  the type of the request
     * @param key   the key of the request
     * @param value the value of the request
     */
    public JsonRequest(final String type, final String[] key, final Object value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the type of this request.
     *
     * @return the type of this request
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of this request.
     *
     * @param type the new type of this request
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Returns the key of this request.
     *
     * @return the key of this request
     */
    public String[] getKey() {
        return key;
    }

    /**
     * Sets the key of this request.
     *
     * @param key the new key of this request
     */
    public void setKey(final String[] key) {
        this.key = key;
    }

    /**
     * Returns the value of this request.
     *
     * @return the value of this request
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the value of this request.
     *
     * @param value the new value of this request
     */
    public void setValue(final Object value) {
        this.value = value;
    }

    /**
     * Creates a set request with the specified key and value.
     *
     * @param key   the key for the set request
     * @param value the value for the set request
     * @return a string representation of a set request
     * with the specified key and value.
     */
    public static String setRequest(final String key, final String value) {
        StringBuilder response = new StringBuilder("{ \"type\":\"set\"");
        response.append(",");
        response.append("\"key\":\"");
        response.append(key).append("\"");
        response.append(",");
        response.append("\"value\":\"");
        response.append(value).append("\"}");
        return response.toString();
    }

    /**
     * Creates a get request with the specified key.
     *
     * @param key the key for the get request
     * @return a string representation of a get request with the specified key
     */
    public static String getRequest(final String key) {
        StringBuilder response = new StringBuilder("{ \"type\":\"get\"");
        response.append(",");
        response.append("\"key\":\"");
        response.append(key).append("\"}");
        return response.toString();
    }

    /**
     * Creates a delete request with the specified key.
     *
     * @param key the key for the delete request
     * @return a string representation of a delete
     * request with the specified key.
     */
    public static String deleteRequest(final String key) {
        StringBuilder response = new StringBuilder("{ \"type\":\"delete\"");
        response.append(",");
        response.append("\"key\":\"");
        response.append(key).append("\"}");
        return response.toString();
    }
}
