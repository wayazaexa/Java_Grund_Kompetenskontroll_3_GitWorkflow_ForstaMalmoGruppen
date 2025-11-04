package org.example.entities;

public class Booking {

    private int id;
    private static int idGenerator;
    private Vehicle vehicle;
    private int date;
    private int price;
    private String email;
    private boolean isReady;

    public Booking(Vehicle vehicle, int date, int price, String email, boolean isReady) {
        this.id = ++idGenerator;
        this.vehicle = vehicle;
        this.date = date;
        this.price = price;
        this.email = email;
        this.isReady = isReady;
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

    public static void setIdGenerator(int idGenerator) {
        Booking.idGenerator = idGenerator;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
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
                ", price=" + price +
                ", email='" + email + '\'' +
                ", isReady=" + isReady +
                '}';
    }
}
