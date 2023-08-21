package server;

/**
 * Represents a command for deleting data from a Database.
 */
public class DeleteCommand implements Command {
    private Database db;         // The Database instance to perform deletion on.
    private String[] key;        // The key(s) identifying the data to be deleted.
    private String results;      // Holds the results of the deletion operation.

    /**
     * Constructs a DeleteCommand object.
     *
     * @param db The Database instance on which the deletion operation will be performed.
     * @param key An array of strings representing the key(s) of the data to be deleted.
     */
    public DeleteCommand(final Database db, final String[] key) {
        this.db = db;
        this.key = key;
    }

    /**
     * Retrieves the key(s) associated with the deletion operation.
     *
     * @return An array of strings representing the key(s).
     */
    public String[] getKey() {
        return key;
    }

    /**
     * Retrieves the results of the deletion operation.
     *
     * @return A string containing the results of the deletion operation.
     */
    public String getResults() {
        return results;
    }

    /**
     * Executes the deletion operation on the specified Database instance using the provided key(s).
     * The results of the operation are stored in the 'results' field.
     */
    @Override
    public void execute() {
        results = db.delete(getKey());
    }
}
