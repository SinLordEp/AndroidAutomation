package com.sinlord.aatdesktop.control;

import com.sinlord.aatdesktop.GUI.MainUI;

/**
 * @author SINLORDEP
 */
public class MainController {
    public void run(){
        MainUI ui = new MainUI(this);
        ui.run();
    }
}
