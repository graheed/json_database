/**
 * Represents a session between the server and a client.
 *
 * This class handles communication with a client through a provided socket. It receives and sends messages,
 * processes client requests, and interacts with a database.
 */
package server;

import client.JsonRequest;
import com.google.gson.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Session extends Thread {

    private final Socket socket;
    private DataOutputStream output;
    private DataInputStream input;

    private String lastMessage;
    private Database db;
    private Server server;

    /**
     * Constructs a new Session object.
     *
     * @param db The Database instance to interact with.
     * @param socketForClient The socket for communication with the client.
     * @param server The Server instance managing the session.
     */
    public Session(final Database db, final Socket socketForClient, final Server server) {
        this.socket = socketForClient;
        this.db = db;
        this.server = server;
    }

    /**
     * Gets the input stream associated with the socket.
     *
     * @return The DataInputStream object for reading client input.
     */
    public DataInputStream getInput() {
        return input;
    }

    /**
     * Sets the input stream for reading client input.
     *
     * @param input The DataInputStream object for reading client input.
     */
    public void setInput(final DataInputStream input) {
        this.input = input;
    }

    /**
     * Gets the output stream associated with the socket.
     *
     * @return The DataOutputStream object for sending data to the client.
     */
    public DataOutputStream getOutput() {
        return output;
    }

    /**
     * Sets the output stream for sending data to the client.
     *
     * @param output The DataOutputStream object for sending data to the client.
     */
    public void setOutput(final DataOutputStream output) {
        this.output = output;
    }

    /**
     * Starts the session by setting up input and output streams, and handling communication with the client.
     */
    @Override
    public void run() {
        try {
            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
            try {
                handleCommunication(getInput(), getOutput());
            } catch (IOException m) {
                //
            }
        } catch (IOException e) {
            //
        }
    }

    /**
     * Gets the last received message from the client.
     *
     * @return The last received message as a String.
     */
    public String getLastMessage() {
        return lastMessage;
    }

    /**
     * Sends a message to the client.
     *
     * @param message The message to be sent.
     * @return True if the message was sent successfully, false otherwise.
     * @throws IOException If an I/O error occurs while sending the message.
     */
    public boolean sendMessage(final String message) throws IOException {
        boolean operationSuccess = false;
        try {
            getOutput().writeUTF(message);
            System.out.println("Sent: " + message);
            operationSuccess = true;
        } catch (IOException e) {
            //
        }
        return operationSuccess;
    }

    /**
     * Receives a message from the client.
     *
     * @return True if the message was received successfully, false otherwise.
     */
    public boolean receiveMessage() {
        boolean operationSuccess = false;
        try {
            this.lastMessage = getInput().readUTF();
            System.out.println("Received: " + getLastMessage());
            operationSuccess = true;
        } catch (IOException e) {
            //
        }

        return operationSuccess;
    }

    /**
     * Handles communication with the client by processing JSON requests and executing corresponding commands.
     *
     * @param input The DataInputStream for reading client input.
     * @param output The DataOutputStream for sending data to the client.
     * @throws IOException If an I/O error occurs during communication.
     */
    public void handleCommunication(final DataInputStream input, final DataOutputStream output) throws IOException {
        Controller controller = new Controller();
        Command currentCommand = null;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(JsonRequest.class, new JsonRequestDeserializer())
                .create();
        JsonRequest jRequest;
        receiveMessage();

        jRequest = gson.fromJson(getLastMessage(), JsonRequest.class);

        switch (jRequest.getType()) {
            case "get":
                currentCommand = new GetCommand(db, jRequest.getKey());
                controller.setCommand(currentCommand);
                controller.executeCommand();
                sendMessage(((GetCommand) currentCommand).getResults());
                break;
            case "set":
                currentCommand = new SetCommand(db, jRequest.getKey(), jRequest.getValue());
                controller.setCommand(currentCommand);
                controller.executeCommand();
                sendMessage(((SetCommand) currentCommand).getResults());
                break;
            case "delete":
                currentCommand = new DeleteCommand(db, jRequest.getKey());
                controller.setCommand(currentCommand);
                controller.executeCommand();
                sendMessage(((DeleteCommand) currentCommand).getResults());
                break;
            case "exit":
                sendMessage("OK");
                server.stopServer();
                socket.close();
                break;
            default:
                break;
        }
    }
}
