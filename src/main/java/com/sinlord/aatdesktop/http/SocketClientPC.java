package com.sinlord.aatdesktop.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author SINLORDEP
 */
public class SocketClientPC {
    private final String host;
    private final int port;
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
            return true;
        }
        return false;
    }

    public String sendCommand(String command) throws IOException {
        out.println(command);
        StringBuilder result = new StringBuilder();
        String response;
        while((response = in.readLine()) != null && !response.isEmpty() && !"END_OF_RESPONSE".equals(response)) {
            result.append(response).append("\n");
        }
        return result.toString();
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
