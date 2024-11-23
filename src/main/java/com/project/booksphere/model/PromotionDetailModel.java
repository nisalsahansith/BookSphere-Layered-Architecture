package com.project.booksphere.model;

import com.project.booksphere.dto.PromotionDetailDto;
import com.project.booksphere.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PromotionDetailModel {
    private final PromotionModel promotionModel = new PromotionModel();
    public boolean savePromotion(PromotionDetailDto promotionDetailDto) throws SQLException {
        if (promotionModel.isValidPromotionId(promotionDetailDto.getPromotionId())) {
            return CrudUtil.execute("insert into promotion_details values(?,?,?)",
                    promotionDetailDto.getPromotionId(),
                    promotionDetailDto.getOrderId(),
                    promotionDetailDto.getDiscountPrice()
            );
        }
        return true;
    }

    public boolean deletePromotion(String id) throws SQLException {
        return CrudUtil.execute("delete from promotion_details where PromotionID = ?",id);
    }

    public boolean ishave(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select PromotionId from promotion where PromotionID= ?",id);
        if (rst.next()){
            rst.getString(1);
            return true;
        }
        return false;
    }

    public boolean deleteOrderPromotion(String id) throws SQLException {
        return CrudUtil.execute("delete from promotion_details where OrderID = ?",id);
    }
}
