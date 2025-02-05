package com.project.booksphere.controller;

import com.project.booksphere.bo.BOFactory;
import com.project.booksphere.bo.custom.ItemDetailBo;
import com.project.booksphere.bo.custom.impl.ItemDetailBOImpl;
import com.project.booksphere.dto.ItemDetailDto;
import com.project.booksphere.dto.StockDetailDto;
import com.project.booksphere.dto.StockDto;
import com.project.booksphere.dto.SupplierDto;
import com.project.booksphere.tm.ItemDetailTM;
import com.project.booksphere.util.NavigationPage;
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
import java.util.ResourceBundle;

public class ItemDetailController implements Initializable {

    @FXML
    private AnchorPane bodyPane;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnBack;

    @FXML
    private TableColumn<String, ItemDetailTM> columnItemID;

    @FXML
    private TableColumn<String, ItemDetailTM> columnQty;

    @FXML
    private TableColumn<String, ItemDetailTM> columnSellPrice;

    @FXML
    private TableColumn<String, ItemDetailTM> columnStockID;

    @FXML
    private Label lblAddSupplier;

    @FXML
    private Label lblDetail;

    @FXML
    private TableView<ItemDetailTM> tblItemDetail;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtItemID;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSellPrice;

    @FXML
    private TextField txtStockID;

    @FXML
    private TextField txtSupplyID;

    @FXML
    private TextField txtUniPrice;

//    ItemDetailModel itemDetailModel = new ItemDetailModel();
//    SupplierModel supplierModel = new SupplierModel();
//    StockModel stockModel = new StockModel();

//    ItemDetailDAO itemDetailDAO = new ItemDetailDAOImpl();
//    SupplierDAO supplierDAO = new SupplierDAOImpl();
//    StockDAO stockDAO = new StockDAOImpl();

    private final ItemDetailBo itemDetailBo = (ItemDetailBo) BOFactory.getInstance().getBO(BOFactory.BOType.ITEM_DETAIL);

    SharedInfo sharedInfo = SharedInfo.getInstance();
    @FXML
    void saveOnAction(ActionEvent event) throws SQLException {
        String stockID = txtStockID.getText();
        String itemID = txtItemID.getText();
        String supplyID = txtSupplyID.getText();
        String stockDesc = txtDesc.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double sellPrice = Double.parseDouble(txtSellPrice.getText());
        double unitPrice = Integer.parseInt(txtUniPrice.getText());
        String userID = sharedInfo.getUserID();

        ItemDetailDto itemDetailDto = new ItemDetailDto(itemID,stockID,sellPrice,qty);
        StockDto stockDto = new StockDto(stockID,stockDesc,userID);
        StockDetailDto stockDetailDto = new StockDetailDto(stockID,supplyID,unitPrice,qty);

        boolean isSaved = itemDetailBo.saveItemDetails(itemDetailDto,stockDto,stockDetailDto);
        if (isSaved){
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION,"Item Added success full",ButtonType.OK).show();
            navigateTo("/view/ManageInventory.fxml");
        }else {
            new Alert(Alert.AlertType.ERROR,"Item not Added",ButtonType.OK).show();
        }
    }

    @FXML
    void searchSupplier(ActionEvent event) throws SQLException {
        String id = txtSupplyID.getText();
        ArrayList<SupplierDto> supId = itemDetailBo.searchSupplier(id);
        SupplierDto supplierDto;
        String supplierId = "No Supplier";
        if (!supId.isEmpty()){
            supplierDto = supId.getFirst();
            supplierId = supplierDto.getSupId();
        }
        lblDetail.setText(supplierId);
        if (supId.equals("No Supplier")){
            lblAddSupplier.setVisible(true);
        }else {
            lblAddSupplier.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnItemID.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        columnStockID.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        columnQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        columnSellPrice.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
        try {
            refreshPage();
        }catch (SQLException e){
            throw (RuntimeException) new RuntimeException("Error in refresh page").initCause(e);
        }
    }

    @FXML
    void backPage(ActionEvent event) throws IOException {
        navigateTo("/view/ManageInventory.fxml");
    }

    private void refreshPage() throws SQLException {
        refreshTable();
        txtStockID.setText(itemDetailBo.nextItemId());
        txtItemID.setText(sharedInfo.getItemId());
        txtQty.setText("");
        txtSellPrice.setText("");
        txtSupplyID.setText("");
        txtUniPrice.setText("");
        txtDesc.setText("");

//        btnSave.setDisable(true);
//        btnSearch.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<ItemDetailDto> itemDetail = itemDetailBo.getAllItems();
        ObservableList<ItemDetailTM> itemDetailTMS = FXCollections.observableArrayList();
        for (ItemDetailDto itemDetailTM : itemDetail){
            ItemDetailTM itemDetailTms = new ItemDetailTM();
            itemDetailTms.setItemId(itemDetailTM.getItemId());
            itemDetailTms.setStockId(itemDetailTM.getStockId());
            itemDetailTms.setQty(itemDetailTM.getQty());
            itemDetailTms.setSellPrice(itemDetailTM.getSellPrice());
            itemDetailTMS.add(itemDetailTms);
        }
        tblItemDetail.setItems(itemDetailTMS);
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

    @FXML
    void popUpSupplierPage(MouseEvent event) throws IOException {
        NavigationPage.newWindowPopUp("/view/ManageSupplier.fxml");
    }
}