package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;
import com.project.booksphere.dao.custom.impl.StockDAOImpl;
import com.project.booksphere.db.DBConnection;
import com.project.booksphere.dto.ItemDetailDto;
import com.project.booksphere.dto.StockDetailDto;
import com.project.booksphere.dto.StockDto;
import com.project.booksphere.dto.SupplierDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDetailBo extends SuperBo {

    boolean saveItemDetails(ItemDetailDto itemDetailDto, StockDto stockDto, StockDetailDto stockDetailDto) throws SQLException;
    ArrayList<ItemDetailDto> getAllItems() throws SQLException;
    ArrayList<SupplierDto> searchSupplier(String id) throws SQLException;
    String nextItemId() throws SQLException;
}
