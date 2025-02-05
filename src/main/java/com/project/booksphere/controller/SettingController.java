package com.project.booksphere.controller;

import com.project.booksphere.bo.BOFactory;
import com.project.booksphere.bo.custom.SettingBo;
import com.project.booksphere.bo.custom.impl.SettingBOImpl;
import com.project.booksphere.dao.custom.UserDAO;
import com.project.booksphere.dao.custom.impl.UserDAOImpl;
import com.project.booksphere.util.EncryptPassword;
import com.project.booksphere.util.SharedInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.Setter;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SettingController implements Initializable {

//    private UserModel userModel = new UserModel();
//    private OwnerHomePageController ownerHomePageController = new OwnerHomePageController(this);
@FXML
private ImageView imgHidePassword;

    @FXML
    private ImageView imgHidePasswordConfirm;

    @FXML
    private ImageView imgHidePasswordNew;

    @FXML
    private ImageView imgShowPassword;

    @FXML
    private ImageView imgShowPasswordConfirm;

    @FXML
    private ImageView imgShowPasswordNew;

    @FXML
    private TextField txtTextPassword;

    @FXML
    private TextField txtTextPasswordConfirm;

    @FXML
    private TextField txtTextPasswordNew;

    @FXML
    private Label lblUserName;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private PasswordField txtCurrentPassword;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private Label txtUsername;

    @Setter
    private OwnerHomePageController ownerHomePageController;

    private SharedInfo sharedInfo = SharedInfo.getInstance();

//    private UserDAO userDAO = new UserDAOImpl();
    private final SettingBo settingBo = (SettingBo) BOFactory.getInstance().getBO(BOFactory.BOType.SETTING);

    @FXML
    void changePassword(MouseEvent event) throws SQLException {
        String currentPassword = txtCurrentPassword.getText();
        String newPassword = txtNewPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String userId = sharedInfo.getUserID();
        String password = settingBo.getUserPassword(userId);
        if (EncryptPassword.verifyPassword(currentPassword,password)){
            if (newPassword.equals(confirmPassword)){
                String hashPassword = EncryptPassword.hashPassword(newPassword);
                boolean isChangePassword = settingBo.updateUserPassword(hashPassword,userId);
                if (isChangePassword){
                    new Alert(Alert.AlertType.INFORMATION,"Password is updated",ButtonType.OK).show();
                    txtCurrentPassword.setText("");
                    txtNewPassword.setText("");
                    txtConfirmPassword.setText("");
                }else{
                    new Alert(Alert.AlertType.INFORMATION,"Password is not updated",ButtonType.OK).show();
                }
            }else {
                new Alert(Alert.AlertType.INFORMATION,"Confirm Password does not match",ButtonType.OK).show();
            }
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Current Password does not match",ButtonType.OK).show();
        }
    }

    @FXML
    void changeUserName(MouseEvent event) throws SQLException {
        String userName = txtUserName.getText();
        String userId = sharedInfo.getUserID();
        boolean isUpdatedName = settingBo.setUserName(userId,userName);
        if (isUpdatedName){
            new Alert(Alert.AlertType.INFORMATION, "User Name is Updated", ButtonType.OK).show();
            txtUserName.setText("");
            setTexUserName();

        }else {
            new Alert(Alert.AlertType.INFORMATION,"User Name is not Updated");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setTexUserName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTexUserName() throws SQLException {
        String id = sharedInfo.getUserID();
        String name = settingBo.getUserName(id);
        lblUserName.setText(name);
    }

    public void updateName(String id) throws SQLException {
        if (ownerHomePageController != null){
            ownerHomePageController.setName(id);
        }
    }

    @FXML
    void hidePassword(MouseEvent event) {
        String Password = txtTextPassword.getText();
        imgHidePassword.setVisible(false);
        txtTextPassword.setVisible(false);
        imgShowPassword.setVisible(true);
        txtCurrentPassword.setVisible(true);
        txtCurrentPassword.setText(Password);
    }

    @FXML
    void hidePasswordConfirm(MouseEvent event) {
        String Password = txtTextPasswordConfirm.getText();
        imgHidePasswordConfirm.setVisible(false);
        txtTextPasswordConfirm.setVisible(false);
        imgShowPasswordConfirm.setVisible(true);
        txtConfirmPassword.setVisible(true);
        txtConfirmPassword.setText(Password);
    }

    @FXML
    void hidePasswordNew(MouseEvent event) {
        String Password = txtTextPasswordNew.getText();
        imgHidePasswordNew.setVisible(false);
        txtTextPasswordNew.setVisible(false);
        imgShowPasswordNew.setVisible(true);
        txtNewPassword.setVisible(true);
        txtNewPassword.setText(Password);
    }

    @FXML
    void showPassword(MouseEvent event) {
        String Password = txtCurrentPassword.getText();
        imgShowPassword.setVisible(false);
        txtCurrentPassword.setVisible(false);
        imgHidePassword.setVisible(true);
        txtTextPassword.setVisible(true);
        txtTextPassword.setText(Password);
    }

    @FXML
    void showPasswordConfirm(MouseEvent event) {
        String Password = txtConfirmPassword.getText();
        imgShowPasswordConfirm.setVisible(false);
        txtConfirmPassword.setVisible(false);
        imgHidePasswordConfirm.setVisible(true);
        txtTextPasswordConfirm.setVisible(true);
        txtTextPasswordConfirm.setText(Password);
    }

    @FXML
    void showPasswordNew(MouseEvent event) {
        String Password = txtNewPassword.getText();
        imgShowPasswordNew.setVisible(false);
        txtNewPassword.setVisible(false);
        imgHidePasswordNew.setVisible(true);
        txtTextPasswordNew.setVisible(true);
        txtTextPasswordNew.setText(Password);
    }


}
