package com.example.truckpark.repository;

import com.example.truckpark.domain.json.mopapi.Mop;

import java.util.ArrayList;
import java.util.List;

public final class CurrentMops {

    private static volatile CurrentMops CURRENT_MOPS;
    private volatile List<Mop> CurrentMopsList = new ArrayList<>();

    private CurrentMops() {
    }

    public static CurrentMops getCurrentMops() {
        if (CURRENT_MOPS == null) {
            synchronized (CurrentMops.class) {
                if (CURRENT_MOPS == null) {
                    CURRENT_MOPS = new CurrentMops();
                }
            }
        }
        return CURRENT_MOPS;
    }

    public synchronized List<Mop> getCurrentMopsList() {
        return CurrentMopsList;
    }

    public synchronized void setCurrentMopsList(List<Mop> currentMopsList) {
        CurrentMopsList = currentMopsList;
    }

}
