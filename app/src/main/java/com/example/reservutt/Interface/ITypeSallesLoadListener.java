package com.example.reservutt.Interface;

import com.example.reservutt.Models.TypeSalle;

import java.util.List;

public interface ITypeSallesLoadListener {
    void onTypeSalleLoadSuccess(List<TypeSalle> typeSalles);
    void onTypeSalleLoadFailed(String message);
}
