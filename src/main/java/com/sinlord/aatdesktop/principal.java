package com.sinlord.aatdesktop;

import com.sinlord.aatdesktop.communication.SocketClientPC;

/**
 * @author SINLORDEP
 */
public class principal {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8888;
        try {
            SocketClientPC client = new SocketClientPC(host, port);
            if(client.connect()){
                client.sendCommand("TEST_COMMAND");
                client.close();
            }else{
                System.out.println("Connection failed");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}


