package com.goteam.gohomerepairservicesapp;

import org.junit.Assert;
import org.junit.Test;


import static org.junit.Assert.*;


public class BasicUnitTest {

    // Unit tests for Deliverable 2 (5)

    @Test
    public void checkAdminRole(){

        User admin = new Admin();
        assertEquals("Check the role of the admin", "Admin", admin.getRoleName());
    }

    @Test
    public void checkHomeownerRole() {
        Homeowner aHomeowner = new Homeowner();
        assertEquals("Check the role of a homeowner", "Homeowner", aHomeowner.getRoleName());
    }

    @Test
    public void checkServiceProviderRole() {
        ServiceProvider aServiceProvider = new ServiceProvider();
        assertEquals("Check the role of a service provider", "Service Provider", aServiceProvider.getRoleName());
    }

    @Test
    public void checkServiceName() {
        Service aService = new Service("Pool installation", 62.99);
        assertEquals("Check the name of a service", "Pool installation", aService.getServiceName());
    }

    @Test
    public void checkServiceRate() {
        Service aService = new Service("Pool installation", 62.99);
        assertEquals("Check the rate of a service", 62.99, aService.getRate(), 0.01);
    }

    // Tests for Deliverable 3

    @Test
    public void checkServiceProviderLicense() {
        ServiceProvider aServiceProvider = new ServiceProvider();
        aServiceProvider.setLicenced(true);
        assertTrue("Check if a service provider is licensed", aServiceProvider.getLicensed());
    }

    @Test
    public void checkServiceProviderAddress() {
        ServiceProvider aServiceProvider = new ServiceProvider();
        aServiceProvider.setAddress("411 Keith Cres.");
        assertEquals("Check if a service provider has the correct address", "411 Keith Cres.", aServiceProvider.getAddress());
    }

    // Tests for Deliverable 4

    @Test
    public void checkBookingName() {
        Booking aBooking = new Booking();
        aBooking.setBookingName("Plumbing");
        assertEquals("Check if a booking has the correct name", "Plumbing", aBooking.getBookingName());
    }

    @Test
    public void checkBookingRate() {
        Booking bBooking = new Booking();
        bBooking.setRate(15.0);
        assertEquals("Check if a booking has the correct rate", 15.0, bBooking.getRate(), 0.01);
    }

    @Test
    public void checkServiceProviderRating() {
        ServiceProvider aServiceProvider = new ServiceProvider();
        aServiceProvider.setRating(3.0);
        assertEquals("Check if a rating has the correct value", 3.0, aServiceProvider.getRating(), 0.01);
    }

    @Test
    public void checkSearchType() {
        Homeowner aHomeOwner = new Homeowner();
        aHomeOwner.setSearchType(true);
        assertTrue("Check if a search is by type", aHomeOwner.getSearchType());
    }
    @Test
    public void checkSearchTime() {
        Homeowner aHomeOwner = new Homeowner();
        aHomeOwner.setSearchTime(true);
        assertTrue("Check if a search is by time", aHomeOwner.getSearchTime());
    }
    @Test
    public void checkSearchRating() {
        Homeowner aHomeOwner = new Homeowner();
        aHomeOwner.setSearchRating(true);
        assertTrue("Check if a search is by rating", aHomeOwner.getSearchRating());
    }

    @Test
    public void checkBookingDateTime() {
        Booking aBooking = new Booking();
        TimeOfAvailability aTime = new TimeOfAvailability();
        aBooking.setDateTime(aTime);
        assertEquals("Check if a dateTime has the correct date and time", aTime, aBooking.getDateTime());
    }
    @Test
    public void checkBookingServiceProvider() {
        Booking aBooking = new Booking();
        ServiceProvider aServiceProvider = new ServiceProvider();
        aBooking.setServiceProvider(aServiceProvider);
        assertEquals("Check if a ServiceProvider has the correct service provider", aServiceProvider, aBooking.getServiceProvider());
    }
    @Test
    public void checkBookingService() {
        Booking aBooking = new Booking();
        Service aService = new Service();
        aBooking.setService(aService);
        assertEquals("Check if a Service has the correct service", aService, aBooking.getService());
    }

    @Test
    public void checkBookingNameSet() {
        Booking aBooking = new Booking();
        aBooking.setBookingNameSet(true);
        assertTrue("Check if a Service has the correct service", aBooking.getBookingNameSet());
    }




}
