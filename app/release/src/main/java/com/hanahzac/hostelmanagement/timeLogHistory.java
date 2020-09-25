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

public class timeLogHistory extends AppCompatActivity {

    private ListView listView;
    private TextView banner;
    private DatabaseReference ref;
    public String date, idno, entry;
    ArrayList<String> times = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_log_history);

        listView = findViewById(R.id.listView);
        banner = findViewById(R.id.banner);

        date = getIntent().getExtras().getString("Date");
        idno = getIntent().getExtras().getString("ID");

        ref = FirebaseDatabase.getInstance().getReference("Log").child(idno).child(date);
        banner.setText(date);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                times.clear();
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    entry = snap.getKey() + " : " + snap.getValue().toString();
                    times.add(entry);
                }

                adapter = new ArrayAdapter<>(timeLogHistory.this, android.R.layout.simple_list_item_1, times);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}