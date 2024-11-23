package com.project.booksphere.util;

public class Validate {
    public String namePattern = "^[a-zA-Z]+$";
    public String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
    public String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

}
