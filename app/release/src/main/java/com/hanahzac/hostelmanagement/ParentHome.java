package com.hanahzac.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ParentHome extends AppCompatActivity {

    private Button signOut, permHistory;
    private ProgressBar progress;
    private TextView titleText;
    private ListView listView;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference ref, permref;
    String name;
    int flag=0;
    ArrayList<String> kids = new ArrayList<>();
    ArrayList<Permission> permList = new ArrayList<Permission>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home);

        signOut = findViewById(R.id.signoutButton);
        progress = findViewById(R.id.progress);
        titleText = findViewById(R.id.titleText);
        listView = findViewById(R.id.listView);
        permHistory = findViewById(R.id.permHistoryButton);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        final String email = user.getEmail();

        ref = FirebaseDatabase.getInstance().getReference("Parent");
        permref = FirebaseDatabase.getInstance().getReference("Permissions");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kids.clear();
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    if (snap.child("paremail").getValue().toString().equals(email)) {
                        name = snap.getKey();
                        break;
                    }
                }

                titleText.setText("Hi "+name+"!");

                permHistory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ParentHome.this, ParentPermHistory.class);
                        intent.putExtra("ParentName", name);
                        startActivity(intent);
                    }
                });

                for (DataSnapshot snapshot: dataSnapshot.child(name).getChildren()) {
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
                ParentPermListAdapter adapter = new ParentPermListAdapter(ParentHome.this, R.layout.perm_row_admin, permList);
                listView.setAdapter(adapter);
                permList.clear();
                for (String kid: kids) {
                    if (dataSnapshot.hasChild(kid)) {
                        for (DataSnapshot snap: dataSnapshot.child(kid).getChildren()) {
                            if (snap.child("pPerm").getValue().equals("Waiting approval")) {
                                flag = 0;
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
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                }
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Permission perm = permList.get(position);
                Intent intent = new Intent(ParentHome.this, ParentGrantPerm.class);
                intent.putExtra("permObj", perm);
                startActivity(intent);
            }
        });


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getInstance().signOut();
                Intent intent = new Intent(ParentHome.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}