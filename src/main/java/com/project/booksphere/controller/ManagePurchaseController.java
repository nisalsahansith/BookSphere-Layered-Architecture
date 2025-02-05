package com.project.booksphere.controller;

import com.project.booksphere.bo.BOFactory;
import com.project.booksphere.bo.custom.PurchaseBo;
import com.project.booksphere.bo.custom.impl.PurchaseBOImpl;
import com.project.booksphere.db.DBConnection;
import com.project.booksphere.dto.*;
import com.project.booksphere.tm.PurchaseTM;
import com.project.booksphere.util.FocusSwitch;
import com.project.booksphere.util.NavigationPage;
import com.project.booksphere.util.PaymentInfo;
import com.project.booksphere.util.SharedInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ManagePurchaseController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private Button btnSearchCust;

    @FXML
    private Button btnSearchItem;

    @FXML
    private Button btnCancle;

    @FXML
    private TableColumn<PurchaseTM, String> columnItemID;

    @FXML
    private TableColumn<PurchaseTM, String> columnItemName;

    @FXML
    private TableColumn<PurchaseTM, Double> columnPrice;

    @FXML
    private TableColumn<PurchaseTM, Integer> columnQty;

    @FXML
    private TableColumn<PurchaseTM, Double> columnQtyPrice;

    @FXML
    private TableColumn<PurchaseTM, Button> columnRemoveButton;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblCustomer;

    @FXML
    private Label lblCustomerID;

    @FXML
    private Label lblItem;

    @FXML
    private Label lblAddCustomer;

    @FXML
    private Label lblDiscountID;

    @FXML
    private TableView<PurchaseTM> tblModel;

    @FXML
    private TextField txtCustomerID;

    @FXML
    private TextField txtDiscount;

    @FXML
    private TextField txtItemID;

    @FXML
    private TextField txtNetTotal;

    @FXML
    private TextField txtOrderID;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtTotal;

//    OrdersModel ordersModel = new OrdersModel();
    private final ObservableList<PurchaseTM> purchaseTMS = FXCollections.observableArrayList();
//    ItemModel itemModel = new ItemModel();
//    CustomerModel customerModel = new CustomerModel();
//    PromotionModel promotionModel = new PromotionModel();
    PaymentInfo paymentInfo = PaymentInfo.getInstance();
//    ItemDetailModel itemDetailModel = new ItemDetailModel();
    SharedInfo sharedInfo = SharedInfo.getInstance();

