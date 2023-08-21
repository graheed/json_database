package server;

import client.JsonRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.Generated;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionTest {
    Server server = mock(Server.class);
    Socket clientSocket = mock(Socket.class);

    Database db = mock(Database.class);

    DataOutputStream dos = mock(DataOutputStream.class);
    DataInputStream dis = mock(DataInputStream.class);
    Session session;

    @BeforeEach
    void setUp() {
        session = new Session(db, clientSocket, server);
        session.setInput(dis);
        session.setOutput(dos);
    }
    @Test
    void getInput() {
        //given
        DataInputStream actualDIS;

        //when
        actualDIS = session.getInput();

        //then
        assertEquals(dis, actualDIS);
    }

    @Test
    void setInput() {
        //given
        DataInputStream actualDIS;

        //when
        actualDIS = session.getInput();

        //then
        assertNotNull(actualDIS);
    }

    @Test
    void getOutput() {
        //given
        DataOutputStream actualDOS;

        //when
        actualDOS = session.getOutput();

        //then
        assertEquals(dos, actualDOS);
    }

    @Test
    void setOutput() {
        //given
        DataOutputStream actualDOS;

        //when
        actualDOS = session.getOutput();

        //then
        assertNotNull(actualDOS);
    }
    
    @Test
    void run() throws IOException {
        //given
        when(clientSocket.getInputStream()).thenReturn(dis);
        when(clientSocket.getOutputStream()).thenReturn(dos);

        //when
        assertThrows(NullPointerException.class, () -> session.run());
    }

    @Test
    void handleCommunicationSet() throws IOException {
        //given

        when(dis.readUTF()).thenReturn(JsonRequest.setRequest("People", "Are Beautiful"));

        //when
        session.handleCommunication(dis, dos);

        //then
        verify(dos).writeUTF(any());
    }

    @Test
    void handleCommunicationGet() throws IOException {
        //given

        when(dis.readUTF()).thenReturn(JsonRequest.getRequest("People"));

        //when
        session.handleCommunication(dis, dos);

        //then
        verify(dos).writeUTF(any());
    }

    @Test
    void handleCommunicationDelete() throws IOException {
        //given
        when(dis.readUTF()).thenReturn(JsonRequest.deleteRequest("People"));

        //when
        session.handleCommunication(dis, dos);

        //then
        verify(dos).writeUTF(any());
    }

    @Test
    void handleCommunicationExit() throws IOException {
        //given
        when(dis.readUTF()).thenReturn("{\"type\":\"exit\"}");

        //when
        session.handleCommunication(dis, dos);

        //then
        verify(dos).writeUTF(any());
    }
}