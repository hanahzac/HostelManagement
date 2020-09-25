package com.hanahzac.hostelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT = "com.hanahzac.hostelmanagement.EXTRA_TEXT";

    private Button loginAdmin;
    private Button loginParent;
    private Button loginStudent;
    private Button loginSecurity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginAdmin = findViewById(R.id.adminLoginButton);
        loginParent = findViewById(R.id.parentLoginButton);
        loginStudent = findViewById(R.id.studentLoginButton);
        loginSecurity = findViewById(R.id.securityLoginButton);

        loginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , LoginActivity.class));
            }
        });

        loginParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ParentLogin.class));
            }
        });

        loginStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StudentLogin.class));
            }
        });

        loginSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecurityLogin.class));
            }
        });

    }
}