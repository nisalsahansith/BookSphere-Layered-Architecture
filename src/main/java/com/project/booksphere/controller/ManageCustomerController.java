package com.project.booksphere.controller;

import com.project.booksphere.dto.CustomerDto;
import com.project.booksphere.dto.tm.CustomerTM;
import com.project.booksphere.model.CustomerModel;
import com.project.booksphere.model.UserDetailModel;
import com.project.booksphere.util.NewPopUpWindow;
import com.project.booksphere.util.SharedInfo;
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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageCustomerController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReport;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnSend;

    @FXML
    private TableColumn<CustomerTM, String> columnEmail;

    @FXML
    private TableColumn<CustomerTM, String> columnId;

    @FXML
    private TableColumn<CustomerTM, String> columnName;

//    @FXML
//    private TableColumn<CustomerTM, String> columnOrderId;

    @FXML
    private TableColumn<CustomerTM, String> columnPhone;

    @FXML
    private Label lblCustomerId;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtPhoneNumber;

    private Validate validate = new Validate();
    private CustomerModel customerModel = new CustomerModel();
    NewPopUpWindow newPopUpWindow = new NewPopUpWindow();


    @FXML
    void addData(ActionEvent event) throws SQLException {
        String id = lblCustomerId.getText();
        String name = txtCustomerName.getText();
        String email = txtEmail.getText();
        String phone = txtPhoneNumber.getText();
        String orderId = "";

        boolean isValidName = name.matches(validate.namePattern);
        boolean isValidEmail = email.matches(validate.emailPattern);
        boolean isValidPhone = phone.matches(validate.phonePattern);
        txtCustomerName.setStyle(txtCustomerName.getStyle() + "-fx-border-color:  #00a8ff; " );
        txtEmail.setStyle(txtEmail.getStyle() + "-fx-border-color:  #00a8ff; " );
        txtPhoneNumber.setStyle(txtPhoneNumber.getStyle() + "-fx-border-color:  #00a8ff; " );
        if (!isValidName){
            txtCustomerName.setStyle(txtCustomerName.getStyle() + "-fx-border-color:  red; " );
        }
        if (!isValidEmail){
            txtEmail.setStyle(txtEmail.getStyle() + "-fx-border-color:  red; " );
        }
        if (!isValidPhone){
            txtPhoneNumber.setStyle(txtPhoneNumber.getStyle() + "-fx-border-color:  red; " );
        }

        if (isValidName && isValidEmail && isValidPhone && id != "" && name != "" && email != "" && phone != ""){
            CustomerDto customerDto = new CustomerDto(id,name,phone,email,orderId);

            boolean isSaved = customerModel.saveCustomer(customerDto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION, "Customer Added Successfully", ButtonType.OK).show();
                refreshPage();
            }else {
               new Alert(Alert.AlertType.ERROR, "Customer Not Saved", ButtonType.OK).show();
            }
        }

    }

    @FXML
    void deleteData(ActionEvent event) throws SQLException {
        String customerId = lblCustomerId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?", ButtonType.YES);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES){
            boolean isDeleted = customerModel.deleteCustomer(customerId);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION, "Customer Deleted", ButtonType.OK).show();
                refreshPage();
            }else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Deleted", ButtonType.OK).show();
            }
        }
    }

    @FXML
    void generateReport(ActionEvent event) {

    }

    @FXML
    void sendGmail(ActionEvent event) throws IOException {
        String email = txtEmail.getText();
        newPopUpWindow.newWindowPopUpEmail("/view/SendMail.fxml",email);
    }

    @FXML
    void updateData(ActionEvent event) throws SQLException {
        String id = lblCustomerId.getText();
        String name = txtCustomerName.getText();
        String email = txtEmail.getText();
        String phone = txtPhoneNumber.getText();
        String orderId = "";

        boolean isValidName = name.matches(validate.namePattern);
        boolean isValidEmail = email.matches(validate.emailPattern);
        boolean isValidPhone = phone.matches(validate.phonePattern);
        txtCustomerName.setStyle(txtCustomerName.getStyle() + "-fx-border-color:  #00a8ff; " );
        txtEmail.setStyle(txtEmail.getStyle() + "-fx-border-color:  #00a8ff; " );
        txtPhoneNumber.setStyle(txtPhoneNumber.getStyle() + "-fx-border-color:  #00a8ff; " );
        if (!isValidName ){
            txtCustomerName.setStyle(txtCustomerName.getStyle() + "-fx-border-color:  red; " );
        }
        if (!isValidEmail ){
            txtEmail.setStyle(txtEmail.getStyle() + "-fx-border-color:  red; " );
        }
        if (!isValidPhone ){
            txtPhoneNumber.setStyle(txtPhoneNumber.getStyle() + "-fx-border-color:  red; " );
        }

        if (isValidName && isValidEmail && isValidPhone ){
            CustomerDto customerDto = new CustomerDto(id,name,phone,email,orderId);
            boolean isUpdated = customerModel.updateCustomer(customerDto);
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION, "Customer Updated", ButtonType.OK).show();
                refreshPage();
            }else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Updated", ButtonType.OK).show();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
//        columnOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        try {
            refreshPage();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void refreshPage() throws SQLException {
        refreshTable();
        String nextCustomerId = customerModel.nextCustomerId();
        lblCustomerId.setText(nextCustomerId);

        txtCustomerName.setText("");
        txtEmail.setText("");
        txtPhoneNumber.setText("");

        btnAdd.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnSend.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<CustomerDto> customerDtos = customerModel.loadCustomerDetails();
        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();
        for (CustomerDto customerDto : customerDtos){
            CustomerTM customerTM = new CustomerTM(
                customerDto.getId(),
                customerDto.getName(),
                customerDto.getPhone(),
                customerDto.getEmail()
//                customerDto.getOrderId()
            );
            customerTMS.add(customerTM);
        }
        tblCustomer.setItems(customerTMS);
    }

    @FXML
    void onClickedTable(MouseEvent event) {
        CustomerTM selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null){
            lblCustomerId.setText(selectedCustomer.getCustomerId());
            txtCustomerName.setText(selectedCustomer.getCustomerName());
            txtEmail.setText(selectedCustomer.getCustomerEmail());
            txtPhoneNumber.setText(selectedCustomer.getCustomerPhone());
            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnSend.setDisable(false);
        }
    }

    @FXML
    void resetPage(ActionEvent event) throws SQLException {
        lblCustomerId.setText(customerModel.nextCustomerId());
        txtCustomerName.setText("");
        txtEmail.setText("");
        txtPhoneNumber.setText("");
        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnSend.setDisable(true);
        refreshPage();
    }


    @FXML
    void searchCustomer(MouseEvent event) throws SQLException {
        String searchText = txtSearch.getText();
        ArrayList<CustomerDto> customerDtos = customerModel.searchByCustID(searchText);
        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();
        for (CustomerDto customerDto : customerDtos){
            CustomerTM customerTM = new CustomerTM(
                    customerDto.getId(),
                    customerDto.getName(),
                    customerDto.getPhone(),
                    customerDto.getEmail()
//                customerDto.getOrderId()
            );
            customerTMS.add(customerTM);
        }
        tblCustomer.setItems(customerTMS);
    }

}
