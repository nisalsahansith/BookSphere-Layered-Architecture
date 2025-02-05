package com.project.booksphere.controller;

import com.project.booksphere.bo.BOFactory;
import com.project.booksphere.bo.custom.SignUpBo;
import com.project.booksphere.bo.custom.impl.SignUpBOImpl;
import com.project.booksphere.dao.custom.EmployeeDAO;
import com.project.booksphere.dao.custom.UserDAO;
import com.project.booksphere.dao.custom.impl.EmployeeDAOImpl;
import com.project.booksphere.dao.custom.impl.UserDAOImpl;
import com.project.booksphere.db.DBConnection;
import com.project.booksphere.dto.EmployeeDto;
import com.project.booksphere.dto.UserDto;
import com.project.booksphere.util.EncryptPassword;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button btnSignUp;

    @FXML
    private ComboBox comBox;

    @FXML
    private ImageView imgHidePassword;

    @FXML
    private ImageView imgHidePasswordConfirm;

    @FXML
    private ImageView imgShowPassword;

    @FXML
    private ImageView imgShowPasswordConfirm;


    @FXML
    private TextField conNum;

    @FXML
    private TextField txtTextPassword;

    @FXML
    private TextField txtTextPasswordConfirm;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Label label;

    @FXML
    private PasswordField password;

    @FXML
    private Label signIn;

    @FXML
    private TextField userName;

    @FXML
    private TextField name;

    @FXML
    private Label welcomePageName;

    @FXML
    private Label lblError;

    @FXML
    private TextField txtEmail;

//    private SignUpModel signUpModel = new SignUpModel();
//    private final EmployeeModel employeeModel = new EmployeeModel();

//    private final EmployeeDAO employeeDAO = new EmployeeDAOImpl();
//    private final UserDAO userDAO = new UserDAOImpl();
    private final SignUpBo signUpBo = (SignUpBo) BOFactory.getInstance().getBO(BOFactory.BOType.SIGNUP);

    @FXML
    void select(ActionEvent event) {
        String s = comBox.getSelectionModel().getSelectedItem().toString();
        label.setText(s);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            boolean isOwn = signUpBo.checkOwner();
            if (isOwn){
                ObservableList<String> list = FXCollections.observableArrayList("Manager","Stock Manager","Cashier");
                comBox.setItems(list);
            }else {
                ObservableList<String> list = FXCollections.observableArrayList("Owner","Manager","Stock Manager","Cashier");
                comBox.setItems(list);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void navigateToSignIn(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));

        Parent loginPageRoot = loader.load();
        Scene logScene = new Scene(loginPageRoot);

        Stage primaryStage = (Stage) welcomePageName.getScene().getWindow();
        primaryStage.setScene(logScene);
    }

    @FXML
    void signUp(ActionEvent event) throws SQLException {
        String employId = signUpBo.getNextEmployId();
        String userId = signUpBo.nextUserId();
        String Name = name.getText();
        String username = userName.getText();
        boolean isHaveUserNAme = signUpBo.checkUser(username);
        String Password = password.getText();
        String conPassword = confirmPassword.getText();
        String phone = conNum.getText();
        boolean isHavePhone = signUpBo.checkPhone(phone);
        String email = txtEmail.getText();
        boolean isHaveEmail = signUpBo.checkEmail(email);
        LocalDate date = Date.valueOf(LocalDate.now()).toLocalDate();
        String role = (String) comBox.getSelectionModel().getSelectedItem();
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            if (!isHaveUserNAme && !isHaveEmail && !isHavePhone) {
                if (Password.equals(conPassword)) {
                    String hashPassword = EncryptPassword.hashPassword(Password);
                    System.out.println(hashPassword);
                    connection.setAutoCommit(false);
                    boolean isEmployeeSave = signUpBo.saveEmployee(new EmployeeDto(employId,Name,role,phone,email,date));
                    if (!isEmployeeSave) {
                        connection.rollback();
                        connection.setAutoCommit(true);
                    }
                    boolean isUserSave = signUpBo.saveUser(new UserDto(userId,username,hashPassword,employId));
                    if (isUserSave) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));

                            Parent loginPageRoot = loader.load();
                            Scene logScene = new Scene(loginPageRoot);

                            Stage primaryStage = (Stage) welcomePageName.getScene().getWindow();
                            primaryStage.setScene(logScene);
                        } catch (IOException e) {
                            System.out.println("Error" + e.getMessage());
                        }
                        connection.commit();
                        connection.setAutoCommit(true);

                    }
                } else {
                    lblError.setText("Password confirmation is wrong");
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
            }else {
                lblError.setText("Duplicate values");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @FXML
    void showPassword(MouseEvent event) {
        String Password = password.getText();
        imgShowPassword.setVisible(false);
        password.setVisible(false);
        imgHidePassword.setVisible(true);
        txtTextPassword.setVisible(true);
        txtTextPassword.setText(Password);
    }
    @FXML
    void hidePassword(MouseEvent event) {
        String Password = txtTextPassword.getText();
        imgHidePassword.setVisible(false);
        txtTextPassword.setVisible(false);
        imgShowPassword.setVisible(true);
        password.setVisible(true);
        password.setText(Password);
    }
    @FXML
    void showPasswordConfirm(MouseEvent event) {
        String Password = confirmPassword.getText();
        imgShowPasswordConfirm.setVisible(false);
        confirmPassword.setVisible(false);
        imgHidePasswordConfirm.setVisible(true);
        txtTextPasswordConfirm.setVisible(true);
        txtTextPasswordConfirm.setText(Password);
    }
    @FXML
    void hidePasswordConfirm(MouseEvent event) {
        String Password = txtTextPasswordConfirm.getText();
        imgHidePasswordConfirm.setVisible(false);
        txtTextPasswordConfirm.setVisible(false);
        imgShowPasswordConfirm.setVisible(true);
        confirmPassword.setVisible(true);
        confirmPassword.setText(Password);
    }

}
