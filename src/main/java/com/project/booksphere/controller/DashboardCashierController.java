package com.project.booksphere.controller;

import com.project.booksphere.bo.BOFactory;
import com.project.booksphere.bo.custom.CashierDashboardBo;
import com.project.booksphere.bo.custom.impl.CashierDashboardBOImpl;
import com.project.booksphere.dao.CrudUtil;
import com.project.booksphere.util.SharedInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardCashierController implements Initializable {

    @FXML
    private Button btnSendEmail;

    @FXML
    private Button btnViewItem;

    @FXML
    private Button btnViewOrder;

    @FXML
    private Button btnViewPayment;

    @FXML
    private Button btnViewPromotion;

    @FXML
    private Button btnViewStock;

    @FXML
    private Label lblUserName;

    @FXML
    private LineChart<String, Number> lineChartOrders;

//    private final EmployeeModel employeeModel = new EmployeeModel();
    private final SharedInfo sharedInfo = SharedInfo.getInstance();

//    private final EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    private final CashierDashboardBo cashierDashboardBo = (CashierDashboardBo) BOFactory.getInstance().getBO(BOFactory.BOType.CASHIER_DASHBOARD);

    @FXML
    void sendEmailPage(ActionEvent event) throws IOException {
        newWindowPopUp("/view/SendMail.fxml");
    }

    @FXML
    void viewItemPage(ActionEvent event) throws IOException {
        newWindowPopUp("/view/ViewItems.fxml");
    }

    @FXML
    void viewOrderPage(ActionEvent event) throws IOException {
        newWindowPopUp("/view/ViewOrder.fxml");
    }

    @FXML
    void viewPaymentPage(ActionEvent event) throws IOException {
        newWindowPopUp("/view/ViewPayment.fxml");
    }

    @FXML
    void viewPromotionPage(ActionEvent event) throws IOException {
        newWindowPopUp("/view/ViewPromotion.fxml");
    }

    @FXML
    void viewStockPage(ActionEvent event) throws IOException {
        newWindowPopUp("/view/ViewStock.fxml");
    }

    public void newWindowPopUp(String path) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource(path));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/logo.jpg")));
        stage.setTitle("Book Sphere");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        stage.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String empID = sharedInfo.getEmployeeId();
        try {
            setOrderChart();
            setName(empID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setName(String id) throws SQLException {
        String getName = cashierDashboardBo.getNameCashier(id);
        lblUserName.setText(getName);
    }

    public void setOrderChart() {
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Order Count");

        // Use CategoryAxis for the X-axis (since dates are treated as categories here)
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");

        // Set title of the chart
        lineChartOrders.setTitle("Selling Performance");
        String id = sharedInfo.getUserID();

        try {
            // SQL Query to get the total orders for the last 7 days
            String sql ="SELECT OrderDate, COUNT(OrderID) AS total_orders " +
                    "FROM orders " +
                    "WHERE OrderDate >= CURDATE() - INTERVAL 20 DAY " +
                    "GROUP BY OrderDate " +
                    "ORDER BY OrderDate DESC";

            // Execute the query
            ResultSet resultSet = CrudUtil.execute(sql);

            // Create the series for the LineChart
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Current Stock");

            // Process each row in the result set
            while (resultSet.next()) {
                int count = resultSet.getInt("total_orders");
                String date = resultSet.getString("OrderDate");

                // Add the data to the series
                series.getData().add(new XYChart.Data<>(date, count));
            }

            // Add the series to the chart
            lineChartOrders.getData().add(series);
            lineChartOrders.setLegendVisible(false);

            // Customize the line color
            series.getNode().setStyle("-fx-stroke: blue; -fx-stroke-width: 2;");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
