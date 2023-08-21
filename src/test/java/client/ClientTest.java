package client;

import org.junit.jupiter.api.*;

import org.junit.jupiter.api.extension.ExtendWith;


import org.mockito.junit.jupiter.MockitoExtension;
import server.Database;
import server.Server;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.*;
import java.net.Socket;


@ExtendWith(MockitoExtension.class)
class ClientTest {


    Socket socket = mock(Socket.class);
    DataOutputStream dos = mock(DataOutputStream.class);
    DataInputStream dis = mock(DataInputStream.class);
    Database db;
    Server server;
    private final int severPort = 15101;
    private final String serverAddress = "127.0.0.1";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    Client client;

    @BeforeEach
    void setUp() throws IOException {
        db = new Database("src/main/java/server/data/db.json");
        server = new Server(severPort, db);
        client =  new Client(server.getServerPort(), serverAddress);
        client.setSocket(socket);

    }

    @AfterEach
    void stopSockets() throws IOException {
        server.stopServer();

    }

    @DisplayName("Client Test - Get Associated Server Name")
    @Test
    void getSeverAddress() throws IOException {

        //when
        String serverAddress = client.getSeverAddress();

        //then
        assertEquals("127.0.0.1", serverAddress);


    }

    @DisplayName("Client Test - Start Client Socket")
    @Test
    void startUp() {
        //given
        System.setOut(new PrintStream(outContent));
        String expectedString = "Client started!\n";

        //when
        client.startUp();
        String actualString = outContent.toString();

        //then
        assertEquals(expectedString, actualString);

    }

    @DisplayName("Client Test - Stop Client Socket")
    @Test
    void shutDown() throws IOException {
        String expectedString = "Client stopped!\n";
        client.startUp();
        System.setOut(new PrintStream(outContent));

        //when
        client.shutDown();
        String actualString = outContent.toString();

        //then
        assertEquals(expectedString, actualString);
    }
    @Test
    void sendMessage() throws IOException {
        //given
        //client.setSocket(socket);
        client.setOutput(dos);
        String messageSent = "Client Response Message";

        //when
        boolean actualBoolean = client.sendMessage(messageSent);

        //then
        assertTrue(actualBoolean);
        verify(dos).writeUTF(messageSent);
    }

    @Test
    void receiveMessage() throws IOException {
        //given
        //client.setSocket(socket);
        client.setInput(dis);
        when(dis.readUTF()).thenReturn("Test Server Message");
        String expectedMessage = "Test Server Message";

        //when
        client.receiveMessage();

        //then
        assertEquals(expectedMessage, client.getLastMessage());
        verify(dis).readUTF();
    }


}