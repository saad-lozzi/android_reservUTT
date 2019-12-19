package com.example.reservutt.Interface;

import com.example.reservutt.Models.BookingInformation;
import com.example.reservutt.Models.Salle;

import java.util.List;

public interface IBookingLoadListener {
    void onBookingLoadSuccess(List<BookingInformation> bookingList);
    void onBookingLoadFailed(String message);
}
