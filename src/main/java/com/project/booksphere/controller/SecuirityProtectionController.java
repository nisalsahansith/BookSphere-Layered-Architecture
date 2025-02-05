package com.project.booksphere.controller;

import com.project.booksphere.bo.BOFactory;
import com.project.booksphere.bo.custom.SecuirityProtectionBo;
import com.project.booksphere.bo.custom.impl.SecuirityProtectionBOImpl;
import com.project.booksphere.dao.custom.EmployeeDAO;
import com.project.booksphere.dao.custom.impl.EmployeeDAOImpl;
import com.project.booksphere.util.SharedInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

public class SecuirityProtectionController implements Initializable {

    @FXML
    private Button btnSubmit;

    @FXML
    private TextField txtOtp;

    Random random = new Random();
//    private final EmployeeModel employeeModel = new EmployeeModel();
    private final LoginController loginController = new LoginController();
    private final SharedInfo sharedInfo = SharedInfo.getInstance();
    private int otp = 0;

//    private final EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    private final SecuirityProtectionBo secuirityProtectionBo = (SecuirityProtectionBo) BOFactory.getInstance().getBO(BOFactory.BOType.SECUIRITY_PROTECTION);

    @FXML
    void resendOtp(MouseEvent event) throws SQLException {
        otp = random.nextInt(100000,999999);
        sendOtp(otp);
    }

    @FXML
    void submitOtp(ActionEvent event) throws IOException {
        int OTP = Integer.parseInt(txtOtp.getText());
        if (OTP == otp){
            sharedInfo.setOwnerSubmit(true);
            Stage stage = (Stage) btnSubmit.getScene().getWindow();
            stage.close();
        }else {
            new Alert(Alert.AlertType.INFORMATION,"OTP is wrong", ButtonType.OK).show();
            txtOtp.setText("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        otp = getOtp();
        try {
            sendOtp(otp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendOtp(int otp) throws SQLException {
        SendMailController sendMailController = new SendMailController();
        String email = secuirityProtectionBo.getMail();
        System.out.println(email);
        String from = "bookspherecom@gmail.com";
        String subject = "Your OTP Code";
        String body = "Your sign up account verification OTP code is here "+ otp;
        sendMailController.sendEmailWithGmail(from,email,subject,body);
    }

    public int getOtp(){
        otp = random.nextInt(100000,999999);
        return otp;
    }
}
