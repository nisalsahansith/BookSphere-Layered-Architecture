package com.project.booksphere.controller;

import com.project.booksphere.dto.tm.ViewStockTM;
import com.project.booksphere.model.StockModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewStockController implements Initializable {

    @FXML
    private TableColumn<ViewStockTM, Integer> ColumnQty;


    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnReset;

    @FXML
    private TableColumn<ViewStockTM, Double> columnBuyPrice;

    @FXML
    private TableColumn<ViewStockTM, String> columnItemID;

    @FXML
    private TableColumn<ViewStockTM, String> columnName;

    @FXML
    private TableColumn<ViewStockTM, Double> columnSellPrice;

    @FXML
    private TableColumn<ViewStockTM, String> columnStockID;

    @FXML
    private TableColumn<ViewStockTM, String> columnSupplierID;

    @FXML
    private TableColumn<ViewStockTM, String> columnSupplierName;

    @FXML
    private TableColumn<ViewStockTM, String> columnUserID;

    @FXML
    private TableView<ViewStockTM> tblStockView;

    @FXML
    private TextField txtSearch;

    @FXML
    private Label lblStockId;

    @FXML
    private TextField txtBuyPrice;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSellPrice;

    StockModel stockModel = new StockModel();


    @FXML
    void resetPage(ActionEvent event) throws SQLException {
        refreshTable();
        txtSearch.setText("");
    }

    @FXML
    void searchOrder(MouseEvent event) throws SQLException {
        String id = txtSearch.getText();
        ArrayList<ViewStockTM> stockTMS = stockModel.searchID(id);
        ObservableList<ViewStockTM> viewStockTMS = FXCollections.observableArrayList();
        for (ViewStockTM viewStockTM: stockTMS){
            ViewStockTM viewStockTms = new ViewStockTM(
                    viewStockTM.getStockId(),
                    viewStockTM.getName(),
                    viewStockTM.getItemId(),
                    viewStockTM.getQty(),
                    viewStockTM.getSellPrice(),
                    viewStockTM.getBuyPrice(),
                    viewStockTM.getSupplierId(),
                    viewStockTM.getSupplierName(),
                    viewStockTM.getUserId()
            );
            viewStockTMS.add(viewStockTms);
        }
        tblStockView.setItems(viewStockTMS);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnStockID.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnItemID.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        ColumnQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        columnSellPrice.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
        columnBuyPrice.setCellValueFactory(new PropertyValueFactory<>("buyPrice"));
        columnSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        columnSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        columnUserID.setCellValueFactory(new PropertyValueFactory<>("userId"));
        try {
            refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void onClick(MouseEvent event) {
        ViewStockTM selected = tblStockView.getSelectionModel().getSelectedItem();
        if (selected != null){
            lblStockId.setText(selected.getStockId());
            txtName.setText(selected.getName());
            txtQty.setText(String.valueOf(selected.getQty()));
            txtSellPrice.setText(String.valueOf(selected.getSellPrice()));
            txtBuyPrice.setText(String.valueOf(selected.getBuyPrice()));
        }
        btnUpdate.setDisable(false);
    }


    @FXML
    void updateStock(ActionEvent event) throws SQLException {
        String id = lblStockId.getText();
        String name = txtName.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double sellPrice = Double.parseDouble(txtSellPrice.getText());
        double buyPrice = Double.parseDouble(txtBuyPrice.getText());
        boolean isUpdated = stockModel.updateStock(id,name,qty,sellPrice,buyPrice);
        if (isUpdated){
            refresh();
            new Alert(Alert.AlertType.INFORMATION,"Stock Updated",ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Stock not Updated",ButtonType.OK).show();
        }

    }

    public void refresh() throws SQLException {
        refreshTable();
        txtSearch.setText("");
        lblStockId.setText("");
        txtName.setText("");
        txtQty.setText("");
        txtBuyPrice.setText("");
        txtSellPrice.setText("");
        btnUpdate.setDisable(true);
    }


    public void refreshTable() throws SQLException {
        ArrayList<ViewStockTM> stockTMS = stockModel.getAll();
        ObservableList<ViewStockTM> viewStockTMS = FXCollections.observableArrayList();
        for (ViewStockTM viewStockTM: stockTMS){
            ViewStockTM viewStockTms = new ViewStockTM(
                    viewStockTM.getStockId(),
                    viewStockTM.getName(),
                    viewStockTM.getItemId(),
                    viewStockTM.getQty(),
                    viewStockTM.getSellPrice(),
                    viewStockTM.getBuyPrice(),
                    viewStockTM.getSupplierId(),
                    viewStockTM.getSupplierName(),
                    viewStockTM.getUserId()
            );
            viewStockTMS.add(viewStockTms);
        }
        tblStockView.setItems(viewStockTMS);
    }

}
