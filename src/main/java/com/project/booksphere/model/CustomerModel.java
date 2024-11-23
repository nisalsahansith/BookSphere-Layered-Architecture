package com.project.booksphere.model;

import com.project.booksphere.db.DBConnection;
import com.project.booksphere.dto.CustomerDto;
import com.project.booksphere.util.CrudUtil;
import com.project.booksphere.util.SharedInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    UserDetailModel userDetailModel = new UserDetailModel();
    OrdersModel ordersModel = new OrdersModel();
    private SharedInfo sharedInfo = SharedInfo.getInstance();
    public boolean saveCustomer(CustomerDto customerDto) throws SQLException {
        String userId = sharedInfo.getUserID();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean save = CrudUtil.execute(
                    "insert into customer(CustomerId,Name,Phone,Email) values(?,?,?,?)",
                    customerDto.getId(),
                    customerDto.getName(),
                    customerDto.getPhone(),
                    customerDto.getEmail()
            );
            if (save){
                boolean savedUserDetails = userDetailModel.saveData(customerDto.getId(),userId);
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

    public ArrayList<CustomerDto> loadCustomerDetails() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM customer");
        ArrayList<CustomerDto> customerDtos = new ArrayList<>();
        while (rs.next()) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(rs.getString("customerId"));
            customerDto.setName(rs.getString("name"));
            customerDto.setPhone(rs.getString("phone"));
            customerDto.setEmail(rs.getString("email"));
            customerDtos.add(customerDto);
        }
        return customerDtos;
    }

    public String nextCustomerId() throws SQLException{
//        Connection connection = DBConnection.getInstance().getConnection();
//        String sql = "SELECT CustomerId FROM customer ORDER BY CustomerId DESC LIMIT 1 ";
//        PreparedStatement statement = connection.prepareStatement(sql);
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

    public boolean updateCustomer(CustomerDto customerDto) throws SQLException {
        return CrudUtil.execute(
                "UPDATE customer SET  Name = ?, Phone = ?, Email = ? WHERE CustomerId = ?",
                customerDto.getName(),
                customerDto.getPhone(),
                customerDto.getEmail(),
                customerDto.getId()
        );
    }

    public boolean deleteCustomer(String customerId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean deleteFromUser = userDetailModel.delete(customerId);
                if (deleteFromUser) {
                    boolean nullOrders = ordersModel.nullOrders(customerId);
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

    public CustomerDto searchCustomer(String no) throws SQLException {
        ResultSet rst = CrudUtil.execute("select CustomerID,Name from customer where Phone = ?",no);
        CustomerDto customerDto = new CustomerDto();
        if (rst.next()){
            customerDto.setId(rst.getString(1));
            customerDto.setName(rst.getString(2));
        }
        return customerDto;
    }

    public ArrayList<CustomerDto> searchByCustID(String searchText) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM customer where CustomerId = ?",searchText);
        ArrayList<CustomerDto> customerDtos = new ArrayList<>();
        while (rs.next()) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(rs.getString("customerId"));
            customerDto.setName(rs.getString("name"));
            customerDto.setPhone(rs.getString("phone"));
            customerDto.setEmail(rs.getString("email"));
            customerDtos.add(customerDto);
        }
        return customerDtos;
    }
}
