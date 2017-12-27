package com.example.diffa.applicationcarorder;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.example.diffa.applicationcarorder.General.Common;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;

public class TripDetails extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private TextView txtDate, txtFee, txtBaseFare, txtTime, txtDistance, txtEstimatedPay, txtFrom, txtTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txtBaseFare = (TextView)findViewById(R.id.txtBaseFare);
        txtDate = (TextView)findViewById(R.id.txtDate);
        txtFee = (TextView)findViewById(R.id.txtFee);
        txtTime = (TextView)findViewById(R.id.txtTime);
        txtDistance = (TextView)findViewById(R.id.txtDistance);
        txtEstimatedPay = (TextView)findViewById(R.id.txtEstimatedPay);
        txtFrom = (TextView)findViewById(R.id.txtFrom);
        txtTo = (TextView)findViewById(R.id.txtTo);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        settingDetails();
    }

    private void settingDetails() {
        if(getIntent() != null){
            //set text
            Calendar calendar = Calendar.getInstance();
            String date = String.format("%s, %d/%d", convertToDayOfweek(calendar.get(Calendar.DAY_OF_WEEK)),calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH));
            txtDate.setText(date);

            txtFee.setText(String.format("IDR %.2f", getIntent().getDoubleExtra("total", 0.0)));
            txtEstimatedPay.setText(String.format("IDR %.2f", getIntent().getDoubleExtra("total", 0.0)));
            txtBaseFare.setText(String.format("IDR %.2f", Common.base_fare));
            txtTime.setText(String.format("%s km",getIntent().getStringExtra("time")));
            txtDistance.setText(String.format("%s km", getIntent().getStringExtra("distance")));
            txtFrom.setText(getIntent().getStringExtra("start_address"));
            txtTo.setText(getIntent().getStringExtra("end_address"));

            //Add marker
            String[] location_end = getIntent().getStringExtra("location_end").split(",");
            LatLng dropOff = new LatLng(Double.parseDouble(location_end[0]),Double.parseDouble(location_end[1]));

            mMap.addMarker(new MarkerOptions().position(dropOff)
            .title("Drop Off here")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dropOff, 12.0f));

        }
    }

    private String convertToDayOfweek(int day) {
        switch (day){
            case Calendar.SUNDAY:
                return "SUNDAY";
            case Calendar.MONDAY:
                return "MONDAY";
            case Calendar.TUESDAY:
                return "TUSDAY";
            case Calendar.WEDNESDAY:
                return "WEDNESDAY";
            case Calendar.THURSDAY:
                return "THURSDAY";
            case Calendar.FRIDAY:
                return "FRIDAY";
            case Calendar.SATURDAY:
                return "SATURDAY";
                default:
                    return "UNK";
        }
    }
}
