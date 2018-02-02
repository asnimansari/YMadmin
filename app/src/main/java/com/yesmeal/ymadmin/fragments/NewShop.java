package com.yesmeal.ymadmin.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.yesmeal.ymadmin.CusUtils;
import com.yesmeal.ymadmin.MainActivity;
import com.yesmeal.ymadmin.R;
import com.yesmeal.ymadmin.Shop;

import java.util.UUID;

/**
 * Created by asnimansari on 05/01/18.
 */

public class NewShop extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1;
    private TextInputEditText latitudeTextView;
    private TextInputEditText longitudeTextView;

    private TextInputEditText shopName;
    private TextInputEditText addressTextView;
    private ImageButton placePicker;
//    private FloatingActionButton fabPickPlace;
    private RadioGroup radioGroup;
    private RadioButton serviceChargeYes;
    private RadioButton serviceChargeNo;
    private Button saveShop;

    double lat = 0;
    double lon = 0;
    String add = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_newshop);

        initViews();

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();


        placePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(NewShop.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });


        saveShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shop = shopName.getText().toString().trim();
                if (shop.length()>0) {
                    add = addressTextView.getText().toString();
                    lat = Double.parseDouble(latitudeTextView.getText().toString());
                    lon = Double.parseDouble(longitudeTextView.getText().toString());

                    boolean serviceCharge = false;

                    int radioButtonID = radioGroup.getCheckedRadioButtonId();
                    View radioButton = radioGroup.findViewById(radioButtonID);
                    int idx = radioGroup.indexOfChild(radioButton);

                    switch (idx) {
                        case 0:
                            serviceCharge = true;
                            break;
                        case 1:
                            serviceCharge = false;
                            break;
                    }

                    Shop shop1 = new Shop();
                    String uuid = UUID.randomUUID().toString();

                    shop1.setAddress(add);
                    shop1.setLatitude(lat);
                    shop1.setLongitude(lon);
        
                    shop1.setId(uuid);
                    shop1.setShopname(shop);
                    DatabaseReference databaseReference = CusUtils.getDatabase().getReference().child("shops");
                    databaseReference.child(uuid).setValue(shop1).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(NewShop.this, "Saved", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(NewShop.this,MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NewShop.this, "Failed To Add Shop", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                else {
                    Toast.makeText(NewShop.this, "Enter Shop Name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        latitudeTextView = (TextInputEditText) findViewById(R.id.latitudeTextView);
        longitudeTextView = (TextInputEditText) findViewById(R.id.longitudeTextView);
        placePicker = (ImageButton) findViewById(R.id.placePicker);
        shopName = (TextInputEditText) findViewById(R.id.shopName);
        addressTextView = (TextInputEditText) findViewById(R.id.addressTextView);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        serviceChargeYes = (RadioButton) findViewById(R.id.serviceCharegeYes);
        serviceChargeNo = (RadioButton) findViewById(R.id.serviceCharegeNo);
        saveShop = (Button) findViewById(R.id.saveShop);
        radioGroup.check(serviceChargeYes.getId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                StringBuilder stBuilder = new StringBuilder();
                String placename = String.format("%s", place.getName());
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
//                String longitude = String.valueOf(place.getLatLng().longitude);
                String address = String.format("%s", place.getAddress());
                latitudeTextView.setText(latitude);
               longitudeTextView.setText(longitude);
               shopName.setText(placename);

                addressTextView.setText(address);

            }
        }
    }

}