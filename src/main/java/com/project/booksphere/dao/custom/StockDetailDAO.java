package com.project.booksphere.dao.custom;

import com.project.booksphere.dao.CrudDAO;
import com.project.booksphere.dto.StockDetailDto;
import com.project.booksphere.entity.StockDetail;

import java.sql.SQLException;

public interface StockDetailDAO extends CrudDAO<StockDetail> {
    boolean deleteDetailSup(String id) throws SQLException;
}
