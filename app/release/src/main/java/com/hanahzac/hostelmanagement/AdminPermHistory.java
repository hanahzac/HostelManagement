package com.hanahzac.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminPermHistory extends AppCompatActivity {

    String name;
    private ProgressBar progress;
    private DatabaseReference permref;
    private ListView listView;
    ArrayList<Permission> permList = new ArrayList<Permission>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_perm_history);

        listView = findViewById(R.id.listViewPerm);
        progress = findViewById(R.id.progress);
        name = getIntent().getExtras().getString("name");
        permref = FirebaseDatabase.getInstance().getReference("Permissions").child(name);

        permref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                permList.clear();
                for (DataSnapshot snap:dataSnapshot.getChildren()) {
                    Permission perm = snap.getValue(Permission.class);
                    permList.add(perm);
                }
                PermissionListAdapter adapterP = new PermissionListAdapter(AdminPermHistory.this, R.layout.perm_row, permList);
                listView.setAdapter(adapterP);
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}