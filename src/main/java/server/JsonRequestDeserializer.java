package server;

import client.JsonRequest;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * This class is responsible for deserializing JSON representations of {@link JsonRequest} objects.
 * It implements the {@link JsonDeserializer} interface and provides the necessary logic to
 * deserialize JSON elements into instances of the {@link JsonRequest} class.
 */
public class JsonRequestDeserializer implements JsonDeserializer<JsonRequest> {

    /**
     * Deserializes a JSON element into a {@link JsonRequest} object.
     *
     * @param json    The JSON element to be deserialized.
     * @param typeOfT The type of the object to deserialize to.
     * @param context The context for deserialization, providing additional information and utility methods.
     * @return A {@link JsonRequest} object deserialized from the provided JSON element.
     * @throws JsonParseException If an error occurs during JSON deserialization.
     */
    @Override
    public JsonRequest deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        Object value = null;
        if (jsonObject.has("value")) {
            JsonElement valueElement = jsonObject.get("value");
            if (valueElement.isJsonObject()) {
                value = context.deserialize(valueElement, Map.class);
            } else {
                value = valueElement.getAsString();
            }
        }
        String[] key = null;
        if (jsonObject.has("key")) {
            JsonElement keyElement = jsonObject.get("key");
            if (keyElement.isJsonArray()) {
                key = context.deserialize(keyElement, String[].class);
            } else {
                key = new String[]{keyElement.getAsString()};
            }
        }
        return new JsonRequest(type, key, value);
    }
}
