package server;

/**
 * Represents a command for setting a value in a database.
 */
public class SetCommand implements Command {

    private Database db;         // The database instance to perform the set operation on.
    private String[] key;        // The key or keys associated with the value to be set.
    private Object value;        // The value to be set.
    private String results;      // The result of the set operation.

    /**
     * Constructs a SetCommand with the specified parameters.
     *
     * @param db The Database instance to perform the set operation on.
     * @param key The key or keys associated with the value to be set.
     * @param value The value to be set.
     */
    public SetCommand(final Database db, final String[] key, final Object value) {
        this.db = db;
        this.key = key;
        this.value = value;
    }

    /**
     * Retrieves the key associated with the value to be set.
     *
     * @return The key as an array of strings.
     */
    public String[] getKey() {
        return key;
    }

    /**
     * Retrieves the value to be set.
     *
     * @return The value object.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Retrieves the results of the set operation.
     *
     * @return The results as a string.
     */
    public String getResults() {
        return results;
    }

    /**
     * Executes the set operation on the database using the provided key and value.
     * Updates the results field with the outcome of the operation.
     */
    @Override
    public void execute() {
        this.results = this.db.set(getKey(), getValue());
    }
}
