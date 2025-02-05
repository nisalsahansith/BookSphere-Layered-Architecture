package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;
import com.project.booksphere.dto.PromotionDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PromotionBo extends SuperBo {

    public boolean savePromotion(PromotionDto promotionDto) throws SQLException;
    public boolean deletePromotion(String id) throws SQLException;
    public String nextPromotionId() throws SQLException;
    public boolean updatePromotion(PromotionDto promotionDto) throws SQLException;
    public ArrayList<PromotionDto> getAllPromotion() throws SQLException;
    public boolean deletePromotionDetail(String id) throws SQLException;
    public boolean isHavePromotionDetail(String id) throws SQLException;
}
