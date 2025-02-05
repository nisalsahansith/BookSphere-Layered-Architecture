package com.project.booksphere.dao.custom.impl;

import com.project.booksphere.dao.custom.PromotionDAO;
import com.project.booksphere.entity.Promotion;
import com.project.booksphere.tm.ViewPromotionTM;
import com.project.booksphere.dao.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PromotionDAOImpl implements PromotionDAO {

    @Override
    public ArrayList<Promotion> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from promotion");
        ArrayList<Promotion> promotions = new ArrayList<>();
        while (rst.next()){
            Promotion promotion = new Promotion(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4)
            );
            promotions.add(promotion);
        }
        return promotions;
    }

    @Override
    public boolean save(Promotion promotion) throws SQLException {
        return CrudUtil.execute("insert into promotion values(?,?,?,?)",
                promotion.getPromotionId(),
                promotion.getDesc(),
                promotion.getRate(),
                promotion.getRange()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from promotion where PromotionID = ?",id);
    }

    @Override
    public String nextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select PromotionID from promotion order by PromotionID desc limit 1 ");
        if (rst.next()){
            String string = rst.getString(1);
            String subString = string.substring(5);
            int id = Integer.parseInt(subString);
            int newId = id + 1;
            String nextId = String.format("PROMO%03d",newId);
            return nextId;
        }
        return "PROMO001";
    }


    @Override
    public boolean update(Promotion promotion) throws SQLException {
        return CrudUtil.execute("update promotion set Description =?, DiscountRate =?, ranges =? where PromotionID = ?",
                promotion.getDesc(),promotion.getRate(),promotion.getRange(),promotion.getPromotionId()
        );
    }

    @Override
    public ArrayList<Promotion> search(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from promotion where PromotionID = ?",id);
        ArrayList<Promotion> promotions = new ArrayList<>();
        while (rst.next()){
            Promotion promotion = new Promotion(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4)
            );
            promotions.add(promotion);
        }
        return promotions;
    }

    @Override
    public double getRate(double total) throws SQLException {
        ResultSet rst = CrudUtil.execute("select DiscountRate from promotion where ranges = ? OR ranges < ?",total,total);
        double rate = 0;
        if (rst.next()){
            rate = Double.parseDouble(rst.getString(1));
        }
        return rate;
    }

    @Override
    public String getId(double discount) throws SQLException {
        ResultSet rst = CrudUtil.execute("select PromotionID from promotion where DiscountRate = ?",discount);
        String id = "";
        if (rst.next()){
            id = rst.getString(1);
        }
        return id;
    }

    @Override
    public boolean isValidPromotionId(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT COUNT(*) FROM promotion WHERE PromotionID = ?",id);
        if (rst.next()){
            int count = rst.getInt(1);
            return count > 0;
        }
        return false;
    }

}
