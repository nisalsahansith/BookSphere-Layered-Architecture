package com.project.booksphere.controller;

import com.project.booksphere.dto.tm.ItemTm;
import com.project.booksphere.dto.tm.ViewItemTM;
import com.project.booksphere.dto.tm.ViewOrderTM;
import com.project.booksphere.model.ItemModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewItemController implements Initializable {

    @FXML
    private TableColumn<ViewItemTM, String> ColumnName;

    @FXML
    private Button btnReset;

    @FXML
    private TableColumn<ViewItemTM, String> columDesc;

    @FXML
    private TableColumn<ViewItemTM, Double> columPrice;

    @FXML
    private TableColumn<ViewItemTM, String> columnISBN;

    @FXML
    private TableColumn<ViewItemTM, String> columnItemId;

    @FXML
    private TableColumn<ViewItemTM, Integer> columnQty;

    @FXML
    private TableColumn<ViewItemTM, String> columnStockId;

    @FXML
    private TableView<ViewItemTM> tblItemView;

    private final ItemModel itemModel = new ItemModel();

    @FXML
    private TextField txtSearch;

    @FXML
    void resetPage(ActionEvent event) throws SQLException {
        refreshTable();
        txtSearch.setText("");
    }

    @FXML
    void searchOrder(MouseEvent event) throws SQLException {
        String search = txtSearch.getText();
        ArrayList<ViewItemTM> viewItemTMS = null;
         viewItemTMS = itemModel.searchFromID(search);
         if (viewItemTMS.isEmpty()) {
             viewItemTMS = itemModel.searchFromName(search);
         }
        ObservableList<ViewItemTM> viewOrderTMs = FXCollections.observableArrayList();
        for (ViewItemTM viewItemTM : viewItemTMS) {
            ViewItemTM viewOrderTmS = new ViewItemTM(
                    viewItemTM.getItemId(),
                    viewItemTM.getDesc(),
                    viewItemTM.getIsbn(),
                    viewItemTM.getPrice(),
                    viewItemTM.getQty(),
                    viewItemTM.getStockId(),
                    viewItemTM.getName()
            );
            viewOrderTMs.add(viewOrderTmS);
        }
        tblItemView.setItems(viewOrderTMs);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        columDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        columnISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        columPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        columnStockId.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        ColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        try {
            refreshTable();
            txtSearch.setText("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshTable() throws SQLException {
        ArrayList<ViewItemTM> itemTMS = itemModel.getAllItems();
        ObservableList<ViewItemTM> viewItemTMS = FXCollections.observableArrayList();
        for (ViewItemTM itemTM : itemTMS){
            ViewItemTM viewItemTM = new ViewItemTM(
                    itemTM.getItemId(),
                    itemTM.getDesc(),
                    itemTM.getIsbn(),
                    itemTM.getPrice(),
                    itemTM.getQty(),
                    itemTM.getStockId(),
                    itemTM.getName()
            );
            viewItemTMS.addAll(viewItemTM);
        }
        tblItemView.setItems(viewItemTMS);
    }
}
