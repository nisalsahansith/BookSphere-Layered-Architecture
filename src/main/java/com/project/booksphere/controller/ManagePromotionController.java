package com.project.booksphere.controller;

import com.project.booksphere.dto.PromotionDto;
import com.project.booksphere.dto.SupplierDto;
import com.project.booksphere.dto.tm.PromotionTM;
import com.project.booksphere.model.PromotionDetailModel;
import com.project.booksphere.model.PromotionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagePromotionController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<PromotionTM, String> columnDescription;

    @FXML
    private TableColumn<PromotionTM, Double> columnDiscountRate;

    @FXML
    private TableColumn<PromotionTM, String> columnPromotionId;

    @FXML
    private TableColumn<PromotionTM, Double> columnRange;

    @FXML
    private Label lblPromotionId;

    @FXML
    private TableView<PromotionTM> tblPromotion;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtDiscountRate;

    @FXML
    private TextField txtRange;

    PromotionModel promotionModel = new PromotionModel();
    PromotionDetailModel promotionDetailModel = new PromotionDetailModel();

    @FXML
    void addData(ActionEvent event) throws SQLException {
        String supId = lblPromotionId.getText();
        String description = txtDescription.getText();
        double rate = Double.parseDouble(txtDiscountRate.getText());
        double range = Double.parseDouble(txtRange.getText());
        PromotionDto promotionDto = new PromotionDto(supId,description,rate,range);
        boolean isSaved = promotionModel.savePromotion(promotionDto);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION,"Promotion saved successfully",ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Promotion not saved",ButtonType.OK).show();
        }
    }

    @FXML
    void clickOnTable(MouseEvent event)  {
        PromotionTM selected = tblPromotion.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblPromotionId.setText(selected.getPromotionId());
            txtDescription.setText(selected.getDescription());
            txtDiscountRate.setText(String.valueOf(selected.getDiscountRate()));
            txtRange.setText(String.valueOf(selected.getRange()));
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnAdd.setDisable(true);
        }
    }

    @FXML
    void deleteData(ActionEvent event) throws SQLException {
       String id = lblPromotionId.getText();
       Alert isOk = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this Promotion ?",ButtonType.YES);
        Optional<ButtonType> result = isOk.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean isDeletePromotionDetails = promotionDetailModel.deletePromotion(id);
            boolean isHave = promotionDetailModel.ishave(id);
            if (isDeletePromotionDetails | isHave) {
                boolean isDelete = promotionModel.deletePromotion(id);
                if (isDelete) {
                    new Alert(Alert.AlertType.INFORMATION, "Delete Successfully", ButtonType.OK).show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Delete Unsuccessfully");
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Something went wrong!");
            }
        }
    }

    @FXML
    void resetPage(ActionEvent event) throws SQLException {
        lblPromotionId.setText(promotionModel.getNextId());
        txtDescription.setText("");
        txtDiscountRate.setText("");
        txtRange.setText("");
        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    @FXML
    void updateData(ActionEvent event) throws SQLException {
        String supId = lblPromotionId.getText();
        String description = txtDescription.getText();
        double rate = Double.parseDouble(txtDiscountRate.getText());
        double range = Double.parseDouble(txtRange.getText());
        PromotionDto promotionDto = new PromotionDto(supId,description,rate,range);
        boolean isSaved = promotionModel.updatePromotion(promotionDto);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION,"Promotion updated successfully",ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Promotion not updated",ButtonType.OK).show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnPromotionId.setCellValueFactory(new PropertyValueFactory<>("promotionId"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnDiscountRate.setCellValueFactory(new PropertyValueFactory<>("discountRate"));
        columnRange.setCellValueFactory(new PropertyValueFactory<>("range"));
        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void refreshPage() throws SQLException {
        refreshTable();
        lblPromotionId.setText(promotionModel.getNextId());
        txtDescription.setText("");
        txtDiscountRate.setText("");
        txtRange.setText("");
        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<PromotionDto> promotionDtos = promotionModel.getAll();
        ObservableList<PromotionTM> promotionTMS = FXCollections.observableArrayList();
        for (PromotionDto promotionDto: promotionDtos){
            PromotionTM promotionTM = new PromotionTM(
                    promotionDto.getPromotionId(),
                    promotionDto.getDesc(),
                    promotionDto.getRate(),
                    promotionDto.getRange()
            );
            promotionTMS.add(promotionTM);
        }
        tblPromotion.setItems(promotionTMS);
    }
}
