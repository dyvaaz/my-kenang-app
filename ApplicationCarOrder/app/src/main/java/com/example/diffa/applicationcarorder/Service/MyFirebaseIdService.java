package com.example.diffa.applicationcarorder.Service;

import com.example.diffa.applicationcarorder.General.Common;
import com.example.diffa.applicationcarorder.Model.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Diffa on 21/12/2017.
 */

public class MyFirebaseIdService extends FirebaseInstanceIdService{
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        updateTokenServer(refreshedToken);

    }

    private void updateTokenServer(String refreshedToken) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference token = db.getReference(Common.token_tbl);

        Token a = new Token(refreshedToken);
        if(FirebaseAuth.getInstance().getCurrentUser() !=null) // if login, update token
            token.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setValue(a);
    }
}
