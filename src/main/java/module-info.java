module com.example.booksphere {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires javafx.base;
    requires mysql.connector.j;
//    requires javax.mail.api;
    requires java.mail;
    requires net.sf.jasperreports.core;
//    requires bcrypt;
    requires jbcrypt;


    opens com.project.booksphere.dto.tm to javafx.base;
    opens com.project.booksphere.dto to javafx.base;
//    opens com.project.booksphere.dto to javafx.fxml;
    opens com.project.booksphere.controller to javafx.fxml;
    exports com.project.booksphere;
}