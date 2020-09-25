package com.hanahzac.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SecurityHome extends AppCompatActivity {

    private Button usersignOut, in, out;
    private TextView username;
    private ProgressBar progress;
    public static TextView resultView;

    private FirebaseAuth auth;
    private String user;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_home);

        username = findViewById(R.id.usernameTextSec);
        resultView = findViewById(R.id.resultView);
        in = findViewById(R.id.inButton);
        out = findViewById(R.id.outButton);
        usersignOut = findViewById(R.id.signoutButtonSec);
        progress = findViewById(R.id.progressSec);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference("Security").child(user).child("fullName");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                progress.setVisibility(View.GONE);
                username.setText("Hi " + name + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String log = "IN";
                Intent intent = new Intent(SecurityHome.this, ScanCode.class);
                intent.putExtra("Log", log);
                startActivity(intent);
            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String log = "OUT";
                Intent intent = new Intent(SecurityHome.this, ScanCode.class);
                intent.putExtra("Log", log);
                startActivity(intent);
            }
        });

        usersignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getInstance().signOut();
                Intent intent = new Intent(SecurityHome.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}