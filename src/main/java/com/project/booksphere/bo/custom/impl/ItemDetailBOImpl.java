package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.ItemDetailBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.ItemDetailDAO;
import com.project.booksphere.dao.custom.StockDAO;
import com.project.booksphere.dao.custom.StockDetailDAO;
import com.project.booksphere.dao.custom.SupplierDAO;
import com.project.booksphere.dao.custom.impl.StockDAOImpl;
import com.project.booksphere.db.DBConnection;
import com.project.booksphere.dto.*;
import com.project.booksphere.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDetailBOImpl implements ItemDetailBo {
    private ItemDetailDAO itemDetailDAO = (ItemDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ITEM_DETAIL);
    private SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER);
    private StockDAO stockDAO = (StockDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STOCK);
    private StockDetailDAO stockDetailDAO = (StockDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STOCK_DETAIL);

    @Override
    public boolean saveItemDetails(ItemDetailDto itemDetailDto, StockDto stockDto, StockDetailDto stockDetailDto) throws SQLException {
        StockDAOImpl stockDAO = new StockDAOImpl();

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            Stock stock = new Stock(stockDto.getStockId(),stockDto.getStockName(),stockDto.getUserID());
            boolean isStockSave = stockDAO.save(stock);
            if(isStockSave){
                StockDetail stockDetail = new StockDetail(stockDetailDto.getStockId(),stockDetailDto.getSupplyId(),stockDetailDto.getUnitPrice(),stockDetailDto.getQuantity());
                boolean isStockDetailSaved = stockDetailDAO.save(stockDetail);
                if(isStockDetailSaved){
                    int qty = itemDetailDto.getQty();
                    ItemDetail itemDetail = new ItemDetail(itemDetailDto.getItemId(),itemDetailDto.getStockId(),itemDetailDto.getSellPrice(),itemDetailDto.getQty());
                    int oldQty = itemDetailDAO.getQty(itemDetail);
//                    int  = 0;
//                    if (rst > qty){
//                        oldQty = rst.getInt(1);
//                    }
                    if (oldQty != 0){
                        qty += oldQty;
                    }
                    itemDetailDto.setQty(qty);
                    boolean isItemDetailSaved = itemDetailDAO.save(itemDetail);
                    if (isItemDetailSaved){
                        connection.commit();
                        return true;
                    }
                }
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

    @Override
    public ArrayList<ItemDetailDto> getAllItems() throws SQLException {
        ArrayList<ItemDetail> itemDetails = itemDetailDAO.getAll();
        ArrayList<ItemDetailDto> itemDetailDtos = new ArrayList<>();
        for (ItemDetail item : itemDetails) {
            ItemDetailDto itemDto = new ItemDetailDto(
                    item.getItemId(),
                    item.getStockId(),
                    item.getSellPrice(),
                    item.getQty()
            );
            itemDetailDtos.add(itemDto);
        }
        return itemDetailDtos;
    }

    @Override
    public ArrayList<SupplierDto> searchSupplier(String id) throws SQLException {
        ArrayList<Supplier> suppliers = supplierDAO.search(id);;
        ArrayList<SupplierDto> supplierDtos = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            SupplierDto supplierDto = new SupplierDto(
                    supplier.getSupId(),
                    supplier.getName(),
                    supplier.getPhone(),
                    supplier.getAddress(),
                    supplier.getEmail(),
                    supplier.getUserId()
            );
            supplierDtos.add(supplierDto);
        }
        return supplierDtos;
    }

    @Override
    public String nextItemId() throws SQLException {
        return stockDAO.nextId();
    }
}
