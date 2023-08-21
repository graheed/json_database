/**
 * The Main class represents the entry point of the client application.
 * It allows users to interact with a server using various commands.
 * The class uses JCommander library to parse command-line arguments.
 * <p>
 * Command-line options:
 * -t : Specifies the request type (set, get, delete, exit).
 * -k : Specifies the operation key for set, get, or delete requests.
 * -v : Specifies the operation value for the set request.
 * -in: Specifies the file path for input data.
 * <p>
 * Usage:
 * java Main -t <requestType> -k <operationKey> -v <operationValue> -in <filePath>
 * <p>
 * Example:
 * java Main -t set -k key1 -v value1   // Sends a set request with key "key1" and value "value1".
 * java Main -t get -k key1            // Sends a get request with key "key1".
 * java Main -t delete -k key1         // Sends a delete request with key "key1".
 * java Main -t exit                  // Sends an exit request to terminate the client.
 * <p>
 * If a file path is provided with -in option, the content of the file is used as the message.
 * Otherwise, the requestType and operationKey/operationValue are used to generate the message.
 * <p>
 * The Main class creates a Client instance, connects to the server, sends the request message,
 * receives the response message, and shuts down the client.
 * <p>
 * Dependencies:
 * - com.beust.jcommander.JCommander: For parsing command-line arguments.
 * - com.google.gson.Gson: For JSON serialization and deserialization.
 * - server.Database: Represents the server's database.
 * - server.Server: Represents the server for handling client requests.
 * - Client: Custom class for client communication with the server.
 *
 * @see Client
 * @see com.beust.jcommander.JCommander
 * @see com.google.gson.Gson
 * @see server.Database
 * @see server.Server
 * @version 1.0
 * @since 2023-08-03
 */


package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Main {
    @Parameter(names = {"-t"})
    String requestType = "";
    @Parameter(names = {"-k"})
    String operationKey = "";
    @Parameter(names = {"-v"})
    String operationValue = "";
    @Parameter(names = {"-in"})
    String filePath = "";


    /**
     * The main method to start the client application.
     * It initializes the Main instance, parses command-line arguments using JCommander,
     * and then executes the run method.
     *
     * @param args The command-line arguments.
     * @throws IOException If an I/O error occurs while reading input data.
     */
    public static void main(String[] args) throws IOException {
        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);
        main.run();
    }

    /**
     * The run method executes the client logic.
     * It creates a new Client instance, connects to the server at "127.0.0.1:15000",
     * and sends a request message to the server. The message content depends on the provided
     * command-line arguments or input file (if specified). The client then receives the response
     * message from the server and shuts down the client connection.
     *
     * @throws IOException If an I/O error occurs during client-server communication.
     */
    public void run() throws IOException {
        Client client = new Client(15000, "127.0.0.1");
        client.startUp();

        String message = "";
        if (!filePath.isEmpty()) {
            try {
                String filePathFixed = "src/client/data/" + filePath;
                message = Files.readString(Paths.get(filePathFixed));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if ("set".equals(requestType)) {
                message = JsonRequest.setRequest(operationKey, operationValue);
            }
            if ("get".equals(requestType)) {
                message = JsonRequest.getRequest(operationKey);
            }
            if ("delete".equals(requestType)) {
                message = JsonRequest.deleteRequest(operationKey);
            }
            if ("exit".equals(requestType)) {
                message = "{\"type\":\"exit\"}";
            }
        }
        client.sendMessage(message);

        client.receiveMessage();

        client.shutDown();
    }
}
