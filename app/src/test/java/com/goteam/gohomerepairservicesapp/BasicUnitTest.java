package com.goteam.gohomerepairservicesapp;

import org.junit.Test;


import static org.junit.Assert.*;


public class BasicUnitTest {

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

}
