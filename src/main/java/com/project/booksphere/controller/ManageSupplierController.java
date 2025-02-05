package com.project.booksphere.controller;

import com.project.booksphere.bo.BOFactory;
import com.project.booksphere.bo.custom.SupplierBo;
import com.project.booksphere.bo.custom.impl.SupplierBOImpl;
import com.project.booksphere.dto.SupplierDto;
import com.project.booksphere.tm.SupplierTM;
import com.project.booksphere.util.NavigationPage;
import com.project.booksphere.util.SharedInfo;
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

public class ManageSupplierController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnSend;

    @FXML
    private Button btnReset;

    @FXML
    private TableColumn<SupplierTM, String> columnAddress;

    @FXML
    private TableColumn<SupplierTM, String> columnEmail;

    @FXML
    private TableColumn<SupplierTM, String> columnName;

    @FXML
    private TableColumn<SupplierTM, String> columnPhone;

    @FXML
    private TableColumn<SupplierTM, String> columnSupId;

    @FXML
    private TableColumn<SupplierTM, String> columnUser;

    @FXML
    private TableView<SupplierTM> tblSup;

    @FXML
    private Label lblSupID;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtPhoneNumber;

//    SupplierModel supplierModel = new SupplierModel();
    SharedInfo sharedInfo = SharedInfo.getInstance();
    NavigationPage navigationPage = new NavigationPage();
//    SupplierDAO supplierDAO = new SupplierDAOImpl();

    private final SupplierBo supplierBo = (SupplierBo) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPLIER);

    @FXML
    void addData(ActionEvent event) throws SQLException {
        String supId = lblSupID.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String name = txtName.getText();
        String phone = txtPhoneNumber.getText();
        String userId = sharedInfo.getUserID();
        SupplierDto supplierDto = new SupplierDto(supId,name,phone,address,email,userId);
        boolean isSaved = supplierBo.saveSupplier(supplierDto);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION,"Supplier saved successfully",ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Supplier not saved",ButtonType.OK).show();
        }
    }

    @FXML
    void deleteData(ActionEvent event) throws SQLException {
        String id = lblSupID.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Supplier?", ButtonType.YES);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES){
            boolean isDeleted = supplierBo.deleteSupplier(id);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION, "Supplier Deleted", ButtonType.OK).show();
                refreshPage();
            }else {
                new Alert(Alert.AlertType.ERROR, "Supplier Not Deleted", ButtonType.OK).show();
            }
        }
    }

    @FXML
    void updateData(ActionEvent event) throws SQLException {
        String supId = lblSupID.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String name = txtName.getText();
        String phone = txtPhoneNumber.getText();
        String userId = sharedInfo.getUserID();
        SupplierDto supplierDto = new SupplierDto(supId,name,phone,address,email,userId);
        boolean isSaved = supplierBo.updateSupplier(supplierDto);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION,"Supplier update successfully",ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Supplier not updated",ButtonType.OK).show();
        }
    }

    @FXML
    void sendGmail(ActionEvent event) throws IOException {
        String email = txtEmail.getText();
        navigationPage.newWindowPopUpEmail("/view/SendMail.fxml",email);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnSupId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnUser.setCellValueFactory(new PropertyValueFactory<>("userID"));
        try{
            refreshPage();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
        }

    }

    public void refreshPage() throws SQLException {
        refreshTable();
        lblSupID.setText(supplierBo.nextSupplierId());
        txtAddress.setText("");
        txtEmail.setText("");
        txtName.setText("");
        txtPhoneNumber.setText("");

        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnSend.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<SupplierDto> supplierDto = supplierBo.getAllSupplier();
        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();
        for (SupplierDto supplierDTO : supplierDto) {
            SupplierTM supplierTM = new SupplierTM(
                    supplierDTO.getSupId(),
                    supplierDTO.getName(),
                    supplierDTO.getPhone(),
                    supplierDTO.getAddress(),
                    supplierDTO.getEmail(),
                    supplierDTO.getUserId()
            );
            supplierTMS.add(supplierTM);
        }
        tblSup.setItems(supplierTMS);
    }

    @FXML
    void clickTable(MouseEvent event) {
        SupplierTM selectedItem = tblSup.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            lblSupID.setText(selectedItem.getSupId());
            txtName.setText(selectedItem.getName());
            txtPhoneNumber.setText(selectedItem.getPhone());
            txtAddress.setText(selectedItem.getAddress());
            txtEmail.setText(selectedItem.getEmail());
            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnSend.setDisable(false);
        }
    }

    @FXML
    void resetPage(ActionEvent event) throws SQLException {
        lblSupID.setText(supplierBo.nextSupplierId());
        txtEmail.setText("");
        txtName.setText("");
        txtPhoneNumber.setText("");
        txtAddress.setText("");
        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnSend.setDisable(true);
        refreshPage();
    }

    @FXML
    void searchSupplier(MouseEvent event) throws SQLException {
        String search = txtSearch.getText();
        ArrayList<SupplierDto> supplierDto = supplierBo.searchSupplier(search);
        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();
        for (SupplierDto supplierDTO : supplierDto) {
            SupplierTM supplierTM = new SupplierTM(
                    supplierDTO.getSupId(),
                    supplierDTO.getName(),
                    supplierDTO.getPhone(),
                    supplierDTO.getAddress(),
                    supplierDTO.getEmail(),
                    supplierDTO.getUserId()
            );
            supplierTMS.add(supplierTM);
        }
        tblSup.setItems(supplierTMS);
    }

}
