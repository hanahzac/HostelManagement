package com.hanahzac.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MasterLogTime extends AppCompatActivity {

    private ListView listView;
    private TextView banner;
    private DatabaseReference ref;
    public String date, entry;
    ArrayList<String> times = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_log_time);

        listView = findViewById(R.id.listView);
        banner = findViewById(R.id.banner);

        date = getIntent().getExtras().getString("Date");

        ref = FirebaseDatabase.getInstance().getReference("Master Log").child(date);

        banner.setText(date);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                times.clear();
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    entry = snap.getKey() + " : " + snap.getValue().toString();
                    times.add(entry);
                }

                adapter = new ArrayAdapter<>(MasterLogTime.this, android.R.layout.simple_list_item_1, times);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}