package com.project.booksphere.controller;

import com.project.booksphere.dto.PromotionDto;
import com.project.booksphere.dto.tm.ViewOrderTM;
import com.project.booksphere.dto.tm.ViewPromotionTM;
import com.project.booksphere.model.PromotionModel;
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

public class ViewPromotionController implements Initializable {

    @FXML
    private Button btnReset;

    @FXML
    private TableColumn<ViewPromotionTM, Double> columRange;


    @FXML
    private TableColumn<ViewPromotionTM, Double> columnRate;

    @FXML
    private TableColumn<ViewPromotionTM, String> columDesc;

    @FXML
    private TableColumn<ViewPromotionTM, String> columnPromotionID;

    @FXML
    private TableView<ViewPromotionTM> tblPromotionView;

    @FXML
    private TextField txtSearch;

    private final PromotionModel promotionModel = new PromotionModel();

    @FXML
    void resetPage(ActionEvent event) throws SQLException {
        txtSearch.setText("");
        refreshPage();
    }

    @FXML
    void searchOrder(MouseEvent event) throws SQLException {
        String id = txtSearch.getText();
        ArrayList<ViewPromotionTM> promotionTMS = promotionModel.searchFromId(id);
        ObservableList<ViewPromotionTM> ViewPromotionTMS = FXCollections.observableArrayList();
        for (ViewPromotionTM viewPromotionTM : promotionTMS) {
            ViewPromotionTM viewPromotionTm = new ViewPromotionTM(
                    viewPromotionTM.getPromotionId(),
                    viewPromotionTM.getDesc(),
                    viewPromotionTM.getRate(),
                    viewPromotionTM.getRange()
            );
            ViewPromotionTMS.add(viewPromotionTm);
        }
        tblPromotionView.setItems(ViewPromotionTMS);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnPromotionID.setCellValueFactory(new PropertyValueFactory<>("promotionId"));
        columDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        columnRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        columRange.setCellValueFactory(new PropertyValueFactory<>("range"));
        try {
            txtSearch.setText("");
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshPage() throws SQLException {
        ArrayList<PromotionDto> promotionTMS = promotionModel.getAll();
        ObservableList<ViewPromotionTM> ViewPromotionTMS = FXCollections.observableArrayList();
        for (PromotionDto viewPromotionTM : promotionTMS) {
            ViewPromotionTM viewPromotionTm = new ViewPromotionTM(
                    viewPromotionTM.getPromotionId(),
                    viewPromotionTM.getDesc(),
                    viewPromotionTM.getRate(),
                    viewPromotionTM.getRange()
            );
            ViewPromotionTMS.add(viewPromotionTm);
        }
        tblPromotionView.setItems(ViewPromotionTMS);
    }
}
