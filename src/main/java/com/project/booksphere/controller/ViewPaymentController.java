package com.project.booksphere.controller;

import com.project.booksphere.dto.PaymentDetailsDto;
import com.project.booksphere.model.PaymentModel;
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
    private TableColumn<PaymentDetailsDto, Date> columnDate;

    @FXML
    private TableColumn<PaymentDetailsDto, String> columnOrderID;

    @FXML
    private TableColumn<PaymentDetailsDto, String> columnPaymentID;

    @FXML
    private TableColumn<PaymentDetailsDto, String> columnPaymentMethod;

    @FXML
    private TableColumn<PaymentDetailsDto, Double> columnTotal;

    @FXML
    private TableView<PaymentDetailsDto> tblPaymentView;

    @FXML
    private TextField txtSearch;

    PaymentModel paymentModel = new PaymentModel();

    @FXML
    void resetPage(ActionEvent event) throws SQLException {
        txtSearch.setText("");
        refreshTable();
    }

    @FXML
    void searchOrder(MouseEvent event) throws SQLException {
        String id = txtSearch.getText();
        ArrayList<PaymentDetailsDto> detailsDto = paymentModel.getAllID(id);
        ObservableList<PaymentDetailsDto> paymentDetailsDtos = FXCollections.observableArrayList();
        for (PaymentDetailsDto paymentDetailsDto: detailsDto){
            PaymentDetailsDto paymentDetailsDTo = new PaymentDetailsDto(
                    paymentDetailsDto.getPaymentId(),
                    paymentDetailsDto.getOrderId(),
                    paymentDetailsDto.getPaymentMethod(),
                    paymentDetailsDto.getTotal(),
                    paymentDetailsDto.getDate()
            );
            paymentDetailsDtos.add(paymentDetailsDTo);
        }
        tblPaymentView.setItems(paymentDetailsDtos);
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
        ArrayList<PaymentDetailsDto> detailsDto = paymentModel.getAll();
        ObservableList<PaymentDetailsDto> paymentDetailsDtos = FXCollections.observableArrayList();
        for (PaymentDetailsDto paymentDetailsDto: detailsDto){
            PaymentDetailsDto paymentDetailsDTo = new PaymentDetailsDto(
                    paymentDetailsDto.getPaymentId(),
                    paymentDetailsDto.getOrderId(),
                    paymentDetailsDto.getPaymentMethod(),
                    paymentDetailsDto.getTotal(),
                    paymentDetailsDto.getDate()
            );
            paymentDetailsDtos.add(paymentDetailsDTo);
        }
        tblPaymentView.setItems(paymentDetailsDtos);
    }


}
