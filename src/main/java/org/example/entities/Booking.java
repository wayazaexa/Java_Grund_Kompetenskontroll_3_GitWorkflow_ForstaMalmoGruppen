package org.example.entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {

    private int id;
    private static int idGenerator;
    private Vehicle vehicle;
    private LocalDate date;
    private LocalTime time;
    private int price;
    private String email;
    private boolean isReady;

    public Booking(Vehicle vehicle, LocalTime time ,LocalDate date, int price, String email, boolean isReady) {
        this.id = ++idGenerator;
        this.vehicle = vehicle;
        this.date = date;
        this.price = price;
        this.email = email;
        this.isReady = isReady;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getIdGenerator() {
        return idGenerator;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public static void setIdGenerator(int idGenerator) {
        Booking.idGenerator = idGenerator;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", vehicle='" + vehicle + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", price=" + price +
                ", email='" + email + '\'' +
                ", isReady=" + isReady +
                '}';
    }
}
