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

public class ParentLogin extends AppCompatActivity {

    private EditText fullname, email, password;
    private Button login;
    private ProgressBar progress;

    private FirebaseAuth auth;
    private DatabaseReference ref;
    final ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_login);

        fullname = findViewById(R.id.nameText);
        email = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);
        login = findViewById(R.id.loginButton);
        progress = findViewById(R.id.progress);


        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("Student");

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
                    list.add(snapshot.child("paremail").getValue().toString());
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
                String txt_fullname = fullname.getText().toString();
                String txt_email = email.getText().toString();
                String txt_pass = password.getText().toString();


                if (TextUtils.isEmpty(txt_fullname )|| TextUtils.isEmpty(txt_pass) || TextUtils.isEmpty(txt_email)){
                    Toast.makeText(ParentLogin.this,"Empty credentials", Toast.LENGTH_SHORT).show();
                }


                if (list.contains(txt_email)){
                    signIn(txt_email, txt_pass);
                }
                else {
                    Toast.makeText(ParentLogin.this, "Not registered as parent!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void signIn(final String txt_email, final String txt_pass) {

        auth.signInWithEmailAndPassword(txt_email, txt_pass)
                .addOnCompleteListener(ParentLogin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ParentLogin.this, "Login Successfull!!",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ParentLogin.this, ParentHome.class);
                            startActivity(intent);
                            finish();

                        } else {
                            auth.createUserWithEmailAndPassword(txt_email, txt_pass)
                                    .addOnCompleteListener(ParentLogin.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(ParentLogin.this, "Login Successfull!!",
                                                        Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(ParentLogin.this, ParentHome.class));
                                                finish();
                                            }
                                            else {
                                                Toast.makeText(ParentLogin.this, "Login FAILED!!",
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                        }
                    }
                });
    }
}