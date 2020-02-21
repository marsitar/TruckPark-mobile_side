package com.example.truckpark.localdatamanagment;

import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.repository.CurrentMops;

import java.util.List;

public class MopsDataManagement implements DataGetter<List<Mop>>, DataSaver<List<Mop>> {

    @Override
    public List<Mop> getData() {
        return CurrentMops.getCurrentMopsInstance().getCurrentMopsList();
    }

    @Override
    public void save(List<Mop> mops) {
        CurrentMops.getCurrentMopsInstance().setCurrentMopsList(mops);
    }
}
