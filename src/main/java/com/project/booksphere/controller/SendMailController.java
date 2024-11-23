//package com.project.booksphere.controller;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//
//import javax.mail.*;
//import javax.mail.internet.AddressException;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.net.URL;
//import java.util.Properties;
//import java.util.ResourceBundle;
//
//public class SendMailController implements Initializable {
//
//    @FXML
//    private Button btnSend;
//
//    @FXML
//    private TextArea txtBody;
//
//    @FXML
//    private TextField txtFromEmail;
//
//    @FXML
//    private TextField txtSubject;
//
//    @FXML
//    private TextField txtToEmail;
//
//    @FXML
//    void sendGmail(ActionEvent event) {
//        String toEmail = txtToEmail.getText();
//        if (toEmail == null) {
//            return;
//        }
//        final String fromEmail = txtFromEmail.getText();
//        String subject = txtSubject.getText();
//        String body = txtBody.getText();
//
//        if (subject.isEmpty() || body.isEmpty()) {
//            new Alert(Alert.AlertType.WARNING,"Subject and body are required").show();
//            return;
//        }
//        sendEmailwithGmail(fromEmail,toEmail,subject,body);
//    }
//
//    private void sendEmailwithGmail(String fromEmail, String toEmail, String subject, String body) {
//        String PASSWORD = "uhxc yzuv fezc xgjb";
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//
//        Session session = Session.getInstance(props,new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(fromEmail, PASSWORD);
//            }
//        });
//
//        try { new===pmre twrh qmzu evdw
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(fromEmail));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
//            message.setSubject(subject);
//            message.setText(body);
//            Transport.send(message);
//            new Alert(Alert.AlertType.INFORMATION,"Email sent").show();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR,"Email not sent").show();
//        }
//
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        txtFromEmail.setText("nisalsahansith@gmail.com");
//        refreshPage();
//    }
//
//    private void refreshPage() {
//        txtFromEmail.setText("nisalsahansith@gmail.com");
//        txtBody.setText("");
//        txtSubject.setText("");
//        txtToEmail.setText("");
//    }
//}

package com.project.booksphere.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class SendMailController implements Initializable {

    @FXML
    private Button btnSend;

    @FXML
    private TextArea txtBody;

    @FXML
    private TextField txtFromEmail;

    @FXML
    private TextField txtSubject;

    @FXML
    private TextField txtToEmail;

    @FXML
    void sendGmail(ActionEvent event) {
        String toEmail = txtToEmail.getText();
        String fromEmail = txtFromEmail.getText();
        String subject = txtSubject.getText();
        String body = txtBody.getText();

        if (!isValidEmail(toEmail)) {
            new Alert(Alert.AlertType.WARNING, "Invalid recipient email address").show();
            return;
        }

        if (subject.isEmpty() || body.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Subject and body are required").show();
            return;
        }

        sendEmailWithGmail(fromEmail, toEmail, subject, body);
    }

    public void sendEmailWithGmail(String fromEmail, String toEmail, String subject, String body) {
        final String PASSWORD = System.getenv("EMAIL_PASSWORD");
        System.out.println("Retrieved Password: " + PASSWORD);
        if (PASSWORD == null || PASSWORD.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Email password not configured").show();
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully").show();
        } catch (AddressException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid email address").show();
            e.printStackTrace();
        } catch (MessagingException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to send email").show();
            e.printStackTrace();
        }
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtFromEmail.setText("bookspherecom@gmail.com"); // Replace with your desired default email or remove for manual input
        refreshPage();
    }

    private void refreshPage() {
        txtToEmail.setText("");
        txtSubject.setText("");
        txtBody.setText("");
    }


    public void setEmail(String text) {
        this.txtToEmail.setText(text);
    }


}

