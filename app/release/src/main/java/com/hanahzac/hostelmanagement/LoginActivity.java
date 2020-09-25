package com.hanahzac.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private ProgressBar progress;

    private FirebaseAuth auth;
    private DatabaseReference ref;
    final ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.usernameText);
        password = findViewById(R.id.passwordText);
        login = findViewById(R.id.loginButton);
        progress = findViewById(R.id.progress);

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("Admin");

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
                String txt_username = username.getText().toString();
                String txt_pass = password.getText().toString();


                if (TextUtils.isEmpty(txt_username )|| TextUtils.isEmpty(txt_pass)){
                    Toast.makeText(LoginActivity.this,"Empty credentials", Toast.LENGTH_SHORT).show();
                }


                if (list.contains(txt_username)){
                    signIn(txt_username, txt_pass);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Not authorized as admin!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void signIn(String txt_username, String txt_pass) {

        auth.signInWithEmailAndPassword(txt_username, txt_pass)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login Successfull!!",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, AdminHome.class));
                            finish();

                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
