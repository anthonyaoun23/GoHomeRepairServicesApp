package com.goteam.gohomerepairservicesapp;

import java.util.ArrayList;

public class Homeowner extends User {

    ArrayList<Booking> bookings;
    private boolean searchType, searchTime, searchRating;

    @SuppressWarnings("unused") // for Firebase usage
    public Homeowner() {
        super("homeowner");
        bookings = new ArrayList();
    }

    @Override
    public String getRoleName() {
        return "Homeowner";
    }

    public void setBookings() { this.bookings = bookings; }

    public void addBooking(Booking booking){
        this.bookings.add(booking);
    }

    public ArrayList<Booking> getBookings(){
        return this.bookings;
    }

    public boolean getSearchType() {
        return searchType;
    }

    public void setSearchType(boolean searchType) {
        this.searchType = searchType;
    }

    public boolean getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(boolean searchTime) {
        this.searchTime = searchTime;
    }

    public boolean getSearchRating() {
        return searchRating;
    }

    public void setSearchRating(boolean searchRating) {
        this.searchRating = searchRating;
    }

}
