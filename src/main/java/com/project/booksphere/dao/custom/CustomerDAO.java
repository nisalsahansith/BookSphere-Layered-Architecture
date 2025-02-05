package com.project.booksphere.dao.custom;

import com.project.booksphere.dao.CrudDAO;
import com.project.booksphere.dto.CustomerDto;
import com.project.booksphere.entity.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {
//    public boolean saveCustomer(CustomerDto customerDto) throws SQLException;
    public Customer searchCustomer(String no) throws SQLException;
}
