package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;
import com.project.booksphere.dto.CustomDto;
import com.project.booksphere.dto.ItemDto;
import com.project.booksphere.tm.ItemTm;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InventoryBo extends SuperBo {

    public boolean saveItem(ItemDto itemDto) throws SQLException;
    public boolean deleteItem(String id) throws SQLException;
    public boolean updateItem(ItemDto itemDto) throws SQLException;
    public ArrayList<CustomDto> searchingItem(String search) throws SQLException;
    public ArrayList<CustomDto> searchByItemName(String search) throws SQLException;
    public String nextItemId() throws SQLException;
    public ArrayList<CustomDto> getAllItems() throws SQLException;
}