//    ItemDAO itemDAO = new ItemDAOImpl();
//    OrderDAO orderDAO = new OrderDAOImpl();
//    CustomerDAO customerDAO = new CustomerDAOImpl();
//    PromotionDAO promotionDAO = new PromotionDAOImpl();
//    ItemDetailDAO itemDetailDAO = new ItemDetailDAOImpl();

    private final PurchaseBo purchaseBo = (PurchaseBo) BOFactory.getInstance().getBO(BOFactory.BOType.PURCHASE);

    private final FocusSwitch focusSwitch = new FocusSwitch();

    @FXML
    void AddToTable(ActionEvent event) throws SQLException {
        try {
            txtQty.setStyle(txtQty.getStyle() + "-fx-border-color:  #00a8ff; " );
            String orderId = txtOrderID.getText();
            String customerId = lblCustomerID.getText();
            String itemId = txtItemID.getText();
            int qty = Integer.parseInt(txtQty.getText());
            String itemName = purchaseBo.searchItem(itemId);
            double unitPrice = purchaseBo.getSellPrice(itemId);
            double price = qty * unitPrice;
            Button btn = new Button("Remove");
            ItemDetailDto itemDetailDto = new ItemDetailDto(itemId,"",0,0);//moda wedak
            int availableQty = purchaseBo.getItemQty(itemDetailDto);

            double total = 0;
            double discountPrice = 0;
            double netTotal = 0;

            PurchaseTM purchaseTM = new PurchaseTM(
                    itemId,
                    itemName,
                    qty,
                    unitPrice,
                    price,
                    btn
            );

            if (availableQty - qty > 0) {
                btn.setOnAction(actionEvent -> {
                    purchaseTMS.remove(purchaseTM);
                    tblModel.refresh();
                    getTotal();
                    try {
                        getDiscount(getTotal());
                        getNetTotal(getTotal(), getDiscount(getTotal()));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                txtQty.setStyle(txtQty.getStyle() + "-fx-border-color: #00a8ff;");
                purchaseTMS.add(purchaseTM);
                tblModel.setItems(purchaseTMS);

                total = getTotal();
                discountPrice = getDiscount(total);
                netTotal = getNetTotal(total, discountPrice);


                txtCustomerID.setDisable(true);
                txtItemID.setText("");
                txtQty.setText("");
            } else {
                txtQty.setStyle(txtQty.getStyle() + "-fx-border-color:  red; ");
            }
        }catch (NumberFormatException e) {
            txtQty.setStyle(txtQty.getStyle() + "-fx-border-color:  red; " );        }
    }

    @FXML
    void placeOrder(ActionEvent event) throws SQLException, IOException, JRException {
        if (tblModel.getItems().isEmpty()){
            return;
        }

        String orderId = txtOrderID.getText();
        String customerId = lblCustomerID.getText();
        Date date = Date.valueOf(lblOrderDate.getText());
        double totalPrice = Double.parseDouble(txtTotal.getText());
        double netTotal = Double.parseDouble(txtNetTotal.getText());
        String discountId = lblDiscountID.getText();
        double discountPrice = Double.parseDouble(txtDiscount.getText());
        String userId = sharedInfo.getUserID();


        ArrayList<OrderDetailDto> orderDetailDtos = new ArrayList<>();//
        for (int i = 0; i < tblModel.getItems().size(); i++) {
            PurchaseTM purchaseTM = tblModel.getItems().get(i);
            OrderDetailDto orderDetailDto = new OrderDetailDto(
                    orderId,
                    purchaseTM.getItemId(),
                    purchaseTM.getQty()
            );
            orderDetailDtos.add(orderDetailDto);
        }

        OrderDto orderDto = new OrderDto(
                orderId,
                customerId,
                totalPrice,
                netTotal,
                date,
                userId,
                orderDetailDtos
        );

        PromotionDetailDto promotionDetailDto = new PromotionDetailDto(
            discountId,
            orderId,
            discountPrice
        );

        SharedInfo sharedInfo = SharedInfo.getInstance();
        sharedInfo.setOrderId(orderId);
        sharedInfo.setTotal(netTotal);

        newWindowPopUp("/view/ManagePayment.fxml");
        if (paymentInfo.getPaymentDetailsDto() == null){
            return;
        }

        boolean isOrderSaved = purchaseBo.orderSaved(orderDto,promotionDetailDto,paymentInfo.getPaymentDetailsDto());
        if (isOrderSaved){
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION,"Order Placed successfully",ButtonType.OK).show();
            String orderID = sharedInfo.getOrderId();
            reportBill(orderID);
        }else {
            new Alert(Alert.AlertType.ERROR,"Order Not Placed",ButtonType.OK).show();
        }

    }

    @FXML
    void addCustomer(MouseEvent event) throws IOException {
        NavigationPage.newWindowPopUp("/view/ManageCustomer.fxml");
    }

    @FXML
    void searchCustomer(ActionEvent event) throws SQLException {
        String no = txtCustomerID.getText();
        CustomerDto customer = purchaseBo.searchCustomer(no);
        lblCustomer.setText(customer.getName());
        lblCustomerID.setText(customer.getId());
        if (lblCustomer.getText() == null){
            lblAddCustomer.setVisible(true);
        }
    }

    @FXML
    void searchItem(ActionEvent event) throws SQLException {
        String search = txtItemID.getText();
        String itemId ;
        itemId = purchaseBo.searchItem(search);
        lblItem.setText(itemId);
        if (itemId.equals("No Items")) {
            System.out.println("Yup");
            itemId = purchaseBo.searchByItemId(search);
            txtItemID.setText(itemId);
            lblItem.setText(search);
        }
    }

    @FXML
    void cancleOrder(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnItemID.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        columnItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        columnQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        columnQtyPrice.setCellValueFactory(new PropertyValueFactory<>("qtyPrice"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnRemoveButton.setCellValueFactory(new PropertyValueFactory<>("button"));

        focusSwitch.focusSwitchButtonPressTextField(btnSearchCust,txtCustomerID,txtItemID);
        focusSwitch.focusSwitchButtonPressTextField(btnSearchItem,txtItemID,txtQty);
        focusSwitch.focusSwitchButtonPressTextField(btnAdd,txtQty,txtItemID);

        try {
            refreshPage();
        }catch (SQLException e){
            throw (RuntimeException) new RuntimeException("Error in refresh Page").initCause(e);
        }
    }

    private void refreshPage() throws SQLException {
        lblOrderDate.setText(LocalDate.now().toString());
        txtOrderID.setText(purchaseBo.nextOrderId());
        txtTotal.setText("");
        txtDiscount.setText("");
        txtNetTotal.setText("");
        lblDiscountID.setText("");
        lblCustomerID.setText("");
        lblCustomer.setText("");
        lblItem.setText("");
        lblAddCustomer.setVisible(false);
        txtCustomerID.setText("");
        txtCustomerID.setDisable(false);
        tblModel.getItems().clear();
    }

    public double getTotal(){
        double total = 0;
        if (!purchaseTMS.isEmpty()) {
            for (int i = 0; i < tblModel.getItems().size(); i++) {
                double prices = columnPrice.getCellData(i);
                if (prices != 0) {
                    total += prices;
                    txtTotal.setText(String.valueOf(total));
                }
            }
        }
        return total;
    }

    public double getDiscount(double total) throws SQLException {
        double discountPrice = 0;
        double discount = purchaseBo.getRate(total);
        String discountId = purchaseBo.getPromotionId(discount);
        lblDiscountID.setText(discountId);
        discountPrice = total * discount/100;
        txtDiscount.setText(String.valueOf(discountPrice));
        return discountPrice;
    }

    public double getNetTotal(double total,double discountPrice){
        double netTotal = 0;
        netTotal = total - discountPrice;
        txtNetTotal.setText(String.valueOf(netTotal));
        return netTotal;
    }

    public void newWindowPopUp(String path) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource(path));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
//        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/logo.jpg")));
        stage.setTitle("Payment");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        stage.showAndWait();
//        stage.show();
    }

    public void reportBill(String orderId) throws SQLException, JRException {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/report/OrderBill.jrxml")
            );

            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("OrderId", orderId);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
            System.err.println("Error generating the Jasper report.");
        }

//        JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/OrderBill.jrxml"));
//        Connection connection = DBConnection.getInstance().getConnection();
//        Map<String,Object> parameters = new HashMap<>();
////        parameters.put("today", LocalDate.now().toString());
//        parameters.put("OrderId", orderId);
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, connection);
//        JasperViewer.viewReport(jasperPrint,false);
    }

//    public void setTextFieldChange(){
//        txtCustomerID.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                btnSearchCust.fire();
//            }
//        });
//        btnSearchCust.setOnKeyPressed(event -> {
//                txtItemID.requestFocus();
//        });
//        txtItemID.setOnKeyPressed(event ->{
//            if (event.getCode() == KeyCode.ENTER){
//                btnSearchCust.fire();
//            }
//        });
//        btnSearchCust.setOnKeyPressed(event ->{
//                txtQty.requestFocus();
//
//        });
//        txtQty.setOnKeyPressed(event ->{
//            if (event.getCode() == KeyCode.ENTER){
//                btnAdd.fire();
//            }
//        });
//    }
}
