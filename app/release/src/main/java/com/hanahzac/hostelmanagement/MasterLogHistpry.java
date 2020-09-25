package com.hanahzac.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MasterLogHistpry extends AppCompatActivity {

    private EditText searchBar;
    private ListView listView;
    ArrayList<String> dates = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_log_histpry);

        searchBar = findViewById(R.id.searchBar);
        listView = findViewById(R.id.listView);

        ref = FirebaseDatabase.getInstance().getReference("Master Log");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dates.clear();
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    dates.add(snap.getKey());
                }

                adapter = new ArrayAdapter<String>(MasterLogHistpry.this, android.R.layout.simple_list_item_1, dates);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (MasterLogHistpry.this).adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String date = dates.get(position);
                Intent intent = new Intent(MasterLogHistpry.this, MasterLogTime.class);
                intent.putExtra("Date", date);
                startActivity(intent);
            }
        });
    }
}