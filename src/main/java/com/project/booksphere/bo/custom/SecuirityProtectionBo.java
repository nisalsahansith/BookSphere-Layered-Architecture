package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;

import java.sql.SQLException;

public interface SecuirityProtectionBo extends SuperBo {

    public String getMail() throws SQLException;
}
