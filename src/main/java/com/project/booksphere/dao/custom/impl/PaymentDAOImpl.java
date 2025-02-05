package com.project.booksphere.dao.custom.impl;

import com.project.booksphere.dao.custom.PaymentDAO;
import com.project.booksphere.dto.PaymentDto;
import com.project.booksphere.dao.CrudUtil;
import com.project.booksphere.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public ArrayList<Payment> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from payment");
        ArrayList<Payment> payments = new ArrayList<>();
        while (rst.next()){
            Payment payment = new Payment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(5),
                    rst.getDouble(4),
                    rst.getDate(3)
            );
            payments.add(payment);
        }
        return payments;
    }

    @Override
    public String nextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT PaymentID FROM payment ORDER BY PaymentID DESC LIMIT 1  ");
        if (rst.next()){
            String string = rst.getString(1);
            String subString = string.substring(3);
            int lastIndex = Integer.parseInt(subString);
            int nextIndex = lastIndex + 1;
            String newId = String.format("PAY%03d", nextIndex);
            return newId;
        }
        return "PAY001";
    }


    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from payment where OrderID = ?",id);
    }

    @Override
    public ArrayList<Payment> search(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from payment where PaymentID = ?",id);
        ArrayList<Payment> payments = new ArrayList<>();
        while (rst.next()){
            Payment payment = new Payment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(5),
                    rst.getDouble(4),
                    rst.getDate(3)
            );
            payments.add(payment);
        }
        return payments;
    }

    @Override
    public boolean save(Payment payment) throws SQLException {
        return CrudUtil.execute("insert into payment values(?,?,?,?,?)",
                payment.getPaymentId(),
                payment.getOrderId(),
                payment.getDate(),
                payment.getTotal(),
                payment.getPaymentMethod()
        );
    }

    @Override
    public boolean update(Payment payment) throws SQLException {
        return false;
    }

}
