package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;
import com.project.booksphere.dto.CustomDto;
import com.project.booksphere.tm.ViewItemTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ViewItemBo extends SuperBo {

    ArrayList<CustomDto> searchFromID(String id) throws SQLException;
    ArrayList<CustomDto> searchFromName(String search) throws SQLException;
    ArrayList<CustomDto> getAllItems() throws SQLException;
}
