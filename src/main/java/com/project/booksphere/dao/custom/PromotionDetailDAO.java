package com.project.booksphere.dao.custom;

import com.project.booksphere.dao.CrudDAO;
import com.project.booksphere.dto.PromotionDetailDto;
import com.project.booksphere.entity.Promotion;
import com.project.booksphere.entity.PromotionDetail;

import java.sql.SQLException;

public interface PromotionDetailDAO extends CrudDAO<PromotionDetail> {
    boolean ishave(String id) throws SQLException;
    boolean deleteOrderPromotion(String id) throws SQLException;
}
