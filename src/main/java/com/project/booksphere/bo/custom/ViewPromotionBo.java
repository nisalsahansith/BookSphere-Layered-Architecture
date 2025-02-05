package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;
import com.project.booksphere.dto.PromotionDto;
import com.project.booksphere.tm.ViewPromotionTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ViewPromotionBo extends SuperBo {
    ArrayList<PromotionDto> searchPromotionFromId(String id) throws SQLException;
    ArrayList<PromotionDto> getAllPromotions() throws SQLException;
}
