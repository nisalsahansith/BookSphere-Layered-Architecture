package com.project.booksphere.controller;

import com.project.booksphere.dto.ItemDto;
import com.project.booksphere.dto.tm.ItemDetailTM;
import com.project.booksphere.dto.tm.ItemTm;
import com.project.booksphere.model.ItemModel;
import com.project.booksphere.util.SharedInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageInventoryController implements Initializable {

    @FXML
    private AnchorPane bodyPane;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnAddExisting;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReport;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<ItemTm, String> columnDescription;

    @FXML
    private TableColumn<ItemTm, String> columnIsbn;

    @FXML
    private TableColumn<ItemTm, String> columnItemId;

    @FXML
    private TableColumn<ItemTm, Double> columnPrice;

    @FXML
    private TableColumn<ItemTm, Integer> columnQty;

    @FXML
    private Label lblItemId;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtIsbn;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSearch;

    ItemModel itemModel = new ItemModel();
    SharedInfo sharedInfo = SharedInfo.getInstance();

    @FXML
    void addExistingItem(ActionEvent event) throws IOException {
        String id = lblItemId.getText();
        sharedInfo.setItemId(id);
        navigateTo("/view/ItemDetail.fxml");
    }

    @FXML
    void addItem(ActionEvent event) throws IOException, SQLException {
        String id = lblItemId.getText();
        sharedInfo.setItemId(id);
        String isbn = txtIsbn.getText();
        String description = txtDescription.getText();
//        String price = txtPrice.getText();
        int qty = Integer.parseInt(txtQty.getText());


        ItemDto itemDto = new ItemDto(id,description,isbn,qty);
        boolean isSaved = itemModel.saveItem(itemDto);
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION,"Item is Saved",ButtonType.OK).show();
            sharedInfo.setItemId(id);
            navigateTo("/view/ItemDetail.fxml");
        }else {
            new Alert(Alert.AlertType.ERROR,"Item is not Saved!",ButtonType.OK).show();
        }
    }

    @FXML
    void deleteItem(ActionEvent event) throws SQLException {
        String id = lblItemId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you Sure want to delete this Item",ButtonType.YES);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES){
            boolean isDeleted = itemModel.deleteItem(id);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Item deleted",ButtonType.OK).show();
                refreshPage();
            }else {
                new Alert(Alert.AlertType.INFORMATION,"Item is not Deleted",ButtonType.OK).show();
            }
        }else {
            System.out.println("not found customer");
        }
    }

    @FXML
    void resetPage(ActionEvent event) throws SQLException {
        lblItemId.setText("");
        txtDescription.setText("");
        txtIsbn.setText("");
        txtQty.setText("");
        txtSearch.setText("");
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnAdd.setDisable(false);
        btnAddExisting.setDisable(false);
        refreshPage();
    }

    @FXML
    void updateItem(ActionEvent event) throws SQLException {
        String id = lblItemId.getText();
        String isbn = txtIsbn.getText();
        String description = txtDescription.getText();
        ItemDto itemDto = new ItemDto(id,description,isbn,0);
        boolean isUpdated = itemModel.updateItem(itemDto);
        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION,"Item is Updated",ButtonType.OK).show();
            refreshPage();
        }else {
            new Alert(Alert.AlertType.ERROR,"Item is not Updated!",ButtonType.OK).show();
        }
    }

    @FXML
    void searchItems(MouseEvent event) throws SQLException {
        String search = txtSearch.getText();
        ArrayList<ItemTm> itemTms = null;
         itemTms = itemModel.search(search);
         if (itemTms .isEmpty()) {
             itemTms = itemModel.searchByName(search);
         }
         if (!itemTms.isEmpty()) {
             ObservableList<ItemTm> itemTmsObservableList = FXCollections.observableArrayList();
             for (ItemTm itemTm : itemTms) {
                 ItemTm itemTM = new ItemTm();
                 itemTM.setId(itemTm.getId());
                 itemTM.setDescription(itemTm.getDescription());
                 itemTM.setQuantity(itemTm.getQuantity());
                 itemTM.setISBN(itemTm.getISBN());
                 itemTmsObservableList.add(itemTM);
             }
             tblItem.setItems(itemTmsObservableList);
         }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnItemId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
//        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnIsbn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));

        try {
            refreshPage();
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();
        String itemId = itemModel.nextItemId();
        lblItemId.setText(itemId);
        txtDescription.setText("");
        txtIsbn.setText("");
//        txtPrice.setText("");
        txtQty.setText(String.valueOf(0));
        txtQty.setDisable(true);

        btnAdd.setDisable(false);
        btnAddExisting.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<ItemDto> item = itemModel.getAllItem();
        ObservableList<ItemTm> itemTms = FXCollections.observableArrayList();
        for (ItemDto itemDto : item) {
            ItemTm itemTm = new ItemTm();
            itemTm.setId(itemDto.getId());
            itemTm.setDescription(itemDto.getDescription());
//            itemTm.setPrice(itemDto.getPrice());
            itemTm.setQuantity(itemDto.getQty());
            itemTm.setISBN(itemDto.getISBN());
            itemTms.add(itemTm);
        }
        tblItem.setItems(itemTms);
    }

    @FXML
    void onCLickedTable(MouseEvent event) {
        ItemTm selectItem = tblItem.getSelectionModel().getSelectedItem();
        if (selectItem != null){
            lblItemId.setText(selectItem.getId());
            txtDescription.setText(selectItem.getDescription());
            txtIsbn.setText(selectItem.getISBN());
            txtQty.setText(String.valueOf(selectItem.getQuantity()));
            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnAddExisting.setDisable(false);
        }
    }

    public void navigateTo(String path) {
        try {
            bodyPane.getChildren().clear();
            AnchorPane load =  FXMLLoader.load(getClass().getResource(path));
            load.prefHeightProperty().bind(bodyPane.widthProperty());
            load.prefHeightProperty().bind(bodyPane.heightProperty());
            bodyPane.getChildren().add(load);
        }catch (IOException e){
            new Alert(Alert.AlertType.ERROR,"Fail to load page "+path).showAndWait();
            e.printStackTrace();
        }
    }
}
