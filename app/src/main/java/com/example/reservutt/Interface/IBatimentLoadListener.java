package com.example.reservutt.Interface;

import com.example.reservutt.Models.Salle;

import java.util.List;

public interface IBatimentLoadListener {
    void onBatimentLoadSuccess(List<Salle> salleList);
    void onBatimentLoadFailed(String message);
}
