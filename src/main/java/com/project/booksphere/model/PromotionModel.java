package com.project.booksphere.model;

import com.project.booksphere.dto.PromotionDetailDto;
import com.project.booksphere.dto.PromotionDto;
import com.project.booksphere.dto.tm.PromotionTM;
import com.project.booksphere.dto.tm.ViewItemTM;
import com.project.booksphere.dto.tm.ViewPromotionTM;
import com.project.booksphere.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PromotionModel {
    public double getRate(double total) throws SQLException {
        ResultSet rst = CrudUtil.execute("select DiscountRate from promotion where ranges = ? OR ranges < ?",total,total);
        double rate = 0;
        if (rst.next()){
            rate = Double.parseDouble(rst.getString(1));
        }
        return rate;
    }

    public String getId(double discount) throws SQLException {
        ResultSet rst = CrudUtil.execute("select PromotionID from promotion where DiscountRate = ?",discount);
        String id = "";
        if (rst.next()){
            id = rst.getString(1);
        }
        return id;
    }

    public boolean isValidPromotionId(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT COUNT(*) FROM promotion WHERE PromotionID = ?",id);
        if (rst.next()){
            int count = rst.getInt(1);
            return count > 0;
        }
        return false;
    }

    public ArrayList<PromotionDto> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from promotion");
        ArrayList<PromotionDto> promotionDtos = new ArrayList<>();
        while (rst.next()){
            PromotionDto promotionDto = new PromotionDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4)
            );
            promotionDtos.add(promotionDto);
        }
        return promotionDtos;
    }

    public String getNextId() throws SQLException {
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

    public boolean savePromotion(PromotionDto promotionDto) throws SQLException {
        return CrudUtil.execute("insert into promotion values(?,?,?,?)",
                    promotionDto.getPromotionId(),
                    promotionDto.getDesc(),
                    promotionDto.getRate(),
                    promotionDto.getRange()
                );
    }

    public boolean updatePromotion(PromotionDto promotionDto) throws SQLException {
        return CrudUtil.execute("update promotion set Description =?, DiscountRate =?, ranges =? where PromotionID = ?",
                    promotionDto.getDesc(),promotionDto.getRate(),promotionDto.getRange(),promotionDto.getPromotionId()
                );
    }

    public boolean deletePromotion(String id) throws SQLException {
        return CrudUtil.execute("delete from promotion where PromotionID = ?",id);
    }

    public ArrayList<ViewPromotionTM> searchFromId(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from promotion where PromotionID = ?",id);
        ArrayList<ViewPromotionTM> viewPromotionTMS = new ArrayList<>();
        while (rst.next()){
            ViewPromotionTM viewPromotionTM = new ViewPromotionTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4)
            );
            viewPromotionTMS.add(viewPromotionTM);
        }
        return viewPromotionTMS;
    }

}
