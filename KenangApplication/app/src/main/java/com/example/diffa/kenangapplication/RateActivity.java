package com.example.diffa.kenangapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.diffa.kenangapplication.General.Common;
import com.example.diffa.kenangapplication.Model.Rates;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RateActivity extends AppCompatActivity {

    Button btnSubmit;
    MaterialRatingBar ratingBar;
    MaterialEditText editComment;

    FirebaseDatabase database;
    DatabaseReference rateDetailRef;
    DatabaseReference driverInfoRef;

    double ratingStars = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        //Init Firebase
        database = FirebaseDatabase.getInstance();
        rateDetailRef = database.getReference(Common.rate_detail_tbl);
        driverInfoRef = database.getReference(Common.user_driver_tbl);

        //Init View
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        ratingBar = (MaterialRatingBar)findViewById(R.id.ratingBus);
        editComment = (MaterialEditText)findViewById(R.id.editComment);

        //Event
        ratingBar.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                ratingStars = rating;
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRateDetails(Common.driverId);
            }
        });
    }
    private void submitRateDetails(final String driverId){
        final SpotsDialog alertDialog = new SpotsDialog(this);
        alertDialog.show();

        Rates rate = new Rates();
        rate.setRate(String.valueOf(ratingStars));
        rate.setComment(editComment.getText().toString());

        //Update new value to firebase
        rateDetailRef.child(driverId)
               // .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .push() //unique key
                .setValue(rate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //If upload succesed on Firebase, calculate average to driver info
                        rateDetailRef.child(driverId)
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        double averageStars = 0.0;
                                        int count = 0;
                                        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                                            Rates rate = postSnapshot.getValue(Rates.class);
                                            averageStars+= Double.parseDouble(rate.getRate());
                                            count++;
                                        }
                                        double finalAverage = averageStars/count;
                                        DecimalFormat df = new DecimalFormat("#.#");
                                        String valueUpdate = df.format(finalAverage);

                                        //Create objact update
                                        Map<String,Object> driverUpdateRate = new HashMap<>();
                                        driverUpdateRate.put("rates", valueUpdate);

                                        driverInfoRef.child(Common.driverId)
                                                .updateChildren(driverUpdateRate)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        alertDialog.dismiss();
                                                        Toast.makeText(RateActivity.this,"Thank you for Submitting", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        alertDialog.dismiss();
                                                        Toast.makeText(RateActivity.this,"Rate updated but can't write to Driver Information",Toast.LENGTH_SHORT).show();

                                                    }
                                                });

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        alertDialog.dismiss();
                        Toast.makeText(RateActivity.this,"Rate Failed !", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
