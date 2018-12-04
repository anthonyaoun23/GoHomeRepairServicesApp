package com.goteam.gohomerepairservicesapp;

public class Booking {

    private ServiceProvider serviceProvider;
    private TimeOfAvailability timeOfAvailability;

    public Booking(){
    }

    public Booking(ServiceProvider serviceProvider, TimeOfAvailability timeOfAvailability){
        this.serviceProvider = serviceProvider;
        this.timeOfAvailability = timeOfAvailability;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public TimeOfAvailability getTimeOfAvailability() {
        return timeOfAvailability;
    }
}
