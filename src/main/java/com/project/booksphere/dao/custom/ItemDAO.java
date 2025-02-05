package com.project.booksphere.dao.custom;

import com.project.booksphere.dao.CrudDAO;
import com.project.booksphere.dto.ItemDto;
import com.project.booksphere.entity.Item;
import com.project.booksphere.tm.ItemTm;
import com.project.booksphere.tm.ViewItemTM;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item> {
//    ArrayList<ItemTm> searching(String search) throws SQLException;
//    ArrayList<ItemTm> searchByName(String search) throws SQLException;
    String searchItem(String id) throws SQLException;
//    double getSellPrice(String itemId) throws SQLException;
    String searchBy(String search) throws SQLException;
//    ArrayList<ViewItemTM> searchFromID(String id) throws SQLException;
//    ArrayList<ViewItemTM> searchFromName(String search) throws SQLException;
//    ArrayList<ViewItemTM> getAllItems() throws SQLException;
}
