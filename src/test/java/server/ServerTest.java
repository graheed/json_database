package server;

import client.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {
    Database db;
    Server server;
    private final int severPort = 15101;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() throws IOException {
        db = new Database("JSON Database (Java)/task/src/server/data/db.json");
        server = new Server(severPort, db);

    }

    @AfterEach
    void stopSockets() throws IOException {
        server.stopServer();

    }
    @Test
    void getServer() {
        //given
        ServerSocket serverSocket = server.getServer();

        //then
        assertNotNull(serverSocket);
    }

    @Test
    void startUp() throws IOException {
        //given
        System.setOut(new PrintStream(outContent));
        String expectedString = "Server started!\n";
        server.setKeepRunning(false);

        //when
        server.startUp();
        String actualString = outContent.toString();

        //then
        assertEquals(expectedString, actualString);
    }

    @Test
    void stopServer() throws IOException {
        //given
        String expectedString = "Server stopped!\n";
        server.setKeepRunning(false);
        server.startUp();
        System.setOut(new PrintStream(outContent));

        //when
        server.stopServer();
        String actualString = outContent.toString();

        //then
        assertEquals(expectedString, actualString);
    }
}