package com.project.booksphere.util;

import com.project.booksphere.dto.PaymentDto;

public class PaymentInfo {
    private static PaymentInfo instance;
    private PaymentDto paymentDto = new PaymentDto();

    public static PaymentInfo getInstance(){
        if (instance == null){
            instance = new PaymentInfo();
        }
        return instance;
    }

    public void setPaymentDetailsDto(PaymentDto paymentDto){
        this.paymentDto = paymentDto;
    }

    public PaymentDto getPaymentDetailsDto(){
        return paymentDto;
    }
}
