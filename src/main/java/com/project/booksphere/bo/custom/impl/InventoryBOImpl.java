package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.InventoryBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.*;
import com.project.booksphere.db.DBConnection;
import com.project.booksphere.dto.CustomDto;
import com.project.booksphere.dto.ItemDto;
import com.project.booksphere.entity.Custom;
import com.project.booksphere.entity.Item;
import com.project.booksphere.dao.CrudUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryBOImpl implements InventoryBo {
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ITEM);
    private final ItemDetailDAO itemDetailDAO = (ItemDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ITEM_DETAIL);
    private final StockDetailDAO stockDetailDAO = (StockDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STOCK_DETAIL);
    private final StockDAO stockDAO = (StockDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STOCK);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY_DAO);

    @Override
    public boolean saveItem(ItemDto itemDto) throws SQLException {
        Item item = new Item(itemDto.getId(),itemDto.getDescription(),itemDto.getISBN(),itemDto.getQty());
        return itemDAO.save(item);
    }

    @Override
    public boolean deleteItem(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            String stockId = itemDetailDAO.getStock(id);
            boolean isStockDetailDelete = stockDetailDAO.delete(stockId);
            boolean isOrderDetailDelete = itemDetailDAO.delete(id);
            if (isStockDetailDelete | isOrderDetailDelete ){
                boolean isStockDelete = stockDAO.delete(stockId);
                boolean isItemDelete = CrudUtil.execute("delete from item where ItemID = ?",id);
                if (isStockDelete | isItemDelete){
                    connection.commit();
                    return true;
                }
            }else {
                CrudUtil.execute("delete from item where ItemID = ?",id);
                connection.commit();
                return true;
            }
            connection.rollback();
            return false;
        }catch (SQLException e){
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }

    public boolean updateItem(ItemDto itemDto) throws SQLException {
        Item item = new Item(itemDto.getId(),itemDto.getDescription(),itemDto.getISBN(),itemDto.getQty());
        return itemDAO.update(item);
    }

    @Override
    public ArrayList<CustomDto> searchingItem(String search) throws SQLException {
        ArrayList<Custom> customs = queryDAO.searching(search);
        ArrayList<CustomDto> customDtos = new ArrayList<>();
        for (Custom custom: customs){
            CustomDto customDto = new CustomDto(
                    custom.getItemId(),
                    custom.getItemDescription(),
                    custom.getISBN(),
                    custom.getQtyOnHand()
            );
            customDtos.add(customDto);
        }
        return customDtos;
    }

    @Override
    public ArrayList<CustomDto> searchByItemName(String search) throws SQLException {
        ArrayList<Custom> customs = queryDAO.searchByName(search);
        ArrayList<CustomDto> customDtos = new ArrayList<>();
        for (Custom custom: customs){
            CustomDto customDto = new CustomDto(
                    custom.getItemId(),
                    custom.getItemDescription(),
                    custom.getISBN(),
                    custom.getQtyOnHand()
            );
            customDtos.add(customDto);
        }
        return customDtos;
    }

    @Override
    public String nextItemId() throws SQLException {
        return itemDAO.nextId();
    }

    @Override
    public ArrayList<CustomDto> getAllItems() throws SQLException {
        ArrayList<Custom> customs = queryDAO.getAllItem();
        ArrayList<CustomDto> customDtos = new ArrayList<>();
        for (Custom custom : customs) {
            CustomDto customDto = new CustomDto(
                    custom.getItemId(),
                    custom.getItemDescription(),
                    custom.getISBN(),
                    custom.getQtyOnHand()
            );
            customDtos.add(customDto);
        }
        return customDtos;
    }
}
