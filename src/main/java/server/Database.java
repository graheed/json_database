package server;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Database {
    private Map<String, Object> database;
    private Gson gson;
    private File databaseFile;
    private ReadWriteLock lock;
    private Lock readLock;
    private Lock writeLock;

    public Database(String databaseFilePath) throws IOException {
        this.database = new HashMap<>();
        this.gson = new Gson();
        this.databaseFile = new File(databaseFilePath);
        lock = new ReentrantReadWriteLock();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
        loadData();
    }

    public Map<String, Object> getDatabase() {
        return database;
    }

    public void setDatabase(Map<String, Object> db) {
        this.database = db;
    }

    public Gson getGson() {
        return gson;
    }


    public String set( final String[] keys, final Object value) {

        writeLock.lock();
        try {

            setValueForNestedJson(getDatabase(), keys, value);
            saveData();
            return JsonResponse.OK;
        } finally {
            writeLock.unlock();
        }
    }


    public String get(String[] keys) {
        readLock.lock();

        try {
            Object response = getValueForNestedJson(getDatabase(), keys);
            if (response == null) {
                return JsonResponse.ERROR;
            } else {
                if (response instanceof Map) {
                    String jsonString = new Gson().toJson(response);

                    return JsonResponse.formatOk((jsonString), true);
                }

                return JsonResponse.formatOk((String) response, false);
            }
        } finally {
            readLock.unlock();
        }
    }

    public String delete(String[] keys) {
        writeLock.lock();
        try {
            boolean success = deleteNestedKey(getDatabase(), keys);
            saveData();
            if (success) {
                return JsonResponse.OK;
            } else {
                return JsonResponse.ERROR;
            }
        } finally {
            writeLock.unlock();
        }
    }

    private void loadData() throws IOException {

        try (Reader reader = new FileReader(databaseFile)) {
            setDatabase(gson.fromJson(reader, HashMap.class));
            /*if (getDatabase() == null) {
                data =
            }*/
        } catch (IOException ignored) {
        }
    }

    private void saveData() {
        try (Writer writer = new FileWriter(databaseFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(getDatabase(), writer);
        } catch (IOException e) {
            //
        }
    }

    private static void setValueForNestedJson(final Map<String, Object> data, final String[] nestedKeys, final Object value) {
        if (nestedKeys.length == 1) {
            data.put(nestedKeys[0], value);
        } else {
            String currentKey = nestedKeys[0];
            Map<String, Object> currentMap;
            if (data.containsKey(currentKey)) {
                currentMap = (Map<String, Object>) data.get(currentKey);
            } else {
                currentMap = new HashMap<>();
                data.put(currentKey, currentMap);
            }
            String[] remainingKeys = new String[nestedKeys.length - 1];
            System.arraycopy(nestedKeys,
                    1, remainingKeys,
                    0, nestedKeys.length - 1);
            setValueForNestedJson(currentMap, remainingKeys, value);
        }
    }

    private static Object getValueForNestedJson(final Map<String, Object> data, final String[] nestedKeys) {
        if (nestedKeys.length == 1) {
            return data.get(nestedKeys[0]);
        } else {
            String currentKey = nestedKeys[0];
            if (data.containsKey(currentKey) && data.get(currentKey) instanceof Map) {
                Map<String, Object> currentMap = (Map<String, Object>) data.get(currentKey);
                String[] remainingKeys = new String[nestedKeys.length - 1];
                System.arraycopy(nestedKeys, 1,
                        remainingKeys, 0,
                        nestedKeys.length - 1);
                return getValueForNestedJson(currentMap, remainingKeys);
            } else {
                return null;
            }
        }
    }

    private static boolean deleteNestedKey(final Map<String, Object> data, final String[] nestedKeys) {
        if (nestedKeys.length == 1) {
            return data.remove(nestedKeys[0]) != null;
        } else {
            String currentKey = nestedKeys[0];
            if (data.containsKey(currentKey)
                    && data.get(currentKey) instanceof Map) {
                Map<String, Object> currentMap = (Map<String, Object>) data.get(currentKey);
                String[] remainingKeys = new String[nestedKeys.length - 1];
                System.arraycopy(nestedKeys, 1,
                        remainingKeys, 0,
                        nestedKeys.length - 1);
                return deleteNestedKey(currentMap, remainingKeys);
            } else {
                return false;
            }
        }
    }



}
