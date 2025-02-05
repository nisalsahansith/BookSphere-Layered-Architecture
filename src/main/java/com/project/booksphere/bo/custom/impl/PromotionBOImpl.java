package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.PromotionBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.PromotionDAO;
import com.project.booksphere.dao.custom.PromotionDetailDAO;
import com.project.booksphere.dto.ItemDetailDto;
import com.project.booksphere.dto.PromotionDto;
import com.project.booksphere.dao.CrudUtil;
import com.project.booksphere.entity.Promotion;

import java.sql.SQLException;
import java.util.ArrayList;

public class PromotionBOImpl implements PromotionBo {
    private final PromotionDAO promotionDAO = (PromotionDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PROMOTION);
    private final PromotionDetailDAO promotionDetailDAO = (PromotionDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PROMOTION_DETAIL);

    @Override
    public boolean savePromotion(PromotionDto promotionDto) throws SQLException {
        Promotion promotion = new Promotion(promotionDto.getPromotionId(),promotionDto.getDesc(),promotionDto.getRate(),promotionDto.getRange());
        return promotionDAO.save(promotion);
    }

    @Override
    public boolean deletePromotion(String id) throws SQLException {
        return promotionDAO.delete(id);
    }

    @Override
    public String nextPromotionId() throws SQLException {
        return promotionDAO.nextId();
    }

    @Override
    public boolean updatePromotion(PromotionDto promotionDto) throws SQLException {
        Promotion promotion = new Promotion(promotionDto.getPromotionId(),promotionDto.getDesc(),promotionDto.getRate(),promotionDto.getRange());
        return promotionDAO.update(promotion);
    }

    @Override
    public ArrayList<PromotionDto> getAllPromotion() throws SQLException {
        ArrayList<Promotion> promotions =  promotionDAO.getAll();
        ArrayList<PromotionDto> promotionDtos = new ArrayList<>();
        for (Promotion promotion : promotions) {
            PromotionDto promotionDto = new PromotionDto(
                    promotion.getPromotionId(),
                    promotion.getDesc(),
                    promotion.getRate(),
                    promotion.getRange()
            );
            promotionDtos.add(promotionDto);
        }
        return promotionDtos;
    }

    @Override
    public boolean deletePromotionDetail(String id) throws SQLException {
        return CrudUtil.execute("delete from promotion_details where PromotionID = ?",id);
    }

    @Override
    public boolean isHavePromotionDetail(String id) throws SQLException {
        return promotionDetailDAO.ishave(id);
    }

}
