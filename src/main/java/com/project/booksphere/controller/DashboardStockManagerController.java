package com.project.booksphere.controller;

import com.project.booksphere.model.EmployeeModel;
import com.project.booksphere.util.CrudUtil;
import com.project.booksphere.util.SharedInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
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

public class DashboardStockManagerController implements Initializable {

    @FXML
    private BarChart<String, Number> barChartItemSell;

    @FXML
    private Button btnSendEmail;

    @FXML
    private Button btnViewItem;

    @FXML
    private Button btnViewPromotion;

    @FXML
    private Button btnViewStock;

    @FXML
    private Label lblUserName;

    private final EmployeeModel employeeModel = new EmployeeModel();
    private final SharedInfo sharedInfo = SharedInfo.getInstance();


    @FXML
    void sendEmailPage(ActionEvent event) throws IOException {
        newWindowPopUp("/view/SendMail.fxml");
    }

    @FXML
    void viewItemPage(ActionEvent event) throws IOException {
        newWindowPopUp("/view/ViewItems.fxml");
    }

    @FXML
    void viewPromotionPage(ActionEvent event) throws IOException {
        newWindowPopUp("/view/ViewPromotion.fxml");
    }

    @FXML
    void viewStockPage(ActionEvent event) throws IOException {
        newWindowPopUp("/view/ViewStock.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String empID = sharedInfo.getEmployeeId();
        try {
            setName(empID);
            setDataToBarChart();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setName(String id) throws SQLException {
        String getName = employeeModel.getName(id);
        lblUserName.setText(getName);
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

    public void setDataToBarChart(){
        CategoryAxis xAxis = (CategoryAxis) barChartItemSell.getXAxis();
        xAxis.setLabel("Item Name");
        NumberAxis yAxis = (NumberAxis) barChartItemSell.getYAxis();
        yAxis.setLabel("Quantity");

        barChartItemSell.setTitle("Most Selling Items");

        try {
            String sql = "SELECT i.Description, SUM(od.OrderQty) AS total_quantity FROM " +
                    "item i join orderdetails od on i.ItemID = od.ItemID " +
                    "GROUP BY i.Description ORDER BY total_quantity DESC LIMIT 20";
            ResultSet resultSet = CrudUtil.execute(sql);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Current Stock");

            while (resultSet.next()) {
                String name = resultSet.getString("Description");
                int quantity = resultSet.getInt("total_quantity");

                XYChart.Data<String, Number> data = new XYChart.Data<>(name, quantity);
                series.getData().add(data);

                data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        newValue.setStyle("-fx-bar-fill: #706fd3;");
                    }
                });
            }

            // Add the series to the chart
            barChartItemSell.getData().add(series);
            barChartItemSell.setLegendVisible(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
