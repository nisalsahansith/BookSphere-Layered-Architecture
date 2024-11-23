package com.project.booksphere.controller;


import com.project.booksphere.model.LoginModel;
import com.project.booksphere.util.EncryptPassword;
import com.project.booksphere.util.NewPopUpWindow;
import com.project.booksphere.util.SharedInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private AnchorPane bodypane;

    @FXML
    private Label pageName;

    @FXML
    private Label lblErorr;


    @FXML
    private Label lblsignUp;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtTextPassword;

    @FXML
    private Button btnSignIn;

    @FXML
    private ImageView imgHidePassword;

    @FXML
    private ImageView imgShowPassword;


    private final LoginModel model = new LoginModel();
    private final SharedInfo sharedInfo = SharedInfo.getInstance();

    void login(){
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        try {
            String employId = model.login(userName);
            sharedInfo.setEmployeeId(employId);
            String storePassword = model.getPassword(employId);
            System.out.println(storePassword);
            boolean isVerify = EncryptPassword.verifyPassword(password,storePassword);
            if(isVerify){
                String role = model.checkRole(employId);
                if(role != null){
                    if (role.equals("Owner")) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OwnerHomePage.fxml"));
                        Parent homePageRoot = loader.load();
                        Scene homeScene = new Scene(homePageRoot);

                        Stage primaryStage = (Stage) pageName.getScene().getWindow();
                        primaryStage.setScene(homeScene);
                        primaryStage.setTitle("Home Page");
                    } else if (role.equals("Manager")){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ManagerHomePage.fxml"));

                        Parent homePageRoot = loader.load();
                        Scene homeScene = new Scene(homePageRoot);

                        Stage primaryStage = (Stage) pageName.getScene().getWindow();
                        primaryStage.setScene(homeScene);
                        primaryStage.setTitle("Home Page");

                    } else if (role.equals("Stock Manager")){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StockManagerHomePage.fxml"));

                        Parent homePageRoot = loader.load();
                        Scene homeScene = new Scene(homePageRoot);

                        Stage primaryStage = (Stage) pageName.getScene().getWindow();
                        primaryStage.setScene(homeScene);
                        primaryStage.setTitle("Home Page");
                    } else if (role.equals("Cashier")){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CashierHomePage.fxml"));

                        Parent homePageRoot = loader.load();
                        Scene homeScene = new Scene(homePageRoot);

                        Stage primaryStage = (Stage) pageName.getScene().getWindow();
                        primaryStage.setScene(homeScene);
                        primaryStage.setTitle("Home Page");
                    }


                } else {
                    System.out.println("role null");
                }
            }else {
                String s = "Invalid username or password. please try again!";
                lblErorr.setText(s);
            }
        }catch(IOException | SQLException  e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail to load page!" + e.getMessage()).show();
        }
    }

    @FXML
    void onAction(ActionEvent event) {
        login();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pageName.setText("Sign in to BookSphere");
        txtUserName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtPassword.requestFocus(); // Move focus to the next text field
            }
        });

        txtPassword.setOnKeyPressed(event -> {
           if (event.getCode() == KeyCode.ENTER) {
               btnSignIn.fire();
           }
        });
    }

    @FXML
    void navigateToSignUpPage(MouseEvent event) throws IOException, SQLException {
        boolean isHasOwner = model.checkOwner();
        System.out.println(isHasOwner);
        if (isHasOwner) {
            NewPopUpWindow.newWindowPopUp("/view/SecuirityProtection.fxml");
            boolean isOwnerSubmit = sharedInfo.isOwnerSubmit();
            if (isOwnerSubmit) {
                SignUpNavigation();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Can't reach Signup page", ButtonType.OK).show();
            }
        }else {
            SignUpNavigation();
        }
    }

    @FXML
    void popUpForgotPassword(MouseEvent event) throws IOException {
        NewPopUpWindow.newWindowPopUp("/view/ForgotPassword.fxml");
    }

    public void SignUpNavigation() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignUp.fxml"));

        Parent welcomePageRoot = loader.load();
        Scene welcomeScene = new Scene(welcomePageRoot);

        Stage primaryStage = (Stage) pageName.getScene().getWindow();
        primaryStage.setScene(welcomeScene);
    }

    @FXML
    void hidePassword(MouseEvent event) {
        String password = txtTextPassword.getText();
        imgHidePassword.setVisible(false);
        txtTextPassword.setVisible(false);
        imgShowPassword.setVisible(true);
        txtPassword.setVisible(true);
        txtPassword.setText(password);
    }

    @FXML
    void showPassword(MouseEvent event) {
        String password = txtPassword.getText();
        imgShowPassword.setVisible(false);
        txtPassword.setVisible(false);
        imgHidePassword.setVisible(true);
        txtTextPassword.setVisible(true);
        txtTextPassword.setText(password);
    }


}

//password yika
// nisal pw = nisal -> owner
//nisals pw = nisal123 -> Manager
