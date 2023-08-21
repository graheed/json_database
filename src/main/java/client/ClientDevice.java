/**
 * Represents an abstract NetworkDevice that can connect to
 * a server using sockets and perform message communication.
 * This class provides the basic functionality for sending and
 * receiving messages through a network connection.
 */
package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public abstract class ClientDevice {

    // Fields
    private final int serverPort; // The port number of the server to connect to.
    private Socket socket; // The socket for communication with the server.
    private DataOutputStream output; // Output stream to send messages to the server.
    private DataInputStream input; // Input stream to receive messages from the server.
    private String lastMessage; // Holds the last received message.

    /**
     * Creates a new NetworkDevice with the specified server port number.
     *
     * @param serverPort The port number of the server to connect to.
     */
    public ClientDevice(final int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * Returns the port number of the server that this NetworkDevice is connecting to.
     * @return The server port number.
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * Returns the socket used for communication with the server.
     *
     * @return The socket used for communication.
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Sets the socket used for communication with the server.
     *
     * @param socket The socket to set.
     */
    public void setSocket(final Socket socket) {
        this.socket = socket;
    }

    /**
     * Returns the DataInputStream used for receiving messages from the server.
     *
     * @return The DataInputStream used for receiving messages.
     */
    public DataInputStream getInput() {
        return input;
    }

    /**
     * Sets the DataInputStream used for receiving messages from the server.
     *
     * @param input The DataInputStream to set.
     */
    public void setInput(final DataInputStream input) {
        this.input = input;
    }

    /**
     * Returns the DataOutputStream used for sending messages to the server.
     *
     * @return The DataOutputStream used for sending messages.
     */
    public DataOutputStream getOutput() {
        return output;
    }

    /**
     * Sets the DataOutputStream used for sending messages to the server.
     *
     * @param output The DataOutputStream to set.
     */
    public void setOutput(final DataOutputStream output) {
        this.output = output;
    }

    /**
     * Returns the last received message from the server.
     *
     * @return The last received message.
     */
    public String getLastMessage() {
        return lastMessage;
    }

    /**
     * An abstract method to be implemented by subclasses
     * to perform any necessary startup operations.
     *
     * This method will be called after the connection to the server is established.
     */
    abstract void startUp();

    /**
     * Sends a message to the server.
     *
     * @param message The message to be sent.
     * @return True if the message is sent successfully; false otherwise.
     * @throws IOException if an I/O error occurs while sending the message.
     */
    public boolean sendMessage(final String message) throws IOException {
        boolean operationSuccess = false;
        try {
            System.out.println("Sent: " + message);
            getOutput().writeUTF(message);
            operationSuccess = true;
        } catch (IOException e) {
            //
        }
        return operationSuccess;
    }

    /**
     * Receives a message from the server.
     *
     * @return True if a message is received successfully; false otherwise.
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
}
