package com.hanahzac.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCode extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView ScannerView;
    int MY_PERMISSIONS_REQUEST_CAMERA=0;
    private String txt_result;
    private String date, time, entry;
    public String name;
    private DatabaseReference ref, uref, sref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ref = FirebaseDatabase.getInstance().getReference("Log");
        uref = FirebaseDatabase.getInstance().getReference("Master Log");
        sref = FirebaseDatabase.getInstance().getReference("Student");

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        date = dateFormat.format(cal.getTime());
        time = timeFormat.format(cal.getTime());

        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);
    }

    @Override
    public void handleResult(final Result result) {
        Intent intent = getIntent();

        if (intent.getStringExtra("Log").equals("IN")) {
            txt_result = result.getText() + " marked for in on " + date +" at " + time;
            ref.child(result.getText()).child("Status").setValue("IN");
            ref.child(result.getText()).child(date).child(time).setValue("IN");
            sref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snap: dataSnapshot.getChildren()) {
                        if (result.getText().equals(snap.child("idno").getValue().toString())) {
                            name = snap.getKey();
                            entry = name + ": IN";
                            uref.child(date).child(time).setValue(entry);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            txt_result = result.getText() + " marked for out on " + date +" at " + time;
            ref.child(result.getText()).child("Status").setValue("OUT");
            ref.child(result.getText()).child(date).child(time).setValue("OUT");
            sref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snap: dataSnapshot.getChildren()) {
                        if (result.getText().equals(snap.child("idno").getValue().toString())) {
                            name = snap.getKey();
                            entry = name + ": OUT";
                            uref.child(date).child(time).setValue(entry);

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
         SecurityHome.resultView.setText(txt_result);
         onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ScannerView.stopCamera();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ScanCode.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }
}