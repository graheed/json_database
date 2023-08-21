package client;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonRequestTest {

    @Test
    public void testGettersAndSetters() {
        JsonRequest request = new JsonRequest("type", new String[]{"key"}, "value");

        assertEquals("type", request.getType());
        assertArrayEquals(new String[]{"key"}, request.getKey());
        assertEquals("value", request.getValue());

        request.setType("newType");
        request.setKey(new String[]{"newKey"});
        request.setValue("newValue");

        assertEquals("newType", request.getType());
        assertArrayEquals(new String[]{"newKey"}, request.getKey());
        assertEquals("newValue", request.getValue());
    }

    @Test
    public void testSetRequest() {
        String key = "someKey";
        String value = "someValue";

        String expected = "{ \"type\":\"set\",\"key\":\"" + key + "\",\"value\":\"" + value + "\"}";
        String actual = JsonRequest.setRequest(key, value);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetRequest() {
        String key = "someKey";

        String expected = "{ \"type\":\"get\",\"key\":\"" + key + "\"}";
        String actual = JsonRequest.getRequest(key);

        assertEquals(expected, actual);
    }

    @Test
    public void testDeleteRequest() {
        String key = "someKey";

        String expected = "{ \"type\":\"delete\",\"key\":\"" + key + "\"}";
        String actual = JsonRequest.deleteRequest(key);

        assertEquals(expected, actual);
    }
}
