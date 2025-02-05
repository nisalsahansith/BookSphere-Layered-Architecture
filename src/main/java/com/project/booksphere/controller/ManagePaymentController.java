package com.project.booksphere.controller;

import com.project.booksphere.bo.BOFactory;
import com.project.booksphere.bo.custom.PaymentBo;
import com.project.booksphere.dto.PaymentDto;
import com.project.booksphere.util.PaymentInfo;
import com.project.booksphere.util.SharedInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ManagePaymentController implements Initializable {

    @FXML
    private Button btnPaid;

    @FXML
    private Label lblDate;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtPayementID;

    @FXML
    private ComboBox<String> cmbMethod;

    @FXML
    private TextField txtTotal;

//    PaymentModel paymentModel = new PaymentModel();
    SharedInfo sharedInfo = SharedInfo.getInstance();
    PaymentInfo paymentInfo = PaymentInfo.getInstance();

//    PaymentDAO paymentDAO = new PaymentDAOImpl();
    private final PaymentBo paymentBo = (PaymentBo) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);

    @FXML
    void paymentDone(ActionEvent event) throws JRException, SQLException {
        String paymentID = txtPayementID.getText();
        String orderID = txtOrderId.getText();
        double total = Double.parseDouble(txtTotal.getText());
        String paymentMethod = cmbMethod.getSelectionModel().getSelectedItem();
        Date date = Date.valueOf(lblDate.getText());

        if (!cmbMethod.getSelectionModel().isEmpty()) {
            cmbMethod.setStyle(cmbMethod.getStyle() + "-fx-border-color:  #00a8ff; " );
            PaymentDto paymentDto = new PaymentDto(
                    paymentID, orderID, paymentMethod, total, date
            );

            paymentInfo.setPaymentDetailsDto(paymentDto);

            Stage stage = (Stage) btnPaid.getScene().getWindow();
            stage.close();
        }else{
            cmbMethod.setStyle(cmbMethod.getStyle()+ "-fx-border-color:  red; ");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lblDate.setText(LocalDate.now().toString());
            txtPayementID.setText(paymentBo.nextPaymentId());
            txtOrderId.setText(sharedInfo.getOrderId());
            System.out.println(txtOrderId.getText());
            txtTotal.setText(String.valueOf(sharedInfo.getTotal()));
            System.out.println(txtTotal.getText());
            cmbMethod.setPromptText("Select an option");
            cmbMethod.getItems().addAll("CARD","CASH");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
