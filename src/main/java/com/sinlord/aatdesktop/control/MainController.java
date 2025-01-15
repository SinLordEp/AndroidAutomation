package com.sinlord.aatdesktop.control;

import com.sinlord.aatdesktop.GUI.MainUI;
import com.sinlord.aatdesktop.http.SocketClientPC;
import com.sinlord.aatdesktop.util.EventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SINLORDEP
 */
public class MainController {
    private final List<EventListener<String[]>> listeners = new ArrayList<>();
    public void run(){
        MainUI ui = new MainUI(this);
        ui.run();
    }

    public void onWindowClosing() {
        System.exit(0);
    }

    public void connectDUT(){
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

    public void addListener(EventListener<String[]> listener) {
        listeners.add(listener);
    }

    private void notifyListeners(String event, String[] data){
        for(EventListener<String[]> listener : listeners){
            listener.onEvent(event, data);
        }
    }
}
