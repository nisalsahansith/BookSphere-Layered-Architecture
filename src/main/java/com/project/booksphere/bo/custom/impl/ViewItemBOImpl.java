package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.ViewItemBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.ItemDAO;
import com.project.booksphere.dao.custom.QueryDAO;
import com.project.booksphere.dao.custom.impl.ItemDAOImpl;
import com.project.booksphere.dto.CustomDto;
import com.project.booksphere.entity.Custom;
import com.project.booksphere.tm.ViewItemTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewItemBOImpl implements ViewItemBo {
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ITEM);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY_DAO);

    @Override
    public ArrayList<CustomDto> searchFromID(String id) throws SQLException {
        ArrayList<Custom> customs = queryDAO.searchFromID(id);
        ArrayList<CustomDto> customDtos = new ArrayList<>();
        for (Custom custom: customs){
            CustomDto customDto = new CustomDto(
                    custom.getItemId(),
                    custom.getItemDescription(),
                    custom.getISBN(),
                    custom.getSellPrice(),
                    custom.getQtyOnHand(),
                    custom.getStockId(),
                    custom.getSupName()
            );
            customDtos.add(customDto);
        }
        return customDtos;
    }

    @Override
    public ArrayList<CustomDto> searchFromName(String search) throws SQLException {
        ArrayList<Custom> customs = queryDAO.searchFromName(search);
        ArrayList<CustomDto> customDtos = new ArrayList<>();
        for (Custom custom: customs){
            CustomDto customDto = new CustomDto(
                    custom.getItemId(),
                    custom.getItemDescription(),
                    custom.getISBN(),
                    custom.getSellPrice(),
                    custom.getQtyOnHand(),
                    custom.getStockId(),
                    custom.getSupName()
            );
            customDtos.add(customDto);
        }
        return customDtos;
    }

    @Override
    public ArrayList<CustomDto> getAllItems() throws SQLException {
        ArrayList<Custom> customs = queryDAO.getAllItems();
        ArrayList<CustomDto> customDtos = new ArrayList<>();
        for (Custom custom: customs){
            CustomDto customDto = new CustomDto(
                    custom.getItemId(),
                    custom.getItemDescription(),
                    custom.getISBN(),
                    custom.getSellPrice(),
                    custom.getQtyOnHand(),
                    custom.getStockId(),
                    custom.getSupName()
            );
            customDtos.add(customDto);
        }
        return customDtos;
    }

}
