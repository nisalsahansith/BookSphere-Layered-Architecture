package com.project.booksphere.dao.custom;

import com.project.booksphere.dao.CrudDAO;
import com.project.booksphere.dto.PromotionDto;
import com.project.booksphere.entity.Promotion;
import com.project.booksphere.tm.ViewPromotionTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PromotionDAO extends CrudDAO<Promotion> {
//    ArrayList<ViewPromotionTM> searchFromId(String id) throws SQLException;
    double getRate(double total) throws SQLException;
    String getId(double discount) throws SQLException;
    boolean isValidPromotionId(String id) throws SQLException;
}
