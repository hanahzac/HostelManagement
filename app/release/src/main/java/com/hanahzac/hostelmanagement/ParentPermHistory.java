package com.hanahzac.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.shapes.OvalShape;
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

public class ParentPermHistory extends AppCompatActivity {

    String name;
    private ProgressBar progress;
    private DatabaseReference ref, kref, permref;
    private ListView listView;
    int flag;
    ArrayList<String> kids = new ArrayList<>();
    ArrayList<Permission> permList = new ArrayList<Permission>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_perm_history);

        listView = findViewById(R.id.listViewPerm);
        progress = findViewById(R.id.progress);
        name = getIntent().getExtras().getString("ParentName");
        ref = FirebaseDatabase.getInstance().getReference("Parent").child(name);
        permref = FirebaseDatabase.getInstance().getReference("Permissions");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    if (snapshot.getKey().equals("paremail")) {
                        continue;
                    }
                    else {
                        kids.add(snapshot.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        permref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (String kid: kids) {
                    if (dataSnapshot.hasChild(kid)) {
                        for (DataSnapshot snap: dataSnapshot.child(kid).getChildren()) {
                            if (snap.child("pPerm").getValue().equals("Waiting approval")) {
                                continue;
                            }
                            else {
                                Permission perm = snap.getValue(Permission.class);
                                for (Permission permission: permList) {
                                    if (permission.getName().equals(perm.getName()) && permission.getReason().equals(perm.getReason())
                                            && permission.getPlace().equals(perm.getPlace()) && permission.getLeave().equals(perm.getLeave()))
                                    {
                                        flag = 1;
                                    }
                                }
                                if (flag == 0) {
                                    permList.add(perm);
                                }
                            }
                            PermissionListAdapter adapterP = new PermissionListAdapter(ParentPermHistory.this, R.layout.perm_row, permList);
                            listView.setAdapter(adapterP);
                            progress.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}