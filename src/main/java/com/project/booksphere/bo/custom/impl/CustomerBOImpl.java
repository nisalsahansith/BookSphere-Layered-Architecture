package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.CustomerBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.CustomerDAO;
import com.project.booksphere.dao.custom.OrderDAO;
import com.project.booksphere.dao.custom.UserDetailDAO;
import com.project.booksphere.db.DBConnection;
import com.project.booksphere.dto.CustomerDto;
import com.project.booksphere.dto.UserDetailDto;
import com.project.booksphere.dao.CrudUtil;
import com.project.booksphere.entity.Customer;
import com.project.booksphere.entity.UserDetail;
import com.project.booksphere.util.SharedInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBo {
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);
    private final UserDetailDAO userDetailDAO = (UserDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER_DETAIL);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);
    private final SharedInfo sharedInfo = SharedInfo.getInstance();

    @Override
    public boolean saveCustomer(CustomerDto customerDto) throws SQLException {
        Customer customer = new Customer(customerDto.getId(),customerDto.getName(),customerDto.getPhone(),customerDto.getEmail());
        String userId = sharedInfo.getUserID();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean save = customerDAO.save(customer);
            if (save){
                boolean savedUserDetails = userDetailDAO.save(new UserDetail(userId,customerDto.getId()));
                if (savedUserDetails){
                    connection.setAutoCommit(true);
                    return true;
                }
            }
            connection.rollback();
            return false;
        }catch (SQLException e){
            try {
                connection.rollback();
            }catch (SQLException roll){
                throw new RuntimeException(roll+"roll back went wrong");
            }
            throw new RuntimeException(e);
        }finally {
            try {
                connection.setAutoCommit(true);
            }catch (RuntimeException e){
                throw new RuntimeException();
            }
        }
    }

    @Override
    public boolean deleteCustomer(String customerId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean deleteFromUser = userDetailDAO.delete(customerId);
            if (deleteFromUser) {
                boolean nullOrders = orderDAO.nullOrders(customerId);
                boolean isDelete = CrudUtil.execute("delete from customer where CustomerId = ?", customerId);
                if (isDelete) {
                    connection.setAutoCommit(true);
                    return true;
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
    public boolean updateCustomer(CustomerDto customerDto) throws SQLException {
        Customer customer = new Customer(customerDto.getId(),customerDto.getName(),customerDto.getPhone(),customerDto.getEmail());
        return customerDAO.update(customer);
    }

    @Override
    public String nextCustomerId() throws SQLException{
        return customerDAO.nextId();
    }

    @Override
    public ArrayList<CustomerDto> getAllCustomer() throws SQLException {
        ArrayList<Customer> customerDto = customerDAO.getAll();
        ArrayList<CustomerDto> customerDtoDto = new ArrayList<>();
        for (Customer customer : customerDto) {
            CustomerDto customerDtos = new CustomerDto(
                    customer.getId(),
                    customer.getName(),
                    customer.getPhone(),
                    customer.getEmail(),
                    ""
            );
            customerDtoDto.add(customerDtos);
        }
        return customerDtoDto;
    }

    @Override
    public ArrayList<CustomerDto> searchCustomer(String searchText) throws SQLException {
        ArrayList<Customer> customerDto = customerDAO.search(searchText);
        ArrayList<CustomerDto> customerDtoDto = new ArrayList<>();
        for (Customer customer : customerDto) {
            CustomerDto customerDtos = new CustomerDto(
                    customer.getId(),
                    customer.getName(),
                    customer.getPhone(),
                    customer.getEmail(),
                    ""
            );
            customerDtoDto.add(customerDtos);
        }
        return customerDtoDto;
    }
}
