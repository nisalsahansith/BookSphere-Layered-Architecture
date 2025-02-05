package com.project.booksphere.controller;

import com.project.booksphere.bo.BOFactory;
import com.project.booksphere.bo.custom.ViewItemBo;
import com.project.booksphere.bo.custom.impl.ViewItemBOImpl;
import com.project.booksphere.dto.CustomDto;
import com.project.booksphere.tm.ViewItemTM;
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

//    private final ItemModel itemModel = new ItemModel();
//    private final ItemDAO itemDAO = new ItemDAOImpl();
    private final ViewItemBo viewItemBo = (ViewItemBo) BOFactory.getInstance().getBO(BOFactory.BOType.VIEW_ITEM);

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
        ArrayList<CustomDto> viewItemTMS = null;
         viewItemTMS = viewItemBo.searchFromID(search);
         if (viewItemTMS.isEmpty()) {
             viewItemTMS = viewItemBo.searchFromName(search);
         }
        ObservableList<ViewItemTM> viewOrderTMs = FXCollections.observableArrayList();
        for (CustomDto viewItemTM : viewItemTMS) {
            ViewItemTM viewOrderTmS = new ViewItemTM(
                    viewItemTM.getItemId(),
                    viewItemTM.getItemDescription(),
                    viewItemTM.getISBN(),
                    viewItemTM.getSellPrice(),
                    viewItemTM.getQtyOnHand(),
                    viewItemTM.getStockId(),
                    viewItemTM.getSupName()
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
        ArrayList<CustomDto> itemTMS = viewItemBo.getAllItems(); //join query
        ObservableList<ViewItemTM> viewItemTMS = FXCollections.observableArrayList();
        for (CustomDto itemTM : itemTMS){
            ViewItemTM viewItemTM = new ViewItemTM(
                    itemTM.getItemId(),
                    itemTM.getItemDescription(),
                    itemTM.getISBN(),
                    itemTM.getSellPrice(),
                    itemTM.getQtyOnHand(),
                    itemTM.getStockId(),
                    itemTM.getSupName()
            );
            viewItemTMS.addAll(viewItemTM);
        }
        tblItemView.setItems(viewItemTMS);
    }
}
