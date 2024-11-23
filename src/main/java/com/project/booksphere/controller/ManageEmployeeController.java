package com.project.booksphere.controller;

import com.project.booksphere.dto.CustomerDto;
import com.project.booksphere.dto.EmployeeDto;
import com.project.booksphere.dto.tm.CustomerTM;
import com.project.booksphere.dto.tm.EmployeeTM;
import com.project.booksphere.model.EmployeeModel;
import com.project.booksphere.util.NewPopUpWindow;
import com.project.booksphere.util.Validate;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageEmployeeController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReport;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSend;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<EmployeeTM, LocalDate> columnDate;

    @FXML
    private TableColumn<EmployeeTM, String> columnEmail;

    @FXML
    private TableColumn<EmployeeTM, String> columnId;

    @FXML
    private TableColumn<EmployeeTM, String> columnName;

    @FXML
    private TableColumn<EmployeeTM, String> columnPhone;

    @FXML
    private TableColumn<EmployeeTM, String> columnRole;

//    @FXML
//    private ComboBox<String> comboRole;

    @FXML
    private Label lblEmployeeId;

    @FXML
    private Label lblSelect;

    @FXML
    private TableView<EmployeeTM> tblEmployee;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtRole;

    private Validate validate = new Validate();
    private EmployeeModel employeeModel = new EmployeeModel();
    private NewPopUpWindow newPopUpWindow = new NewPopUpWindow();

    @FXML
    void addData(ActionEvent event) throws SQLException {
        String id = lblEmployeeId.getText();
        String name = txtEmployeeName.getText();
        String phone = txtPhoneNumber.getText();
        String role = txtRole.getText();
        String email = txtEmail.getText();
        LocalDate date = LocalDate.now();

        boolean isValidName = name.matches(validate.namePattern);
        boolean isValidPhone = phone.matches(validate.phonePattern);
        boolean isValidEmail = email.matches(validate.emailPattern);
        txtEmployeeName.setStyle(txtEmployeeName.getStyle() + " ;-fx-border-colour: #00a8ff;");
        txtPhoneNumber.setStyle(txtPhoneNumber.getStyle() + " ;-fx-border-colour: #00a8ff;");
        txtEmail.setStyle(txtEmail.getStyle() + " ;-fx-border-colour: #00a8ff;");
        if (!isValidName){
            txtEmployeeName.setStyle(txtEmployeeName.getStyle() + " ;-fx-border-colour: red;");
        }
        if (!isValidPhone){
            txtPhoneNumber.setStyle(txtPhoneNumber.getStyle() + " ;-fx-border-colour: red;");
        }
        if (!isValidEmail){
            txtEmail.setStyle(txtEmail.getStyle() + " ;-fx-border-colour: red;");
        }
        if (isValidName && isValidPhone && isValidEmail) {
            EmployeeDto employeeDto = new EmployeeDto(id,name,role,phone,email,date);
            boolean isSaved = employeeModel.saveEmployee(employeeDto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Employee added successfully",ButtonType.OK).show();
                refreshPage();
            }else {
                new Alert(Alert.AlertType.ERROR,"Employee added not successfully",ButtonType.OK).show();
            }
        }
    }

    @FXML
    void deleteData(ActionEvent event) throws SQLException {
        String employeeId = lblEmployeeId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this employee?", ButtonType.YES);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES){
            boolean isDeleted = employeeModel.deleteCustomer(employeeId);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION, "Employee Deleted", ButtonType.OK).show();
                refreshPage();
            }else {
                new Alert(Alert.AlertType.ERROR, "Employee Not Deleted", ButtonType.OK).show();
            }
        }
    }

    @FXML
    void generateReport(ActionEvent event) throws IOException {

    }

    @FXML
    void sendGmail(ActionEvent event) throws IOException {
        String email = txtEmail.getText();
        newPopUpWindow.newWindowPopUpEmail("/view/SendMail.fxml",email);
    }

    @FXML
    void onClickedTable(MouseEvent event) {
        EmployeeTM selectedEmployee = tblEmployee.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null){
            lblEmployeeId.setText(selectedEmployee.getEmployeeId());
            txtEmployeeName.setText(selectedEmployee.getName());
            txtRole.setText(selectedEmployee.getRole());
            txtEmail.setText(selectedEmployee.getEmail());
            txtPhoneNumber.setText(selectedEmployee.getPhone());
            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnSend.setDisable(false);
        }
    }

