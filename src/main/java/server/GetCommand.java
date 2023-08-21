package server;

/**
 * Represents a command for retrieving data from a database using a specified key.
 */
public class GetCommand implements Command {
    private Database db;       // The database instance to retrieve data from.
    private String[] key;      // The key used to retrieve data.
    private String results;    // Stores the results of the database query.

    /**
     * Constructs a new GetCommand object.
     *
     * @param db The database instance to retrieve data from.
     * @param key The key used to retrieve data.
     */
    public GetCommand(final Database db, final String[] key) {
        this.db = db;
        this.key = key;
    }

    /**
     * Retrieves the key used for the database query.
     *
     * @return The key for the database query.
     */
    public String[] getKey() {
        return key;
    }

    /**
     * Retrieves the results of the database query.
     *
     * @return The results of the database query.
     */
    public String getResults() {
        return results;
    }

    /**
     * Executes the GetCommand by querying the database with the specified key
     * and storing the results in the 'results' field.
     */
    @Override
    public void execute() {
        results = db.get(getKey());
    }
}
