package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.ViewPromotionBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.PromotionDAO;
import com.project.booksphere.dto.PromotionDto;
import com.project.booksphere.entity.Promotion;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewPromotionBOImpl implements ViewPromotionBo {
    private final PromotionDAO promotionDAO = (PromotionDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PROMOTION);

    @Override
    public ArrayList<PromotionDto> searchPromotionFromId(String id) throws SQLException {
        ArrayList<Promotion> promotions = promotionDAO.search(id);
        ArrayList<PromotionDto> promotionDtos = new ArrayList<>();
        for (Promotion promotion : promotions){
            PromotionDto promotionDto = new PromotionDto(
                promotion.getPromotionId(),promotion.getDesc(),promotion.getRate(),promotion.getRange()
            );
            promotionDtos.add(promotionDto);
        }
        return promotionDtos;
    }

    @Override
    public ArrayList<PromotionDto> getAllPromotions() throws SQLException {
        ArrayList<Promotion> promotions = promotionDAO.getAll();
        ArrayList<PromotionDto> promotionDtos = new ArrayList<>();
        for (Promotion promotion : promotions){
            PromotionDto promotionDto = new PromotionDto(
                    promotion.getPromotionId(),promotion.getDesc(),promotion.getRate(),promotion.getRange()
            );
            promotionDtos.add(promotionDto);
        }
        return promotionDtos;
    }
}
