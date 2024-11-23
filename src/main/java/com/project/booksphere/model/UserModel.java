package com.project.booksphere.model;

import com.project.booksphere.dto.UserDto;
import com.project.booksphere.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserModel {
//    private SupplierModel supplierModel = new SupplierModel();
//    private StockModel stockModel = new StockModel();
//    private OrdersModel ordersModel = new OrdersModel();
//    private UserDetailModel userDetailModel = new UserDetailModel();

    public String getUserID(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select UserID from user where Employ_ID = ?", id);
        String name = null;
        if (rst.next()){
            name = rst.getString(1);
        }
        return name;
    }

    public ArrayList<UserDto> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from user");
        ArrayList<UserDto> userDtos = new ArrayList<>();
        while (rst.next()){
            UserDto userDto = new UserDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            userDtos.add(userDto);
        }
        return userDtos;
    }

    public ArrayList<UserDto> getAllId(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from user where UserID = ?",id);
        ArrayList<UserDto> userDtos = new ArrayList<>();
        while (rst.next()){
            UserDto userDto = new UserDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            userDtos.add(userDto);
        }
        return userDtos;
    }

    public boolean deleteUser(String id) throws SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        try{
//            connection.setAutoCommit(false);
//            boolean isnull = supplierModel.deleteUser(id);
//            System.out.println(isnull);
//            boolean isNullStock = stockModel.deleteUser(id);
//            System.out.println(isNullStock);
//            boolean isnullOrders = ordersModel.nullUser(id);
//            System.out.println(isnullOrders);
//            boolean isNullUserDetail = userDetailModel.nullUser(id);
//            System.out.println(isNullUserDetail);
//            if (isnull || isNullStock || isNullUserDetail || isnullOrders){
//                boolean result = CrudUtil.execute("delete from user where UserID = ?", id);
//                System.out.println(result);
//                if (result){
//                    connection.commit();
//                    return true;
//                }
//            }
//            connection.rollback();
//            return false;
//        }catch (SQLException e){
//            try {
//               connection.rollback();
//            }catch (SQLException e1){
//                e1.printStackTrace();
//            }
//            throw new RuntimeException(e.getMessage());
//        }finally {
//            try {
//                connection.setAutoCommit(true);
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
            return CrudUtil.execute("delete from user where UserID = ?", id);

    }

    public String getMail(String employeeId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select email from employee where EmployeeID = ?", employeeId);
        if (rst.next()){
            return rst.getString(1);
        }
        return null;
    }

    public boolean setUserName(String userId, String userName) throws SQLException {
        return CrudUtil.execute("update user set UserName = ? where UserID = ?",userName,userId);
    }

    public String getName(String id) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select UserName from user where UserID = ?",id);
        String name = "";
        if (resultSet.next()){
            name = resultSet.getString(1);
        }
        return name;
    }

    public String getPassword(String userId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Password from user where UserID = ?",userId);
        String password = "";
        if (resultSet.next()){
            password = resultSet.getString(1);
        }
        return password;
    }

    public boolean updatePassword(String newPassword, String userId) throws SQLException {
        return CrudUtil.execute("update user set Password = ? where UserID = ?",newPassword,userId);
    }

    public boolean resetPassword(String newPassword, String empId) throws SQLException {
        return CrudUtil.execute("update user set Password = ? where Employ_ID = ?",newPassword,empId);
    }
}
