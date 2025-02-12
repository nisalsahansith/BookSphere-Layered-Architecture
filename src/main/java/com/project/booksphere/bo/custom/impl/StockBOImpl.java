package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.ViewStockBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.QueryDAO;
import com.project.booksphere.dao.custom.StockDAO;
import com.project.booksphere.dao.custom.StockDetailDAO;
import com.project.booksphere.dao.custom.impl.ItemDetailDAOImpl;
import com.project.booksphere.db.DBConnection;
import com.project.booksphere.dto.CustomDto;
import com.project.booksphere.dto.ItemDetailDto;
import com.project.booksphere.dto.StockDetailDto;
import com.project.booksphere.entity.Custom;
import com.project.booksphere.entity.ItemDetail;
import com.project.booksphere.entity.StockDetail;
import com.project.booksphere.tm.ViewStockTM;
import com.project.booksphere.dao.CrudUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ViewStockBOImpl implements ViewStockBo {
    private final StockDAO stockDAO = (StockDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STOCK);
    private final StockDetailDAO stockDetailDAO = (StockDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STOCK_DETAIL);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY_DAO);
    @Override
    public ArrayList<CustomDto> searchStockID(String id) throws SQLException {
        ArrayList<Custom> customs = queryDAO.searchID(id);
        ArrayList<CustomDto> customDtos = new ArrayList<>();
        for (Custom custom: customs){
            CustomDto customDto = new CustomDto(
                    custom.getStockId(),
                    custom.getStockName(),
                    custom.getItemId(),
                    custom.getQtyOnHand(),
                    custom.getSellPrice(),
                    custom.getUnitPrice(),
                    custom.getSupId(),
                    custom.getSupName(),
                    custom.getUserId()
            );
            customDtos.add(customDto);
        }
        return customDtos;
    }

    @Override
    public boolean updateStock(String id, String name, int qty, double sellPrice, double buyPrice) throws SQLException {
        ItemDetailDAOImpl itemDetailDAO = new ItemDetailDAOImpl();

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean isUpdateStockDetail = stockDetailDAO.update(new StockDetail(id,"",buyPrice,qty));
            if (isUpdateStockDetail){
                boolean isUpdateItemDetail = itemDetailDAO.update(new ItemDetail("",id,sellPrice,qty));
                if (isUpdateItemDetail){
                    boolean isUpdate = CrudUtil.execute("update stock set Name = ? where StockID = ? ",name,id);
                    if (isUpdate){
                        connection.setAutoCommit(true);
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public ArrayList<CustomDto> getAllDetails() throws SQLException {
        ArrayList<Custom> customs = queryDAO.getAlls();
        ArrayList<CustomDto> customDtos = new ArrayList<>();
        for (Custom custom: customs){
            CustomDto customDto = new CustomDto(
                    custom.getStockId(),
                    custom.getStockName(),
                    custom.getItemId(),
                    custom.getQtyOnHand(),
                    custom.getSellPrice(),
                    custom.getUnitPrice(),
                    custom.getSupId(),
                    custom.getSupName(),
                    custom.getUserId()
            );
            customDtos.add(customDto);
        }
        return customDtos;
    }
}
