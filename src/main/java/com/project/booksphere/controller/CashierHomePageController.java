package com.project.booksphere.controller;

import com.project.booksphere.model.EmployeeModel;
import com.project.booksphere.model.UserModel;
import com.project.booksphere.util.SharedInfo;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class CashierHomePageController implements Initializable {

    @FXML
    private AnchorPane ownerBodyPane;

    @FXML
    private Button btnDashboard;

    @FXML
    private Label lblUser;

    @FXML
    private Label lblUserID;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private Button manageCustomer;

    @FXML
    private Button managePurchase;

    private final EmployeeModel employeeModel = new EmployeeModel();
    private final UserModel userModel = new UserModel();
    private final SharedInfo sharedInfo = SharedInfo.getInstance();

    @FXML
    void SettingPage(MouseEvent event) throws IOException {
        newWindowPopUp("/view/SettingPage.fxml");
    }

    @FXML
    void navigateDashboard(ActionEvent event) {
        navigateTo("/view/DashboardCashier.fxml");
    }

    @FXML
    void navigateToCustomerPage(ActionEvent event) {
        navigateTo("/view/ManagePromotion.fxml");
    }

    @FXML
    void navigateToLogin(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));

        Parent homePageRoot = loader.load();
        Scene homeScene = new Scene(homePageRoot);

        Stage primaryStage = (Stage) lblUser.getScene().getWindow();
        primaryStage.setScene(homeScene);
        primaryStage.setTitle("LogIn Page");
    }

    @FXML
    void navigateToPurchasePage(ActionEvent event) {
        navigateTo("/view/ManagePurchase.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String empID = sharedInfo.getEmployeeId();
        try {
            setDashboard();
            setName(empID);
            displayTime();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setName(String id) throws SQLException {
        String getName = employeeModel.getName(id);
        lblUser.setText(getName);
        System.out.println(getName);
        String userId = userModel.getUserID(id);
        sharedInfo.setUserID(userId);
        System.out.println(userId);
        if (getName != null && userId != null){
            lblUser.setText(getName);
            lblUserID.setText(userId);
        }
    }

    private void setDashboard() throws IOException {
        ownerBodyPane.getChildren().clear();
        AnchorPane load =  FXMLLoader.load(getClass().getResource("/view/DashboardCashier.fxml"));
        load.prefHeightProperty().bind(ownerBodyPane.widthProperty());
        load.prefHeightProperty().bind(ownerBodyPane.heightProperty());
        ownerBodyPane.getChildren().add(load);
    }

    public void newWindowPopUp(String path) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource(path));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/logo.jpg")));
        stage.setTitle("Book Sphere");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        stage.showAndWait();
    }

    public void navigateTo(String path) {
        try {
            ownerBodyPane.getChildren().clear();
            AnchorPane load =  FXMLLoader.load(getClass().getResource(path));
            load.prefHeightProperty().bind(ownerBodyPane.widthProperty());
            load.prefHeightProperty().bind(ownerBodyPane.heightProperty());
            ownerBodyPane.getChildren().add(load);
        }catch (IOException e){
            new Alert(Alert.AlertType.ERROR,"Fail to load page "+path).showAndWait();
            e.printStackTrace();
        }
    }

    public void displayTime() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat time =  new SimpleDateFormat("HH:mm:ss");

                lblTime.setText(time.format(new Date()));
                lblDate.setText(date.format(new Date()));
            }
        };

        timer.start();
        //comment
    }
}
