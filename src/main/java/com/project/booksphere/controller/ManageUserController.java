package com.project.booksphere.controller;

import com.project.booksphere.dto.UserDto;
import com.project.booksphere.model.UserModel;
import com.project.booksphere.util.NewPopUpWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageUserController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSend;

    @FXML
    private TableColumn<UserDto, String> columnEmployeeID;

    @FXML
    private TableColumn<UserDto, String> columnPassword;

    @FXML
    private TableColumn<UserDto, String> columnUserID;

    @FXML
    private TableColumn<UserDto, String> columnUserName;

    @FXML
    private TableView<UserDto> tblUser;

    @FXML
    private TextField txtSearch;

    UserModel userModel = new UserModel();
    NewPopUpWindow newPopUpWindow = new NewPopUpWindow();


    @FXML
    void deleteUsers(ActionEvent event) throws SQLException {
        String id = tblUser.getSelectionModel().getSelectedItem().getUserId();
        System.out.println(id);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure want to delete this User",ButtonType.YES);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean isDelete = userModel.deleteUser(id);
            if (isDelete) {
                refresh();
                new Alert(Alert.AlertType.INFORMATION, "User deleted successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "User could not be deleted").show();
            }
        }
    }

    @FXML
    void resetPage(ActionEvent event) throws SQLException {
        txtSearch.setText("");
        refresh();
        btnSend.setDisable(true);
    }

    @FXML
    void searchOrder(MouseEvent event) throws SQLException {
        String id = txtSearch.getText();
        ArrayList<UserDto> userDtos = userModel.getAllId(id);
        ObservableList<UserDto> userDTOS = FXCollections.observableArrayList();
        for (UserDto userDto: userDtos){
            UserDto userDTos = new UserDto(
                    userDto.getUserId(),
                    userDto.getUserName(),
                    userDto.getPassword(),
                    userDto.getEmployeeId()
            );
            userDTOS.add(userDTos);
        }
        tblUser.setItems(userDTOS);
    }

    @FXML
    void sendGmail(ActionEvent event) throws SQLException, IOException {
        String employeeId = tblUser.getSelectionModel().getSelectedItem().getEmployeeId();
        String email = userModel.getMail(employeeId);
        if (email != null) {
            newPopUpWindow.newWindowPopUpEmail("/view/SendMail.fxml", email);
        }else {
            newPopUpWindow.newWindowPopUpEmail("/view/SendMail.fxml","no email found");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnUserID.setCellValueFactory(new PropertyValueFactory<>("UserId"));
        columnUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        columnPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        columnEmployeeID.setCellValueFactory(new PropertyValueFactory<>("EmployeeId"));
        columnPassword.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("*".repeat(item.length())); //meka stack overflow eken gatte process eka hriytm danne
                }
            }
        });
        try {
            btnSend.setDisable(true);
            refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void refresh() throws SQLException {
        ArrayList<UserDto> userDtos = userModel.getAll();
        ObservableList<UserDto> userDTOS = FXCollections.observableArrayList();
        for (UserDto userDto: userDtos){
            UserDto userDTos = new UserDto(
                    userDto.getUserId(),
                    userDto.getUserName(),
                    userDto.getPassword(),
                    userDto.getEmployeeId()
            );
            userDTOS.add(userDTos);
        }
        tblUser.setItems(userDTOS);
    }

    @FXML
    void onClickTable(MouseEvent event) {
        btnSend.setDisable(false);
    }

}
