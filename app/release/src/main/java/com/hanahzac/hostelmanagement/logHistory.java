package com.hanahzac.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class logHistory extends AppCompatActivity {

    private ListView listView;
    private DatabaseReference ref, sref;
    public String idno;
    ArrayList<String> dates = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_history);

        String name = getIntent().getExtras().getString("Name");

        listView = findViewById(R.id.listView);

        sref = FirebaseDatabase.getInstance().getReference("Student").child(name);

        sref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 idno = dataSnapshot.child("idno").getValue().toString();
                 ref = FirebaseDatabase.getInstance().getReference("Log").child(idno);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dates.clear();
                        for (DataSnapshot snap: dataSnapshot.getChildren()) {
                            if (snap.getKey().equals("Status")) {
                                continue;
                            } else {
                                dates.add(snap.getKey());
                            }
                        }

                        adapter = new ArrayAdapter<String>(logHistory.this, android.R.layout.simple_list_item_1,dates);
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String date = dates.get(position);
                Intent intent = new Intent(logHistory.this, timeLogHistory.class);
                intent.putExtra("Date", date);
                intent.putExtra("ID", idno);
                startActivity(intent);
            }
        });

    }
}