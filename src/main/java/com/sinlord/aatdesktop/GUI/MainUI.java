package com.sinlord.aatdesktop.GUI;

import com.sinlord.aatdesktop.control.MainController;
import com.sinlord.aatdesktop.util.EventListener;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.sinlord.aatdesktop.principal.getProperty;

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
    private JButton button_startTest;
    private JButton button_cancelTest;
    private JButton button_pauseTest;
    private JButton button_refreshPlan;
    private JButton button_refreshHistory;
    private JButton button_loadHistory;
    private JButton button_deleteHistory;
    private JLabel label_testplan;
    private JLabel label_history;
    private JLabel label_current;
    private JTable table_history;
    private JTable table_current;
    private JTextField textField_PortDUT;
    private JTextField textField_PortREF;
    private JLabel label_portDUT;
    private JLabel label_portREF;
    private JButton button_folderHistory;
    private JButton button_clearLog;
    private JPanel panel_main;
    private JTextPane textPane_log;
    private JComboBox comboBox_plan;
    private JButton button_language;

    public MainUI(MainController controller) {
        this.controller = controller;
        controller.addListener(this);
    }

    public void run() {
        initialize();
        JFrame frame = new JFrame(UIText.getDialog().getText("frame_title"));
        frame.setContentPane(panel_main);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.onWindowClosing();
            }
        });
    }

    private void initialize() {
        setUIText();
    }

    private void setUIText(){
        button_connectDUT.setText(UIText.getDialog().getText("button_connectDUT"));
        button_connectREF.setText(UIText.getDialog().getText("button_connectREF"));
        button_refreshDUT.setText(UIText.getDialog().getText("button_refresh"));
        button_refreshREF.setText(UIText.getDialog().getText("button_refresh"));
        button_addPlan.setText(UIText.getDialog().getText("button_add"));
        button_updatePlan.setText(UIText.getDialog().getText("button_update"));
        button_deletePlan.setText(UIText.getDialog().getText("button_delete"));
        button_pauseTest.setText(UIText.getDialog().getText("button_pauseTest"));
        button_refreshPlan.setText(UIText.getDialog().getText("button_refresh"));
        button_refreshHistory.setText(UIText.getDialog().getText("button_refresh"));
        button_loadHistory.setText(UIText.getDialog().getText("button_load"));
        button_deleteHistory.setText(UIText.getDialog().getText("button_delete"));
        button_startTest.setText(UIText.getDialog().getText("button_start"));
        button_cancelTest.setText(UIText.getDialog().getText("button_cancel"));
        button_pauseTest.setText(UIText.getDialog().getText("button_pause"));
        button_folderHistory.setText(UIText.getDialog().getText("button_folderHistory"));
        button_clearLog.setText(UIText.getDialog().getText("button_clearLog"));
        button_language.setText(UIText.getDialog().getText("button_language"));
        label_testplan.setText(UIText.getDialog().getText("label_testplan"));
        label_history.setText(UIText.getDialog().getText("label_history"));
        label_current.setText(UIText.getDialog().getText("label_current"));
        label_portDUT.setText(UIText.getDialog().getText("label_portDUT"));
        label_portREF.setText(UIText.getDialog().getText("label_portREF"));
        if(textField_PortDUT.getText().isBlank()){
            textField_PortDUT.setText(getProperty("PortDUT"));
        }
        if(textField_PortREF.getText().isBlank()){
            textField_PortREF.setText(getProperty("PortREF"));
        }
    }

    private void buttonListener(){

    }

    @Override
    public void onEvent(String event, String[] data) {

    }
}
