package com.goteam.gohomerepairservicesapp;

public class Booking {

    private ServiceProvider serviceProvider;
    private TimeOfAvailability timeOfAvailability;
    private String bookingName;

    public Booking(){
    }

    public Booking(String bookingName, ServiceProvider serviceProvider, TimeOfAvailability timeOfAvailability){
        this.serviceProvider = serviceProvider;
        this.timeOfAvailability = timeOfAvailability;
        this.bookingName = bookingName;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public TimeOfAvailability getTimeOfAvailability() {
        return timeOfAvailability;
    }

    public String getBookingName(){
        return bookingName;
    }
}
