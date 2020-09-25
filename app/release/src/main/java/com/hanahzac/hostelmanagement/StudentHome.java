package com.hanahzac.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class StudentHome extends AppCompatActivity {

    private Button signOut, permButton, logButton;
    private TextView titleText;
    private ListView listView;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference ref, permref;
    String name = "";
    ArrayList<Permission> permList = new ArrayList<Permission>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        signOut = findViewById(R.id.signoutButton);
        permButton = findViewById(R.id.permButton);
        logButton = findViewById(R.id.logButton);
        titleText = findViewById(R.id.titleText);
        listView = findViewById(R.id.listView);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        final String email = user.getEmail();
        ref = FirebaseDatabase.getInstance().getReference("Student");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    if (snap.child("email").getValue().toString().equals(email)) {
                        name = snap.getKey();
                        titleText.setText("Hi "+name+"!");
                        permref = FirebaseDatabase.getInstance().getReference("Permissions").child(name);
                        permref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                permList.clear();
                                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                                    Permission perm = snap.getValue(Permission.class);
                                    permList.add(perm);
                                }
                                PermissionListAdapter adapter = new PermissionListAdapter(StudentHome.this, R.layout.perm_row, permList);
                                listView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        permButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHome.this, PermissoinActivity.class);
                intent.putExtra("Name", name);
                startActivity(intent);
            }
        });

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHome.this, logHistory.class);
                intent.putExtra("Name", name);
                startActivity(intent);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getInstance().signOut();
                Intent intent = new Intent(StudentHome.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }
}