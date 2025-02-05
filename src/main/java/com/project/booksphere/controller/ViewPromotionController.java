package com.project.booksphere.controller;

import com.project.booksphere.bo.BOFactory;
import com.project.booksphere.bo.custom.ViewPromotionBo;
import com.project.booksphere.dto.PromotionDto;
import com.project.booksphere.tm.ViewPromotionTM;
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

//    private final PromotionModel promotionModel = new PromotionModel();
//    private final PromotionDAO promotionDAO = new PromotionDAOImpl();
    private final ViewPromotionBo viewPromotionBo = (ViewPromotionBo) BOFactory.getInstance().getBO(BOFactory.BOType.VIEW_PROMOTION);

    @FXML
    void resetPage(ActionEvent event) throws SQLException {
        txtSearch.setText("");
        refreshPage();
    }

    @FXML
    void searchOrder(MouseEvent event) throws SQLException {
        String id = txtSearch.getText();
        ArrayList<PromotionDto> promotionDtos = viewPromotionBo.searchPromotionFromId(id);
        ObservableList<ViewPromotionTM> promotionDtos1 = FXCollections.observableArrayList();
        for (PromotionDto promotionDto : promotionDtos) {
            ViewPromotionTM viewPromotionTm = new ViewPromotionTM(
                    promotionDto.getPromotionId(),
                    promotionDto.getDesc(),
                    promotionDto.getRate(),
                    promotionDto.getRange()
            );
            promotionDtos1.add(viewPromotionTm);
        }
        tblPromotionView.setItems(promotionDtos1);
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
        ArrayList<PromotionDto> promotionTMS = viewPromotionBo.getAllPromotions();
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
