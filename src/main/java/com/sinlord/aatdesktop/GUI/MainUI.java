package com.sinlord.aatdesktop.GUI;

import com.sinlord.aatdesktop.control.MainController;
import com.sinlord.aatdesktop.util.EventListener;

import javax.swing.*;

/**
 * @author SINLORDEP
 */
public class MainUI implements EventListener<String[]> {
    private final MainController controller;
    private JButton button_connectDUT;
    private JButton button_connectREF;
    private JTextArea textArea_DUT;
    private JTextArea textArea_REF;
    private JButton button_refreshDUT;
    private JButton button_refreshREF;
    private JButton button_addPlan;
    private JButton button_updatePlan;
    private JButton button_deletePlan;
    private JButton button_testSwitch;
    private JButton button_refreshPlan;
    private JButton button_refreshHistory;
    private JButton button_loadHistory;
    private JButton button_deleteHistory;
    private JLabel label_testplan;
    private JLabel label_history;
    private JLabel label_current;
    private JTable table_plan;
    private JTable table_history;
    private JTable table_current;
    private JLabel label_selectedPlan;
    private JButton button_lockPlan;
    private JTextField textField_PortDUT;
    private JTextField textField_PortREF;

    public MainUI(MainController controller) {
        this.controller = controller;

    }

    public void run(){

    }

    @Override
    public void onEvent(String event, String[] data) {

    }
}
