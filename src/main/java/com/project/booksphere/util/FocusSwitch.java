package com.project.booksphere.util;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class FocusSwitch {
    public void focusSwitchButtonPress(Button button, TextField textField){
        button.setOnKeyPressed(keyEvent -> {
            textField.requestFocus();
        });
    }

    public void switchFocusTextFieldPress(Button button,TextField textField){
        textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER){
                button.fire();
            }
        });
    }
    public void switchNextTextField(TextField txt1,TextField txt2){
        txt1.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.DOWN){
                txt2.requestFocus();
            }
        });
    }
    public void focusSwitchButtonPressTextField(Button button, TextField txt1,TextField txt2){
        txt1.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER){
                button.fire();
            }
            if (keyEvent.getCode() == KeyCode.DOWN){
                txt2.requestFocus();
            }
            if (keyEvent.getCode() == KeyCode.UP){
                txt2.requestFocus();
            }
        });
    }
    public void upTextField(TextField txt1,TextField txt2){
        txt1.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.UP){
                txt2.requestFocus();
            }
        });
    }
}
