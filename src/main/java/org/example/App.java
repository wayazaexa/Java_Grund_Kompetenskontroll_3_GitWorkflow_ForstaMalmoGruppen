package org.example;

import org.example.entities.Booking;
import org.example.entities.BookingStatus;
import org.example.entities.EmailSender;
import org.example.entities.Vehicle;
import org.example.services.BookingService;
import org.example.services.NotificationService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class App {
    public static void main(String[] args) {

//    NotificationService notificationService = getNotificationService();
//    notificationService.notifyBookingEvent(new Booking(1, new Vehicle("eer44", "bmw",2000),
//            LocalTime.now(), LocalDate.now(), 500, "david.refai@hotmail.com"), BookingStatus.BOOKED);
//        System.out.println("Email sent successfully");
    }

//    private static NotificationService getNotificationService() {

//        Properties props = new Properties();
//        try (FileInputStream fis = new FileInputStream("local.properties")) {
//            props.load(fis);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        String host = props.getProperty("SMTP_HOST");
//        int port = Integer.parseInt(props.getProperty("SMTP_PORT"));
//        String user = props.getProperty("SMTP_USER");
//        String pass = props.getProperty("SMTP_PASS");
//        String from = props.getProperty("SMTP_FROM");

//        EmailSender sender = new EmailSender(host, port, user, pass, from);


//        return new NotificationService(sender);
//    }
}
