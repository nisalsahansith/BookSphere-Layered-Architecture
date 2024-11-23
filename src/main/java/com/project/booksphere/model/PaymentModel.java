package com.project.booksphere.model;

import com.project.booksphere.dto.PaymentDetailsDto;
import com.project.booksphere.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentModel {
    public String getNextId() throws SQLException {
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

    public boolean savePayment(PaymentDetailsDto paymentDetailsDto) throws SQLException {
        return CrudUtil.execute("insert into payment values(?,?,?,?,?)",
                    paymentDetailsDto.getPaymentId(),
                    paymentDetailsDto.getOrderId(),
                    paymentDetailsDto.getDate(),
                    paymentDetailsDto.getTotal(),
                    paymentDetailsDto.getPaymentMethod()
                );
    }

    public boolean deletePayment(String id) throws SQLException {
        return CrudUtil.execute("delete from payment where OrderID = ?",id);
    }

    public ArrayList<PaymentDetailsDto> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from payment");
        ArrayList<PaymentDetailsDto> paymentDetailsDtos = new ArrayList<>();
        while (rst.next()){
            PaymentDetailsDto paymentDetailsDto = new PaymentDetailsDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(5),
                    rst.getDouble(4),
                    rst.getDate(3)
            );
            paymentDetailsDtos.add(paymentDetailsDto);
        }
        return paymentDetailsDtos;
    }

    public ArrayList<PaymentDetailsDto> getAllID(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from payment where PaymentID = ?",id);
        ArrayList<PaymentDetailsDto> paymentDetailsDtos = new ArrayList<>();
        while (rst.next()){
            PaymentDetailsDto paymentDetailsDto = new PaymentDetailsDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(5),
                    rst.getDouble(4),
                    rst.getDate(3)
            );
            paymentDetailsDtos.add(paymentDetailsDto);
        }
        return paymentDetailsDtos;
    }
}
