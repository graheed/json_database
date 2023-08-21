package server;

import java.io.IOException;

/**
 * The main class that initializes and starts the server with a specified database.
 */
public class Main {

    /**
     * The main method that serves as the entry point of the application.
     *
     * @param args The command line arguments (not used in this application).
     * @throws IOException If an I/O error occurs while interacting with the database or starting the server.
     */
    public static void main(String[] args) throws IOException {
        // Create a new instance of the Database class, specifying the JSON database file path.
        Database db = new Database("src/main/java/server/data/db.json");

        // Create a new instance of the Server class, passing the port number and the database instance.
        Server server = new Server(15000, db);

        // Start up the server.
        server.startUp();
    }
}
