package com.sinlord.aatdesktop;

import com.sinlord.aatdesktop.control.MainController;


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
    }

    private static void initializeProperties() {
        try{
            PROPERTIES.load(principal.class.getResourceAsStream("/default.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String property){
        return PROPERTIES.getProperty(property);
    }

}


