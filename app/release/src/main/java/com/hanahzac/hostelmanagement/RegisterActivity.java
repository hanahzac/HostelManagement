package com.hanahzac.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button register;
    private EditText username;

    private DatabaseReference mRef;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);
        register = findViewById(R.id.registerButton);
        username = findViewById(R.id.usernameText);

        auth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Admin");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_pass = password.getText().toString();
                String txt_name = username.getText().toString();

                if (TextUtils.isEmpty(txt_email )|| TextUtils.isEmpty(txt_pass)){
                    Toast.makeText(RegisterActivity.this,"Empty credentials", Toast.LENGTH_SHORT).show();
                }
                else if (txt_pass.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                }
                else {
                    registerUser(txt_email, txt_pass, txt_name);
                }

            }
        });
    }

    private void registerUser(final String txt_email, String txt_pass, final String txt_name) {
        auth.createUserWithEmailAndPassword(txt_email, txt_pass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    admin info = new admin(txt_name, txt_email);
                    FirebaseDatabase.getInstance().getReference("Admin")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(RegisterActivity.this, "New admin registered successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}