package com.hanahzac.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SecurityLogin extends AppCompatActivity {

    private EditText email, pass;
    private ProgressBar progress;
    private Button login;
    private FirebaseAuth auth;
    private DatabaseReference ref;
    final ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_login);

        email = findViewById(R.id.usernameText);
        pass = findViewById(R.id.passwordText);
        progress = findViewById(R.id.progress);
        login = findViewById(R.id.loginButton);

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("Security");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setVisibility(View.VISIBLE);
                    }
                });
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    list.add(snapshot.child("email").getValue().toString());
                    progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_pass = pass.getText().toString();


                if (TextUtils.isEmpty(txt_email )|| TextUtils.isEmpty(txt_pass)){
                    Toast.makeText(SecurityLogin.this,"Empty credentials", Toast.LENGTH_SHORT).show();
                }


                if (list.contains(txt_email)){
                    signIn(txt_email, txt_pass);
                }
                else {
                    Toast.makeText(SecurityLogin.this, "Not authorized as security!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void signIn(String txt_email, String txt_pass) {

        auth.signInWithEmailAndPassword(txt_email, txt_pass)
                .addOnCompleteListener(SecurityLogin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SecurityLogin.this, "Login Successfull!!",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SecurityLogin.this, SecurityHome.class));
                            finish();

                        } else {
                            Toast.makeText(SecurityLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}