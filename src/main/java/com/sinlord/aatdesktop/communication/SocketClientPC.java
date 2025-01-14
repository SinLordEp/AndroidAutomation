package com.sinlord.aatdesktop.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author SINLORDEP
 */
public class SocketClientPC {
    private String host;
    private int port;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public SocketClientPC(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean connect() throws IOException {
        socket = new Socket(host, port);
        if(socket.isConnected()) {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected to Android service.");
            return true;
        }
        return false;
    }

    public void sendCommand(String command) throws IOException {
        out.println(command);
        String response = in.readLine();
        System.out.println("Received response: " + response);
    }

    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
        if (out != null) {
            out.close();
        }
        if (in != null) {
            in.close();
        }
    }
}
