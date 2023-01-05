package com.example.exchangeme;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.util.HashMap;

public class Extraadapter implements AdapterView.OnItemSelectedListener {
    private HashMap<String, Float> hashMap;

    public Extraadapter(HashMap<String, Float> hashMap) {
        this.hashMap = hashMap;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        switch (position){
            case 0:
                Log.v("item",(String) adapterView.getItemAtPosition(position));
                Float gbp = hashMap.get("GBP");
                break;

            case 1:
                Float usd = hashMap.get("USD");
                break;
            case 2:
                Float tRy = hashMap.get("TRY");
                break;
            case 3:
                Float eur = hashMap.get("EUR");
                break;
            case 4:
                Float chf = hashMap.get("CHF");
                break;

        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
