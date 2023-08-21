package server;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DatabaseTest {
    private Database database;
    ArrayList<Integer> stringArray;
    static String[] setKey;
    static String setString;
    static Map<String, String> setObject;

    @BeforeAll
    static void firstSetUp() {
        setKey = new String[]{"People", "Fall", "In", "Love"};
        setString = "In Mysterious Ways";
        setObject = new HashMap<>();
        setObject.put("In Mysterious Ways","Maybe just the touch of a hand");
    }


    @BeforeEach
    public void setUp() throws IOException {
        database = new Database("src/main/java/server/data/db.json");
    }




    @Test
    public void testGetters() {
        assertNotNull(database.getDatabase());
        assertNotNull(database.getGson());
    }

    @Test
    public void testSetDatabase() {
        Map<String, Object> newDatabase = new HashMap<>();
        newDatabase.put("key", "value");
        database.setDatabase(newDatabase);

        assertEquals(newDatabase, database.getDatabase());
    }


    @Test
    void setString() {
        //given
        String expectedValue = "{\"response\":\"OK\"}";

        //when
        String actualValue = database.set(setKey, setString);

        //then
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void setObject() {
        //given
        String expectedValue = "{\"response\":\"OK\"}";

        //when
        String actualValue = database.set(setKey, setObject);

        //then
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void getString() {
        //given
        database.set(setKey, setString);
        String[] getKey = new String[]{"People", "Fall", "In", "Love"};
        String expectedValue = "{\"response\":\"OK\",\"value\":\"In Mysterious Ways\"}";

        //when
        String actualValue = database.get(getKey);

        //then
        assertEquals(expectedValue, actualValue);
    }


    @Test
    void getObject() {
        //given
        database.set(setKey, setObject);
        String[] getKey = new String[]{"People", "Fall", "In", "Love"};
        String expectedValue = "{\"response\":\"OK\",\"value\":{\"In Mysterious Ways\":\"Maybe just the touch of a hand\"}}";

        //when
        String actualValue = database.get(getKey);

        //then
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void getNull() {
        //given
        database.set(setKey, setString);
        String[] getKey = new String[]{"People", "Fall", "In", "Love","Song"};
        String expectedValue = "{\"response\":\"ERROR\",\"reason\":\"No such key\"}";

        //when
        String actualValue = database.get(getKey);

        //then
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void deleteString() {
        //given
        database.set(setKey, setString);
        String[] deleteKey = new String[]{"People", "Fall", "In", "Love"};
        String expectedValue = "{\"response\":\"OK\"}";

        //when
        String actualValue = database.delete(deleteKey);

        //then
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void deleteObject() {
        //given
        database.set(setKey, setObject);
        String[] deleteKey = new String[]{"People", "Fall", "In", "Love"};
        String expectedValue = "{\"response\":\"OK\"}";

        //when
        String actualValue = database.delete(deleteKey);

        //then
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void deleteNull() {
        //given
        database.set(setKey, setString);
        String[] deleteKey = new String[]{"People", "Fall", "In", "Love","Song"};
        String expectedValue = "{\"response\":\"ERROR\",\"reason\":\"No such key\"}";

        //when
        String actualValue = database.delete(deleteKey);

        //then
        assertEquals(expectedValue, actualValue);
    }
}