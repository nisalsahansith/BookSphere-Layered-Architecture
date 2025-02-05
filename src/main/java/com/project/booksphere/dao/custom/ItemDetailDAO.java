package com.project.booksphere.dao.custom;

import com.project.booksphere.dao.CrudDAO;
import com.project.booksphere.dto.ItemDetailDto;
import com.project.booksphere.entity.ItemDetail;

import java.sql.SQLException;

public interface ItemDetailDAO extends CrudDAO<ItemDetail> {
//    boolean saveItemDetails(ItemDetailDto itemDetailDto, StockDto stockDto, StockDetailDto stockDetailDto) throws SQLException;
    int getQty(ItemDetail itemDetail) throws SQLException;
    String getStock(String id) throws SQLException;
    boolean increaseQTY(String id, double qty) throws SQLException;
//    boolean update(String id, int qty, double sellPrice) throws SQLException;
//    int getQtySum(String id) throws SQLException;
}
