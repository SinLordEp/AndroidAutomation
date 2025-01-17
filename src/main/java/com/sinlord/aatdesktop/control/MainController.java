package com.sinlord.aatdesktop.control;

import com.sinlord.aatdesktop.GUI.LogStage;
import com.sinlord.aatdesktop.GUI.MainUI;
import com.sinlord.aatdesktop.http.SocketClientPC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SINLORDEP
 */
public class MainController {
    private MainUI ui;
    private SocketClientPC DUT;
    private SocketClientPC REF;
    private int portDUT;
    private int portREF;
    public void run(){
        this.ui = new MainUI(this);
        ui.run();
    }

    public void onWindowClosing() {
        System.exit(0);
    }

    public void connectDUT(int port){
        try {
            portDUT = port;
            setupPortForwarding(getConnectedDevices());
            DUT = new SocketClientPC("localhost", portDUT);
            if(DUT.connect()){
                ui.appendLog(LogStage.PASS, "connectDUT_pass");
                readDeviceInfo(Device.DUT);
            }else{
                ui.appendLog(LogStage.ERROR, "connectDUT_failed");
            }
        } catch (Exception e) {
            ui.appendLog(LogStage.ERROR, "connectDUT_failed", e.getMessage());
        }
    }

    public void connectREF(int port){
        try {
            portREF = port;
            setupPortForwarding(getConnectedDevices());
            REF = new SocketClientPC("localhost", portREF);
            if(REF.connect()){
                ui.appendLog(LogStage.PASS, "connectREF_pass");
                REF.sendCommand("REF_CHECK");
            }else{
                ui.appendLog(LogStage.ERROR, "connectREF_failed");
            }
        } catch (Exception e) {
            ui.appendLog(LogStage.ERROR, "connectREF_failed", e.getMessage());
        }
    }

    public void readDeviceInfo(Device device){
        switch (device){
            case DUT:
                try {
                    ui.appendLog(LogStage.ONGOING, "DUT_INFO_ONGOING");
                    String response = DUT.sendCommand("DUT_INFO");
                    if(!response.isEmpty()){
                        ui.updateDeviceInfo(device, response);
                        ui.appendLog(LogStage.PASS, "GENERAL_PASS");
                    }
                } catch (IOException e) {
                    ui.appendLog(LogStage.ERROR, "GENERAL_FAIL", e.getMessage());
                }
                break;
            case REF:
                try {
                    ui.appendLog(LogStage.ONGOING, "REF_INFO_ONGOING");
                    String response = REF.sendCommand("REF_INFO");
                    if(!response.isEmpty()){
                        ui.updateDeviceInfo(device, response);
                        ui.appendLog(LogStage.PASS, "GENERAL_PASS");
                    }
                } catch (IOException e) {
                    ui.appendLog(LogStage.ERROR, "GENERAL_FAIL", e.getMessage());
                }
                break;
        }
    }

    public List<String> getConnectedDevices() {
        List<String> devices = new ArrayList<>();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("adb", "devices");
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.endsWith("device")) {
                    String deviceSerial = line.split("\\s+")[0];
                    devices.add(deviceSerial);
                }
            }
        } catch (IOException e) {
            ui.appendLog(LogStage.ERROR, "failed_getting_devices_list");
        }
        return devices;
    }

    public void setupPortForwarding(List<String> devices) {
        List<String> portForwardingList = new ArrayList<>();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("adb", "forward", "--list");
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                portForwardingList.add(line);
            }
        } catch (IOException e) {
            ui.appendLog(LogStage.ERROR, "failed_getting_forwarded_list");
        }

        for (String device : devices) {
            boolean forwarded = false;
            for (String line : portForwardingList) {
                if (line.contains(device)) {
                    forwarded = true;
                    break;
                }
            }
            if (forwarded) {
                continue;
            }
            try {
                ProcessBuilder forwardPortDUT = new ProcessBuilder("adb", "-s", device, "forward", "tcp:" + portDUT, "tcp:8888");
                ProcessBuilder forwardPortREF = new ProcessBuilder("adb", "-s", device, "forward", "tcp:" + portREF, "tcp:8877");
                forwardPortDUT.start().waitFor();
                forwardPortREF.start().waitFor();
                ui.appendLog(LogStage.INFO, "success_forward_port", device);
            } catch (IOException | InterruptedException e) {
                ui.appendLog(LogStage.ERROR, "failed_forward_port", device);
            }
        }
    }

}
