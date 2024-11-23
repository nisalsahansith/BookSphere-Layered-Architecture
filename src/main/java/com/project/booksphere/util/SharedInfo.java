package com.project.booksphere.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SharedInfo {
    private static SharedInfo instance;
    private String itemId;
    private String orderId;
    private double total;
    private String employeeId;
    private String userID;
    private String customerEmail;
    private boolean isOwnerSubmit;

    public static SharedInfo getInstance() {
        if (instance == null) {
            instance = new SharedInfo();
        }
        return instance;
    }
    public SharedInfo() {}
    public String getItemId() {
        return itemId;
    }
    public void setItemId(String itemId) {
        System.out.println();
        this.itemId = itemId;
    }
    public String getOrderId(){
        return orderId;
    }

    public void setOrderId(String orderId){
        System.out.println(orderId);
        this.orderId = orderId;
    }

    public double getTotal(){
        return total;
    }

    public void setTotal(double total){
        System.out.println(total);
        this.total = total;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        System.out.println(employeeId);
        this.employeeId = employeeId;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        System.out.println(userID);
        this.userID = userID;
    }

    public boolean isOwnerSubmit() {
        return isOwnerSubmit;
    }
}
