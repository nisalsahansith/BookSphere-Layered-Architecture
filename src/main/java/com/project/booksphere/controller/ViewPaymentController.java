package com.project.booksphere.controller;

import com.project.booksphere.bo.BOFactory;
import com.project.booksphere.bo.custom.ViewPaymentBo;
import com.project.booksphere.dto.PaymentDto;
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
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewPaymentController implements Initializable {

    @FXML
    private Button btnReports;

    @FXML
    private Button btnReset;

    @FXML
    private TableColumn<PaymentDto, Date> columnDate;

    @FXML
    private TableColumn<PaymentDto, String> columnOrderID;

    @FXML
    private TableColumn<PaymentDto, String> columnPaymentID;

    @FXML
    private TableColumn<PaymentDto, String> columnPaymentMethod;

    @FXML
    private TableColumn<PaymentDto, Double> columnTotal;

    @FXML
    private TableView<PaymentDto> tblPaymentView;

    @FXML
    private TextField txtSearch;

//    PaymentModel paymentModel = new PaymentModel();
//    PaymentDAO paymentDAO = new PaymentDAOImpl();
    private final ViewPaymentBo viewPaymentBo = (ViewPaymentBo) BOFactory.getInstance().getBO(BOFactory.BOType.VIEW_PAYMENT);

    @FXML
    void resetPage(ActionEvent event) throws SQLException {
        txtSearch.setText("");
        refreshTable();
    }

    @FXML
    void searchOrder(MouseEvent event) throws SQLException {
        String id = txtSearch.getText();
        ArrayList<PaymentDto> detailsDto = viewPaymentBo.getAllPaymentID(id);
        ObservableList<PaymentDto> paymentDtos = FXCollections.observableArrayList();
        for (PaymentDto paymentDto : detailsDto){
            PaymentDto paymentDTo = new PaymentDto(
                    paymentDto.getPaymentId(),
                    paymentDto.getOrderId(),
                    paymentDto.getPaymentMethod(),
                    paymentDto.getTotal(),
                    paymentDto.getDate()
            );
            paymentDtos.add(paymentDTo);
        }
        tblPaymentView.setItems(paymentDtos);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnPaymentID.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        columnOrderID.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        columnPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        columnTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        try {
            refreshTable();
            txtSearch.setText("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshTable() throws SQLException {
        ArrayList<PaymentDto> detailsDto = viewPaymentBo.getAllPayments();
        ObservableList<PaymentDto> paymentDtos = FXCollections.observableArrayList();
        for (PaymentDto paymentDto : detailsDto){
            PaymentDto paymentDTo = new PaymentDto(
                    paymentDto.getPaymentId(),
                    paymentDto.getOrderId(),
                    paymentDto.getPaymentMethod(),
                    paymentDto.getTotal(),
                    paymentDto.getDate()
            );
            paymentDtos.add(paymentDTo);
        }
        tblPaymentView.setItems(paymentDtos);
    }


}
