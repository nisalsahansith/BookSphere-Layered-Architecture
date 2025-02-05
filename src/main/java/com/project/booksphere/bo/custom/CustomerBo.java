package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;
import com.project.booksphere.dto.CustomerDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBo extends SuperBo {

    public boolean saveCustomer(CustomerDto customerDto) throws SQLException;
    public boolean deleteCustomer(String customerId) throws SQLException;
    public boolean updateCustomer(CustomerDto customerDto) throws SQLException;
    public String nextCustomerId() throws SQLException;
    public ArrayList<CustomerDto> getAllCustomer() throws SQLException;
    public ArrayList<CustomerDto> searchCustomer(String searchText) throws SQLException;
}
