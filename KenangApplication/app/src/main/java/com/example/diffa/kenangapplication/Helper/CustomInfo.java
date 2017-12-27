package com.example.diffa.kenangapplication.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.diffa.kenangapplication.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Diffa on 21/12/2017.
 */

public class CustomInfo implements GoogleMap.InfoWindowAdapter {
    View myView;

    public CustomInfo(Context context){
        myView = LayoutInflater.from(context)
                .inflate(R.layout.custom_rider_info, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        TextView txtPickupTitle = ((TextView)myView.findViewById(R.id.txtPickupInfo));
        txtPickupTitle.setText(marker.getTitle());

        TextView txtPickupSnippet = ((TextView)myView.findViewById(R.id.txtPickupSnippet));
        txtPickupSnippet.setText(marker.getSnippet());

        return myView;


    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
