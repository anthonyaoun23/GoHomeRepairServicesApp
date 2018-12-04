package com.goteam.gohomerepairservicesapp;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

import java.io.Serializable;


public class Booking implements Serializable {

    private ServiceProvider serviceProvider;
    private TimeOfAvailability dateTime;
    private String bookingName;
    private Service service;
    private double rate;

    public Booking(){
    }

    public Booking(String bookingName, ServiceProvider serviceProvider, TimeOfAvailability dateTime, Service service, Double rate){
        this.serviceProvider = serviceProvider;
        this.dateTime=dateTime;
        this.bookingName = bookingName;
        this.service=service;
        this.rate=rate;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public TimeOfAvailability getDateTime(){
        return dateTime;
    }

    public Double getRate(){return rate;}

    public Service getService(){ return service;}

    public String getBookingName(){
        return bookingName;
    }

    public void setBookingName(String bookingName) { this.bookingName = bookingName; }
}
