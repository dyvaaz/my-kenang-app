package com.example.diffa.applicationcarorder;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diffa.applicationcarorder.General.Common;
import com.example.diffa.applicationcarorder.Model.Driver;
import com.example.diffa.applicationcarorder.Model.FCMResponse;
import com.example.diffa.applicationcarorder.Model.Notifications;
import com.example.diffa.applicationcarorder.Model.Token;
import com.example.diffa.applicationcarorder.Movement.Service;
import com.example.diffa.applicationcarorder.Movement.mGoogleAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustommerCall extends AppCompatActivity {
    TextView txtTime,txtAddress,txtDistance;
    Button btnCancel, btnAccept;

    MediaPlayer notificationPlayer;

    mGoogleAPI mService;
    Service mFCMService;
    String CustomerID;
    double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custommer_call);

        mService = Common.getGoogleAPI();
        mFCMService = Common.getFCMService();

        //InitView
        txtAddress = (TextView)findViewById(R.id.txtAddress);
        txtDistance = (TextView)findViewById(R.id.txtDistance);
        txtTime = (TextView)findViewById(R.id.txtTime);

        btnAccept =(Button)findViewById(R.id.btnAccept);
        btnCancel = (Button)findViewById(R.id.btnDecline);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(CustomerID))
                    cancelBooking(CustomerID);

            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustommerCall.this,DriverTracking.class);
                //send customer lcation to new activity
                intent.putExtra("Lat",lat);
                intent.putExtra("lng",lng);
                intent.putExtra("customerID",CustomerID);

                startActivity(intent);
                finish();
            }
        });

        notificationPlayer = MediaPlayer.create(this,R.raw.notification);
        notificationPlayer.setLooping(true);
        notificationPlayer.start();

        if(getIntent() != null){
            lat = getIntent().getDoubleExtra("lat",-1.0);
            lng = getIntent().getDoubleExtra("lng",-1.0);
            CustomerID = getIntent().getStringExtra("customer");
            getDirection(lat,lng);
        }
    }

    private void cancelBooking(String customerID) {
        Token token = new Token(customerID);

        Notifications notification = new Notifications("Nootice!", "Driver has cancelled your request");
        Driver driver = new Driver(token.getToken(),notification);

        mFCMService.sendMessage(driver)
                .enqueue(new Callback<FCMResponse>() {
                    @Override
                    public void onResponse(Call<FCMResponse> call, Response<FCMResponse> response) {
                        if(response.body().success == 1){
                            Toast.makeText(CustommerCall.this,"Cancelled",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<FCMResponse> call, Throwable t) {

                    }
                });

    }

    private void getDirection(double lat, double lng) {
        String requestAPI = null;
        try{
            requestAPI = "https://maps.googleapis.com/maps/api/directions/json?"+
                    "mode=driving&"+
                    "transit_routing_preference=less_driving"+
                    "origin="+ Common.mLastLocation.getLatitude()+","+Common.mLastLocation.getLongitude()+"&"+
                    "destination="+lat+","+lng+"&"+
                    "key="+getResources().getString(R.string.google_direction_api);
            Log.d("ME", requestAPI);
            mService.getPath(requestAPI)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response.body().toString());
                                JSONArray routes = jsonObject.getJSONArray("routes");

                                JSONObject object = routes.getJSONObject(0);
                                JSONArray legs = object.getJSONArray("legs");
                                JSONObject legsObject = legs.getJSONObject(0);
                                //get distance
                                JSONObject distance = legsObject.getJSONObject("distance");
                                txtDistance.setText(distance.getString("text"));
                                //get time
                                JSONObject time = legsObject.getJSONObject("duration");
                                txtTime.setText(time.getString("text"));
                                //get distance
                                String address = legsObject.getString("end_address");
                                txtAddress.setText(address);



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(CustommerCall.this,""+t.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        notificationPlayer.release();
        super.onStop();
    }

    @Override
    protected void onPause() {
        notificationPlayer.release();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        notificationPlayer.start();
    }
}
