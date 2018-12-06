package com.goteam.gohomerepairservicesapp;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

import java.io.Serializable;


public class Booking implements Serializable {

    private ServiceProvider serviceProvider;
    private TimeOfAvailability dateTime;
    private String bookingName;
    private boolean bookingNameSet;
    private Service service;
    private double rate;

    public Booking(){
    }

    public Booking(String bookingName, ServiceProvider serviceProvider, TimeOfAvailability dateTime, Service service, double rate){
        this.serviceProvider = serviceProvider;
        this.dateTime=dateTime;
        this.bookingName = bookingName;
        this.service=service;
        this.rate=rate;
    }



    public void setBookingNameSet(boolean bookingNameSet){this.bookingNameSet=bookingNameSet;}

    public boolean getBookingNameSet() {
        return bookingNameSet;
    }

    public void setServiceProvider(ServiceProvider serviceProvider){this.serviceProvider=serviceProvider;}

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }


    public void setDateTime(TimeOfAvailability dateTime){this.dateTime=dateTime;}
    public TimeOfAvailability getDateTime(){
        return dateTime;
    }


    public double getRate(){return rate;}

    public void setService(Service service){this.service =service;}
    public Service getService(){ return service;}


    public String getBookingName(){
        return bookingName;
    }

    public void setRate(double rate){this.rate=rate;}

    public void setBookingName(String bookingName) { this.bookingName = bookingName; }
}
