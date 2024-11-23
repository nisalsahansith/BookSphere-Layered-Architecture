package com.project.booksphere.controller;

import com.project.booksphere.dto.tm.ViewOrderTM;
import com.project.booksphere.model.OrdersModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewOrderController implements Initializable {


    @FXML
    private Button btnReset;

    @FXML
    private Button btnDelete;

    @FXML
    private TableColumn<ViewOrderTM, String> columnCustomerUD;

    @FXML
    private TableColumn<ViewOrderTM, Date> columnDate;

    @FXML
    private TableColumn<ViewOrderTM, String> columnDesc;

    @FXML
    private TableColumn<ViewOrderTM, String> columnOrderID;

    @FXML
    private TableColumn<ViewOrderTM, Integer> columnOrderQty;

    @FXML
    private TableColumn<ViewOrderTM, String> columnStockID;

    @FXML
    private TableColumn<ViewOrderTM, Double> columnTotal;

    @FXML
    private TableColumn<ViewOrderTM, String> columnUserID;

    @FXML
    private TableView<ViewOrderTM> tblOrdersView;

    @FXML
    private TextField txtSearch;

    OrdersModel ordersModel = new OrdersModel();

    @FXML
    void searchOrder(MouseEvent event) throws SQLException {
        String id = txtSearch.getText();
        ArrayList<ViewOrderTM> viewOrderTmS = ordersModel.searchFromOrderID(id);
        ObservableList<ViewOrderTM> viewOrderTMS = FXCollections.observableArrayList();
        for (ViewOrderTM viewOrderTM : viewOrderTmS) {
            ViewOrderTM viewOrderTMs = new ViewOrderTM(
                    viewOrderTM.getOrderID(),
                    viewOrderTM.getCustomerID(),
                    viewOrderTM.getUserID(),
                    viewOrderTM.getTotal(),
                    viewOrderTM.getDate(),
                    viewOrderTM.getQty(),
                    viewOrderTM.getDesc(),
                    viewOrderTM.getStockID()
            );
            viewOrderTMS.add(viewOrderTMs);
        }
        tblOrdersView.setItems(viewOrderTMS);
    }

    @FXML
    void resetPage(ActionEvent event) throws SQLException {
        refreshTable();
        txtSearch.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnOrderID.setCellValueFactory(new PropertyValueFactory<>("OrderID"));
        columnCustomerUD.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        columnUserID.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        columnTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnOrderQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        columnDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        columnStockID.setCellValueFactory(new PropertyValueFactory<>("stockID"));
        try {
            refreshTable();
            txtSearch.setText("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void deleteOrders(ActionEvent event) throws SQLException {
        String id = tblOrdersView.getSelectionModel().getSelectedItem().getOrderID();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you Sure want to delete this Order",ButtonType.YES);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES){
            boolean isDeleted = ordersModel.deleteItem(id);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Order deleted",ButtonType.OK).show();
                txtSearch.setText("");
                refreshTable();
            }else {
                new Alert(Alert.AlertType.INFORMATION,"Order is not Deleted",ButtonType.OK).show();
            }
        }
    }

    public void refreshTable() throws SQLException {
        ArrayList<ViewOrderTM> orderTMS = ordersModel.getOrdersAll();
        ObservableList<ViewOrderTM> viewOrderTMS = FXCollections.observableArrayList();
        for (ViewOrderTM viewOrderTM : orderTMS){
            ViewOrderTM viewOrderTMs = new ViewOrderTM(
                    viewOrderTM.getOrderID(),
                    viewOrderTM.getCustomerID(),
                    viewOrderTM.getUserID(),
                    viewOrderTM.getTotal(),
                    viewOrderTM.getDate(),
                    viewOrderTM.getQty(),
                    viewOrderTM.getDesc(),
                    viewOrderTM.getStockID()
            );
            viewOrderTMS.add(viewOrderTMs);
        }
        tblOrdersView.setItems(viewOrderTMS);
    }
}
