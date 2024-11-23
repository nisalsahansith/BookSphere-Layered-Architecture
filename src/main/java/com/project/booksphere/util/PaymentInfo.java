package com.project.booksphere.util;

import com.project.booksphere.dto.PaymentDetailsDto;

public class PaymentInfo {
    private static PaymentInfo instance;
    private PaymentDetailsDto paymentDetailsDto = new PaymentDetailsDto();

    public static PaymentInfo getInstance(){
        if (instance == null){
            instance = new PaymentInfo();
        }
        return instance;
    }

    public void setPaymentDetailsDto(PaymentDetailsDto paymentDetailsDto){
        this.paymentDetailsDto = paymentDetailsDto;
    }

    public PaymentDetailsDto getPaymentDetailsDto(){
        return paymentDetailsDto;
    }
}
