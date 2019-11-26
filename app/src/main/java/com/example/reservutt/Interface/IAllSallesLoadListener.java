package com.example.reservutt.Interface;

import java.util.List;

public interface IAllSallesLoadListener {
    void onAllSalleLoadSuccess(List<String> depNameList);
    void onAllSalleLoadFailed(String message);
}
