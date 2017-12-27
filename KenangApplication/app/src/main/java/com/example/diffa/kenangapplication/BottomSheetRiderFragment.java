package com.example.diffa.kenangapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diffa.kenangapplication.General.Common;
import com.example.diffa.kenangapplication.Movement.IGoogleAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Diffa on 21/12/2017.
 */

public class BottomSheetRiderFragment extends BottomSheetDialogFragment {
    String mLocation, mDestination;

    boolean isTapOnMap;

    IGoogleAPI mService;
    TextView txtLocation,txtDestination,txtBill;

    public static BottomSheetRiderFragment newInstance(String location, String destination, boolean isTapOnMap){
        BottomSheetRiderFragment a = new BottomSheetRiderFragment();
        Bundle args = new Bundle();
        args.putString("location", location);
        args.putString("destination", destination);
        args.putBoolean("isTapOnMap", isTapOnMap);
        a.setArguments(args);
        return a;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocation = getArguments().getString("location");
        mDestination = getArguments().getString("destination");
        isTapOnMap = getArguments().getBoolean("isTapOnMap");

       // mTag = getArguments().getString("TAG");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.button_for_rider, container, false);
        txtLocation = (TextView) view.findViewById(R.id.txtLocation);
        txtDestination = (TextView) view.findViewById(R.id.txtDestination);
        txtBill = (TextView) view.findViewById(R.id.txtBill);

        //Get price
        mService = Common.getGoogleService();
        getPrice(mLocation, mDestination);

        //Get data
        if(!isTapOnMap){
            //Call this fragmen from Place autocomplete text view
        txtLocation.setText(mLocation);
        txtDestination.setText(mDestination);
        }
        return view;
    }

    private void getPrice(String mLocation, String mDestination) {
        String requestUrl= null;
        try{
            requestUrl = "https://maps.googleapis.com/maps/api/direction/json?"+
                    "mode=driving&"
                    +"transit_routing_preference=less_driving&"
                    +"origin="+mLocation+"&"
                    +"destination="+mDestination+"&"
                    +"key="+getResources().getString(R.string.google_browser_key);
            Log.e("LINK", requestUrl); // print for debug
            mService.getPath(requestUrl).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    //Get Object
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        JSONArray routes = jsonObject.getJSONArray("routes");

                        JSONObject object = routes.getJSONObject(0);
                        JSONArray legs = object.getJSONArray("legs");

                        JSONObject legsObject = legs.getJSONObject(0);
                        //Get distance
                        JSONObject distance = legsObject.getJSONObject("distance");
                        String distance_txt = distance.getString("text");
                        //regex to extract double from string
                        Double distance_value = Double.parseDouble(distance_txt.replaceAll("[^0-9\\\\.]+", ""));

                        //Get time
                        JSONObject time = legsObject.getJSONObject("duration");
                        String time_txt = time.getString("text");
                        Integer time_value = Integer.parseInt(time_txt.replaceAll("\\D+", ""));

                        String final_bill = String.format("%s + %s = $%.2f", distance_txt, time_txt,
                                Common.getPrice(distance_value, time_value));

                        txtBill.setText(final_bill);

                        if(isTapOnMap){
                            String start_address = legsObject.getString("start_address");
                            String end_address = legsObject.getString("end_address");

                            txtLocation.setText(start_address);
                            txtDestination.setText(end_address);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("ERROR",t.getMessage());

                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

