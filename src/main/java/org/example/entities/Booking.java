package org.example.entities;

import org.example.exceptions.InvalidVehicleException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking {

    private static int idGenerator;
    private int id;
    private Vehicle vehicle;
    private LocalDateTime date;
    private int price;
    private String email;
    private BookingStatus status;

    public Booking(Vehicle vehicle, LocalDateTime date, String email) {
        if (vehicle == null) {
            throw new InvalidVehicleException("Vehicle was not created correctly");
        }
        this.vehicle = vehicle;
        this.date = date;
        this.email = email;
        this.status = BookingStatus.BOOKED;
        this.id = ++idGenerator;
    }

    public int getId() {
        return id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void printShortInfo() {
        String ios = String.valueOf(date);
        LocalDateTime dt = LocalDateTime.parse(ios);
        DateTimeFormatter nice = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy 'kl.' HH:mm");
        System.out.println("Booking - id: " + id + "RegNr: " + vehicle.getRegNr() + ", date: " + dt.format(nice) + " , status: " + status);
    }

    @Override
    public String toString() {
        return "id: " + id +
                ", vehicle: [ " + vehicle + " ]" +
                ", date: " + date +
                ", price: " + price +
                ", email: '" + email + '\'' +
                ", status: " + status;
    }
}
