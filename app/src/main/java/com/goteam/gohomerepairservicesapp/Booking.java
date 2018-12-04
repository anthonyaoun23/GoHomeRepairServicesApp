package com.goteam.gohomerepairservicesapp;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

import java.io.Serializable;


public class Booking implements Serializable {

    private ServiceProvider serviceProvider;
    private TimeOfAvailability dateTime;
    private String bookingName;

    public Booking(){
    }

    public Booking(String bookingName, ServiceProvider serviceProvider, TimeOfAvailability dateTime){
        this.serviceProvider = serviceProvider;
        this.dateTime=dateTime;
        this.bookingName = bookingName;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public TimeOfAvailability getDateTime(){
        return dateTime;
    }

    public String getBookingName(){
        return bookingName;
    }

    public void setBookingName(String bookingName) { this.bookingName = bookingName; }
}
