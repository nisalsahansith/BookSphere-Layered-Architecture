package com.project.booksphere.dao.custom;

import com.project.booksphere.dao.SuperDAO;
import com.project.booksphere.entity.Custom;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {

    ArrayList<Custom> searching(String search) throws SQLException;

    ArrayList<Custom> searchByName(String search) throws SQLException;

    double getSellPrice(String itemId) throws SQLException;

    ArrayList<Custom> searchFromName(String search) throws SQLException;

    ArrayList<Custom> getAllItems() throws SQLException;

    ArrayList<Custom> searchFromID(String id) throws SQLException;

    ArrayList<Custom> searchFromOrderID(String id) throws SQLException;

    ArrayList<Custom> getOrdersAll() throws SQLException;

    ArrayList<Custom> searchID(String id) throws SQLException;

    ArrayList<Custom> getAlls() throws SQLException;

    public ArrayList<Custom> getAllItem() throws SQLException;

}
