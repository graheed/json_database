package client;


import java.io.*;
import java.net.*;

/**
 * This class represents a client that extends the NetworkDevice class.
 */
public class Client extends ClientDevice {

    private final String severAddress;



    /**
     * Constructs a new Client with the specified server port and server address.
     *
     * @param serverPort    the port number of the server
     * @param serverAddress the address of the server
     */
    public Client(final int serverPort, final String serverAddress) {
        super(serverPort);
        severAddress = serverAddress;
    }

    /**
     * Returns the server address of this client.
     *
     * @return the server address of this client
     */
    public String getSeverAddress() {
        return severAddress;
    }

    /**
     * Starts up this client by creating a socket, input stream, and output stream.
     */
    public void startUp() {
        try {
            Socket socket = new Socket(getSeverAddress(), getServerPort());
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            super.setSocket(socket);
            super.setInput(input);
            super.setOutput(output);
            System.out.println("Client started!");
        } catch (IOException e) {
            //
        }
    }

    /**
     * Shuts down this client by closing its socket.
     *
     * @throws IOException if an I/O error occurs when closing the socket
     */
    public void shutDown() throws IOException {
        getSocket().close();
        System.out.println("Client stopped!");
    }
}
