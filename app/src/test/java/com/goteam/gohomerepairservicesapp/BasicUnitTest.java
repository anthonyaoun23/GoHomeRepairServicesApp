package com.goteam.gohomerepairservicesapp;

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


}
