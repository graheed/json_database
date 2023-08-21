package server;

import java.io.IOException;
import java.net.Socket;

/**
 * The abstract base class for server devices that communicate over sockets.
 */
public abstract class ServerDevice {
    private final int serverPort;
    private Socket socket;

    /**
     * Constructs a ServerDevice object with the specified server port.
     *
     * @param serverPort The port number on which the server device will operate.
     */
    public ServerDevice(final int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * Retrieves the port number on which the server device is operating.
     *
     * @return The port number.
     */
    public int getServerPort() {
        return serverPort;
    }


    /**
     * Abstract method to start up the server device, initializing necessary resources and connections.
     *
     * @throws IOException If an I/O error occurs during startup.
     */
    abstract void startUp() throws IOException;
}
