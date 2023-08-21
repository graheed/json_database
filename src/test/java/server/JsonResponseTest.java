package server;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonResponseTest {

    @Test
    public void testErrorJsonResponse() {
        assertEquals("{\"response\":\"ERROR\",\"reason\":\"No such key\"}", JsonResponse.ERROR);
    }

    @Test
    public void testOkJsonResponse() {
        assertEquals("{\"response\":\"OK\"}", JsonResponse.OK);
    }

    @Test
    public void testFormatOkWithValue() {
        String value = "someValue";

        String expected = "{\"response\":\"OK\",\"value\":\"" + value + "\"}";
        String actual = JsonResponse.formatOk(value, false);

        assertEquals(expected, actual);
    }
    @Test
    public void testFormatOkWithMapAndValue() {
        String value = "{\"key\":\"value\"}";

        String expected = "{\"response\":\"OK\",\"value\":" + value + "}";
        String actual = JsonResponse.formatOk(value, true);

        assertEquals(expected, actual);
    }

}
