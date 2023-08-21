package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Represents a server that listens for incoming connections and handles client sessions.
 */
public class Server extends ServerDevice {
    private ServerSocket server; // The server socket for accepting client connections


    private boolean keepRunning; // Flag to control the server's running state
    private final Database db; // The database used for storing and retrieving data


    /**
     * Constructs a Server object.
     *
     * @param serverPort The port on which the server will listen for incoming connections.
     * @param db The database instance used for data storage and retrieval.
     * @throws IOException If an I/O error occurs while setting up the server socket.
     */


    public Server(final int serverPort, final Database db) {
        super(serverPort); // Initialize the parent class with the server port
        this.db = db;
        try {
            server = new ServerSocket(super.getServerPort()); // Create a server socket
        } catch (IOException e) {
            //
        }

        //setServer(server); // Set the server socket
        //System.out.println("Server started!");
        keepRunning = true; // Start the server in running state
    }

    /**
     * Collapses while loop that keeps server running.
     */
    public void setKeepRunning(final boolean keepRunning) {
        this.keepRunning = keepRunning;
    }

    /**
     * Retrieves the server socket instance.
     *
     * @return The ServerSocket instance associated with this server.
     */
    public ServerSocket getServer() {
        return server;
    }

    /**
     * Starts the server and listens for incoming client connections.
     * Creates a thread pool to handle client sessions concurrently.
     */
    public void startUp() {
        System.out.println("Server started!");
        int poolSize = Runtime.getRuntime().availableProcessors(); // Calculate thread pool size
        ExecutorService executor = Executors.newFixedThreadPool(poolSize); // Create a thread pool

        while (keepRunning) {
            try {
                Session session = new Session(db, server.accept(), this); // Accept a client connection
                executor.submit(session); // Submit the session for execution in the thread pool
            } catch (IOException socketExcept) {
                 //
            }
        }
    }

    /**
     * Stops the server by closing the server socket and ending the main loop.
     *
     * @throws IOException If an I/O error occurs while closing the server socket.
     */
    public void stopServer() throws IOException {
        keepRunning = false; // Set the running state to false
        getServer().close(); // Close the server socket
        System.out.println("Server stopped!");
    }
}
