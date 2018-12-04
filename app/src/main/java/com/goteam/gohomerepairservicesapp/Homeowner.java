package com.goteam.gohomerepairservicesapp;

import java.util.ArrayList;

public class Homeowner extends User {

    ArrayList<Booking> bookings;

    @SuppressWarnings("unused") // for Firebase usage
    public Homeowner() {
        super("homeowner");
        bookings = new ArrayList();
    }

    @Override
    public String getRoleName() {
        return "Homeowner";
    }

    public void addBooking(Booking booking){
        this.bookings.add(booking);
    }

    public ArrayList<Booking> getBookings(){
        return this.bookings;
    }

}
