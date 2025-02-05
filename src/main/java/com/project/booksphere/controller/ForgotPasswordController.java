package com.project.booksphere.controller;

import com.project.booksphere.bo.BOFactory;
import com.project.booksphere.bo.custom.ForgotPasswordBo;
import com.project.booksphere.bo.custom.impl.ForgotPasswordBOImpl;
import com.project.booksphere.dao.custom.EmployeeDAO;
import com.project.booksphere.dao.custom.UserDAO;
import com.project.booksphere.dao.custom.impl.EmployeeDAOImpl;
import com.project.booksphere.dao.custom.impl.UserDAOImpl;
import com.project.booksphere.util.EncryptPassword;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

public class ForgotPasswordController implements Initializable {

    Random random = new Random();

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSendOPT;

    @FXML
    private Button btnSubmit;

    @FXML
    private Label lblConfirmPassword;

    @FXML
    private Label lblNewPassword;

    @FXML
    private Label lblOTP;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    public TextField txtEmail;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private TextField txtOTP;

//    private EmployeeModel employeeModel = new EmployeeModel();
//    private UserModel userModel = new UserModel();

//    private final EmployeeDAO employeeDAO = new EmployeeDAOImpl();
//    private final UserDAO userDAO = new UserDAOImpl();
    private final ForgotPasswordBo forgotPasswordBo = (ForgotPasswordBo) BOFactory.getInstance().getBO(BOFactory.BOType.FORGOT_PASSWORD);
    int OTP = random.nextInt(100000,999999);

    @FXML
    void resetPassword(ActionEvent event) throws SQLException {
        String email = txtEmail.getText();
        String newPassword = txtNewPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String empId = forgotPasswordBo.getEmpId(email);
        if (newPassword.equals(confirmPassword)) {
            String hashPassword = EncryptPassword.hashPassword(newPassword);
            boolean reset = forgotPasswordBo.resetPassword(hashPassword,empId);
            if (reset){
                new Alert(Alert.AlertType.INFORMATION,"Password reset successfully.",ButtonType.OK).show();
                Stage stage = (Stage) btnReset.getScene().getWindow();
                stage.close();
            }else {
                new Alert(Alert.AlertType.INFORMATION,"Password reset unsuccessfully.",ButtonType.OK).show();
            }
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Password does not match",ButtonType.OK).show();
        }
    }

    @FXML
    void sendGmail(ActionEvent event) throws SQLException {
        String email = txtEmail.getText();
        boolean isHasEmail = forgotPasswordBo.checkEmail(email);
        if (isHasEmail){
            txtEmail.setStyle(txtEmail.getStyle()+"-fx-border-color:  #00a8ff; ");
            SendMailController sendMailController = new SendMailController();
            String from = "bookspherecom@gmail.com";
            String subject = "Your Account Password Reset OTP ";
            String body ="Your Password reset OTP is " + OTP;
            sendMailController.sendEmailWithGmail(from,email,subject,body);
            setVisible();
        }else{
            txtEmail.setStyle(txtEmail.getStyle()+"-fx-border-color:  red; ");
        }
    }

    @FXML
    void submitOtp(ActionEvent event) {
        int otp = Integer.parseInt(txtOTP.getText());
        if (otp == OTP){
            setVisibleTrue();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblOTP.setVisible(false);
        lblNewPassword.setVisible(false);
        lblConfirmPassword.setVisible(false);
        txtOTP.setVisible(false);
        txtNewPassword.setVisible(false);
        txtConfirmPassword.setVisible(false);
        btnSubmit.setVisible(false);
        btnReset.setVisible(false);
    }

    public void setVisible(){
        txtEmail.setDisable(true);
        btnSendOPT.setDisable(true);
        lblOTP.setVisible(true);
        txtOTP.setVisible(true);
        btnSubmit.setVisible(true);

    }

    public void setVisibleTrue(){
        txtOTP.setDisable(true);
        btnSubmit.setDisable(true);
        lblNewPassword.setVisible(true);
        lblConfirmPassword.setVisible(true);
        txtNewPassword.setVisible(true);
        txtConfirmPassword.setVisible(true);
        btnReset.setVisible(true);
    }
}
