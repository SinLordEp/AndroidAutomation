package com.sinlord.aatdesktop;

import com.sinlord.aatdesktop.control.MainController;
import com.sinlord.aatdesktop.http.SocketClientPC;

import java.io.IOException;
import java.util.Properties;

/**
 * @author SINLORDEP
 */
public class principal {
    private static final Properties PROPERTIES = new Properties();
    public static void main(String[] args) {
        initializeProperties();
        MainController controller = new MainController();
        controller.run();
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

    private static void initializeProperties() {
        try{
            PROPERTIES.load(principal.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}


