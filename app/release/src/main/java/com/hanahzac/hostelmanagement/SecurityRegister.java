package com.hanahzac.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SecurityRegister extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button register;
    private EditText username;

    private DatabaseReference mRef;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_register);

        email = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);
        register = findViewById(R.id.registerButton);
        username = findViewById(R.id.usernameText);

        auth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Security");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_pass = password.getText().toString();
                String txt_name = username.getText().toString();

                if (TextUtils.isEmpty(txt_email )|| TextUtils.isEmpty(txt_pass)){
                    Toast.makeText(SecurityRegister.this,"Empty credentials", Toast.LENGTH_SHORT).show();
                }
                else if (txt_pass.length() < 6){
                    Toast.makeText(SecurityRegister.this, "Password too short", Toast.LENGTH_SHORT).show();
                }
                else {
                    registerUser(txt_email, txt_pass, txt_name);
                }

            }
        });
    }

    private void registerUser(final String txt_email, String txt_pass, final String txt_name) {
        auth.createUserWithEmailAndPassword(txt_email, txt_pass).addOnCompleteListener(SecurityRegister.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Security info = new Security(txt_name, txt_email);
                    FirebaseDatabase.getInstance().getReference("Security")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(SecurityRegister.this, "New security registered successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
                else {
                    Toast.makeText(SecurityRegister.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}