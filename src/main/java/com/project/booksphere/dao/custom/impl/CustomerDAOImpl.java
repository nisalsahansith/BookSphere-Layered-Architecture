package com.project.booksphere.dao.custom.impl;

import com.project.booksphere.dao.custom.CustomerDAO;
import com.project.booksphere.dto.CustomerDto;
import com.project.booksphere.dao.CrudUtil;
import com.project.booksphere.entity.Customer;
import com.project.booksphere.util.SharedInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    private SharedInfo sharedInfo = SharedInfo.getInstance();
    private UserDetailDAOImpl userDetailDAO = new UserDetailDAOImpl();
    private OrderDAOImpl orderDAO = new OrderDAOImpl();

    @Override
    public ArrayList<Customer> getAll() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM customer");
        ArrayList<Customer> customers = new ArrayList<>();
        while (rs.next()) {
            Customer customerDto = new Customer();
            customerDto.setId(rs.getString("customerId"));
            customerDto.setName(rs.getString("name"));
            customerDto.setPhone(rs.getString("phone"));
            customerDto.setEmail(rs.getString("email"));
            customers.add(customerDto);
        }
        return customers;
    }

//    @Override
//    public boolean saveCustomer(CustomerDto customerDto) throws SQLException {
//        String userId = sharedInfo.getUserID();
//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//            connection.setAutoCommit(false);
//            boolean save = save(customerDto);
//            if (save){
//                boolean savedUserDetails = userDetailDAO.save(new UserDetailDto(userId,customerDto.getId()));
//                if (savedUserDetails){
//                    connection.setAutoCommit(true);
//                    return true;
//                }
//            }
//            connection.rollback();
//            return false;
//        }catch (SQLException e){
//            try {
//                connection.rollback();
//            }catch (SQLException roll){
//                throw new RuntimeException(roll+"roll back went wrong");
//            }
//            throw new RuntimeException(e);
//        }finally {
//            try {
//                connection.setAutoCommit(true);
//            }catch (RuntimeException e){
//                throw new RuntimeException();
//            }
//        }
//    }

    @Override
    public boolean save(Customer customer) throws SQLException {
       return CrudUtil.execute(
                "insert into customer(CustomerId,Name,Phone,Email) values(?,?,?,?)",
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getEmail()
        );
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//            connection.setAutoCommit(false);
//            boolean deleteFromUser = userDetailDAO.delete(customerId);
//            if (deleteFromUser) {
//                boolean nullOrders = orderDAO.nullOrders(customerId);
//                boolean isDelete = CrudUtil.execute("delete from customer where CustomerId = ?", customerId);
//                if (isDelete) {
//                    connection.setAutoCommit(true);
//                    return true;
//                }
//            }
//            connection.rollback();
//            return false;
//        }catch (SQLException e){
//            connection.rollback();
//            return false;
//        }finally {
//            connection.setAutoCommit(true);
//        }
        return false;
    }

    @Override
    public boolean update(Customer customer) throws SQLException {
        return CrudUtil.execute(
                "UPDATE customer SET  Name = ?, Phone = ?, Email = ? WHERE CustomerId = ?",
                customer.getName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getId()
        );
    }

    @Override
    public String nextId() throws SQLException{
        ResultSet rst = CrudUtil.execute("SELECT CustomerId FROM customer ORDER BY CustomerId DESC LIMIT 1");
        if (rst.next()){
            String string = rst.getString(1);
            String subString = string.substring(1);
            int lastIndex = Integer.parseInt(subString);
            int nextIndex = lastIndex + 1;
            String newId = String.format("C%03d", nextIndex);
            return newId;
        }
        return "C001";
    }

//    public ArrayList<CustomerDto> search(String id) throws SQLException {
//        return null;
//    }

    @Override
    public ArrayList<Customer> search(String searchText) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM customer where CustomerId = ?",searchText);
        ArrayList<Customer> customers = new ArrayList<>();
        while (rs.next()) {
            Customer customer = new Customer();
            customer.setId(rs.getString("customerId"));
            customer.setName(rs.getString("name"));
            customer.setPhone(rs.getString("phone"));
            customer.setEmail(rs.getString("email"));
            customers.add(customer);
        }
        return customers;
    }

    @Override
    public Customer searchCustomer(String no) throws SQLException {
        ResultSet rst = CrudUtil.execute("select CustomerID,Name from customer where Phone = ?",no);
        Customer customer = new Customer();
        if (rst.next()){
            customer.setId(rst.getString(1));
            customer.setName(rst.getString(2));
        }
        return customer;
    }

}
