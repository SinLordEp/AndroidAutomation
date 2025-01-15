package com.sinlord.aatdesktop.GUI;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.sinlord.aatdesktop.exception.OperationCancelledException;
import org.yaml.snakeyaml.Yaml;

import static com.sinlord.aatdesktop.principal.getProperty;

/**
 * @author SINLORDEP
 */
public class UIText {
    private static UIText instance;
    protected Map<String,Object> texts;
    protected Map<String,Object> inputs;
    protected Map<String,Object> popups;
    protected Map<String,Object> options;
    protected String language;

    public UIText() {
        URL resource = getClass().getResource(getProperty("generalDialog"));
        initialize(resource);
    }

    @SuppressWarnings("unchecked")
    protected void initialize(URL resource) {
        if(resource == null) {
            throw new IllegalArgumentException("GeneralDialog yaml config is null");
        }
        try(InputStream inputStream = resource.openStream()) {
            Yaml yaml = new Yaml();
            Map<String,Object> dialogs = yaml.load(inputStream);
            texts = (Map<String, Object>) dialogs.get("text");
            inputs = (Map<String, Object>) dialogs.get("input");
            popups = (Map<String, Object>) dialogs.get("popup");
            options = (Map<String, Object>) dialogs.get("options");
        }catch (IOException e){
            message("Initializing dialogs failed\n"+e.getMessage());
        }
        this.language = getProperty("defaultLanguage");
    }

    public static UIText getDialog() {
        if (instance == null) {
            instance = new UIText();
        }
        return instance;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @SuppressWarnings("unchecked")
    public String getText(String subType) {
        if(!texts.containsKey(subType)) {
            return "Sub_type: %s not found".formatted(subType);
        }
        return (String) ((Map<String, Object>)(texts.get(subType))).get(language);
    }

    @SuppressWarnings("unchecked")
    protected String getPopup(String subType) {
        if(!popups.containsKey(subType)) {
            return "Sub_type: %s not found".formatted(subType);
        }
        return (String) ((Map<String, Object>)(popups.get(subType))).get(language);
    }

    public void popup(String subType) {
        JOptionPane.showMessageDialog(null, getPopup(subType));
    }

    @SuppressWarnings("unchecked")
    public String input(String subType) {
        if(!inputs.containsKey(subType)) {
            return JOptionPane.showInputDialog("Sub_type: %s not found".formatted(subType));
        }
        return JOptionPane.showInputDialog(((Map<String, Object>) inputs.get(subType)).get(language));
    }

    @SuppressWarnings("unchecked")
    protected Map<String,Object> option(String subType){
        if(!options.containsKey(subType)) {
            Map<String,Object> nullOptions = new HashMap<>();
            nullOptions.put("Sub type: %s not found".formatted(subType), null);
            return nullOptions;
        }
        return (Map<String, Object>) options.get(subType);
    }

    public void message(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    @SuppressWarnings("unchecked")
    protected String getTitle(Map<String, Object> dialog) {
        Map<String, Object> titleDialog = (Map<String, Object>) dialog.get("title");
        return (String) titleDialog.get(language);
    }

    @SuppressWarnings("unchecked")
    protected String getMessage(Map<String, Object> dialog) {
        Map<String, Object> messageDialog = (Map<String, Object>) dialog.get("message");
        return (String) messageDialog.get(language);
    }

    @SuppressWarnings("unchecked")
    protected String[] getOptions(Map<String, Object> dialog) {
        Map<String, Object> optionsDialog = (Map<String, Object>) dialog.get("option");
        return ((java.util.List<String>)optionsDialog.get(language)).toArray(new String[0]);
    }

    @SuppressWarnings("unchecked")
    public String[] getOptions(String subType) {
        if(!options.containsKey(subType)) {
            String[] nullOptions = new String[1];
            nullOptions[0] = "Sub_type: %s not found".formatted(subType);
            return nullOptions;
        }
        return ((java.util.List<String>)(((Map<String, Object>) option(subType).get("option")).get(language))).toArray(new String[0]);
    }

    public int selectionDialog(String subType) {
        Map<String, Object> dialog = option(subType);
        String[] options = getOptions(dialog);
        int choice = JOptionPane.showOptionDialog(
                null,
                getMessage(dialog),
                getTitle(dialog),
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );
        if (choice == -1) {
            throw new OperationCancelledException();
        }
        return choice;
    }

    public Object selectionDialog(String subType, Object[] options) {
        Map<String, Object> dialog = option(subType);
        int choice = JOptionPane.showOptionDialog(
                null,
                getMessage(dialog),
                getTitle(dialog),
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );
        if (choice == -1) {
            throw new OperationCancelledException();
        }
        return options[choice];
    }
}
