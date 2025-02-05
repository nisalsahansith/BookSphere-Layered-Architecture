package com.project.booksphere.dao.custom.impl;

import com.project.booksphere.dao.custom.PromotionDetailDAO;
import com.project.booksphere.dto.PromotionDetailDto;
import com.project.booksphere.dao.CrudUtil;
import com.project.booksphere.entity.PromotionDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PromotionDetailDAOImpl implements PromotionDetailDAO {
    private final PromotionDAOImpl promotionDAO = new PromotionDAOImpl();

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from promotion_details where PromotionID = ?",id);
    }

    @Override
    public boolean ishave(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select PromotionId from promotion where PromotionID= ?",id);
        if (rst.next()){
            rst.getString(1);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteOrderPromotion(String id) throws SQLException {
        return CrudUtil.execute("delete from promotion_details where OrderID = ?",id);
    }

    @Override
    public ArrayList<PromotionDetail> getAll() throws SQLException {
        return null;
    }

    @Override
    public String nextId() throws SQLException {
        return "";
    }

    @Override
    public ArrayList<PromotionDetail> search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean save(PromotionDetail promotionDetail) throws SQLException {
        if (promotionDAO.isValidPromotionId(promotionDetail.getPromotionId())) {
            return CrudUtil.execute("insert into promotion_details values(?,?,?)",
                    promotionDetail.getPromotionId(),
                    promotionDetail.getOrderId(),
                    promotionDetail.getDiscountPrice()
            );
        }
        return true;
    }

    @Override
    public boolean update(PromotionDetail promotionDetail) throws SQLException {
        return false;
    }

}
