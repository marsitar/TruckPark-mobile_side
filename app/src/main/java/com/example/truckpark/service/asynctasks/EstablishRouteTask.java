package com.example.truckpark.service.asynctasks;

import android.os.AsyncTask;

import android.app.Activity;
import android.widget.Toast;

public class EstablishRouteTask extends AsyncTask<Void, Void, Void> {

    Activity currentActivity;

    public EstablishRouteTask(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @Override
    protected void onPreExecute() {
//        wywolujaceActivity.showDialog(MainActivity.PLEASE_WAIT_DIALOG);
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(Void result) {
//        wywolujaceActivity.removeDialog(MainActivity.PLEASE_WAIT_DIALOG);
        Toast.makeText(currentActivity, "Trasa wyznaczona!", Toast.LENGTH_SHORT).show();
    }

}