//    @FXML
//    void selectRole(ActionEvent event) {
//        String role = comboRole.getSelectionModel().getSelectedItem();
//        lblSelect.setText(role);
//    }

    @FXML
    void updateData(ActionEvent event) throws SQLException {
        String id = lblEmployeeId.getText();
        String name = txtEmployeeName.getText();
        String role = txtRole.getText();
        String email = txtEmail.getText();
        String phone = txtPhoneNumber.getText();
        LocalDate date = LocalDate.now();

        boolean isValidName = name.matches(validate.namePattern);
        boolean isValidEmail = email.matches(validate.emailPattern);
        boolean isValidPhone = phone.matches(validate.phonePattern);
        txtEmployeeName.setStyle(txtEmployeeName.getStyle() + "-fx-border-color:  #00a8ff; " );
        txtEmail.setStyle(txtEmail.getStyle() + "-fx-border-color:  #00a8ff; " );
        txtPhoneNumber.setStyle(txtPhoneNumber.getStyle() + "-fx-border-color:  #00a8ff; " );
        if (!isValidName){
            txtEmployeeName.setStyle(txtEmployeeName.getStyle() + "-fx-border-color:  red; " );
        }
        if (!isValidEmail){
            txtEmail.setStyle(txtEmail.getStyle() + "-fx-border-color:  red; " );
        }
        if (!isValidPhone){
            txtPhoneNumber.setStyle(txtPhoneNumber.getStyle() + "-fx-border-color:  red; " );
        }

        if (isValidName && isValidEmail && isValidPhone){
            EmployeeDto employeeDto = new EmployeeDto(id,name,role,phone,email,date);
            boolean isUpdated = employeeModel.updateEmployee(employeeDto);
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION, "Employee Updated", ButtonType.OK).show();
                refreshPage();
            }else {
                new Alert(Alert.AlertType.ERROR, "Employee Not Updated", ButtonType.OK).show();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        columnRole.setCellValueFactory(new PropertyValueFactory<>("Role"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        try {
//            ObservableList<String> roles = FXCollections.observableArrayList("Manager", "Stock Manager", "Cashier");
//            comboRole.setItems(roles);
            refreshPage();
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    public void refreshPage() throws SQLException {
        refreshTable();
        String employeeId = employeeModel.nextEmployeeId();
        lblEmployeeId.setText(employeeId);

        txtEmployeeName.setText("");
        txtPhoneNumber.setText("");
        txtEmail.setText("");
        txtRole.setText("");
//        comboRole.getItems().clear();

        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnSend.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<EmployeeDto> employees = employeeModel.getAllEmployee();
        ObservableList<EmployeeTM> employeeTMS = FXCollections.observableArrayList();
        for (EmployeeDto employee : employees) {
            EmployeeTM employeeTM = new EmployeeTM();
            employeeTM.setEmployeeId(employee.getId());
            employeeTM.setName(employee.getName());
            employeeTM.setRole(employee.getRole());
            employeeTM.setPhone(employee.getPhoneNumber());
            employeeTM.setEmail(employee.getEmail());
            employeeTM.setDate(employee.getDate());
            employeeTMS.add(employeeTM);
        }
        tblEmployee.setItems(employeeTMS);
    }

    @FXML
    void resetPage(ActionEvent event) throws SQLException {
        lblEmployeeId.setText(employeeModel.nextEmployeeId());
        txtEmployeeName.setText("");
        txtEmail.setText("");
        txtPhoneNumber.setText("");
        txtRole.setText("");
        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnSend.setDisable(true);
        refreshPage();
    }

    @FXML
    void searchEmploye(MouseEvent event) throws SQLException {
        String id = txtSearch.getText();
        ArrayList<EmployeeDto> employees = employeeModel.search(id);
        ObservableList<EmployeeTM> employeeTMS = FXCollections.observableArrayList();
        for (EmployeeDto employee : employees) {
            EmployeeTM employeeTM = new EmployeeTM();
            employeeTM.setEmployeeId(employee.getId());
            employeeTM.setName(employee.getName());
            employeeTM.setRole(employee.getRole());
            employeeTM.setPhone(employee.getPhoneNumber());
            employeeTM.setEmail(employee.getEmail());
            employeeTM.setDate(employee.getDate());
            employeeTMS.add(employeeTM);
        }
        tblEmployee.setItems(employeeTMS);
    }

}
