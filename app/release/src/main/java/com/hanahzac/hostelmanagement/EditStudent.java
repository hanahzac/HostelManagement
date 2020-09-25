package com.hanahzac.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditStudent extends AppCompatActivity {

    private EditText name, branch, year, dob, cno, fname, fcno, mname, mcno, add, email, paremail, idno;
    private Button register;
    private RadioButton male, female;
    private DatabaseReference ref, pref, sref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        String txt_name = getIntent().getExtras().getString("Name");

        name = findViewById(R.id.fullnameText);
        branch = findViewById(R.id.branchText);
        idno = findViewById(R.id.idText);
        year = findViewById(R.id.yearText);
        dob = findViewById(R.id.dobText);
        cno = findViewById(R.id.contacttext);
        fname = findViewById(R.id.fathertext);
        fcno = findViewById(R.id.fathercon);
        mname = findViewById(R.id.mothertext);
        mcno = findViewById(R.id.mothercon);
        paremail = findViewById(R.id.paremail);
        add = findViewById(R.id.address);
        email = findViewById(R.id.emailText);
        male = findViewById(R.id.genderM);
        female = findViewById(R.id.genderF);
        register = findViewById(R.id.registerButton);

        ref = FirebaseDatabase.getInstance().getReference("Student");
        sref = FirebaseDatabase.getInstance().getReference("Student").child(txt_name);
        pref = FirebaseDatabase.getInstance().getReference("Parent");

        sref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String txt_name = dataSnapshot.child("name").getValue().toString();
                String txt_branch = dataSnapshot.child("branch").getValue().toString();
                String txt_id = dataSnapshot.child("idno").getValue().toString();
                String txt_year = dataSnapshot.child("year").getValue().toString();
                String txt_dob = dataSnapshot.child("dob").getValue().toString();
                String txt_cno = dataSnapshot.child("cno").getValue().toString();
                String txt_fname = dataSnapshot.child("fname").getValue().toString();
                String txt_fcno = dataSnapshot.child("fcno").getValue().toString();
                String txt_mname = dataSnapshot.child("mname").getValue().toString();
                String txt_mcno = dataSnapshot.child("mcno").getValue().toString();
                String txt_paremail = dataSnapshot.child("paremail").getValue().toString();
                String txt_add = dataSnapshot.child("add").getValue().toString();
                String txt_email = dataSnapshot.child("email").getValue().toString();
                String txt_gender = dataSnapshot.child("gender").getValue().toString();
                name.setText(txt_name);
                branch.setText(txt_branch);
                idno.setText(txt_id);
                year.setText(txt_year);
                dob.setText(txt_dob);
                cno.setText(txt_cno);
                fname.setText(txt_fname);
                fcno.setText(txt_fcno);
                mname.setText(txt_mname);
                mcno.setText(txt_mcno);
                paremail.setText(txt_paremail);
                add.setText(txt_add);
                email.setText(txt_email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_gender = "";
                final String txt_name = name.getText().toString();
                String txt_branch = branch.getText().toString();
                String txt_id = idno.getText().toString();
                String txt_year = year.getText().toString();
                String txt_dob = dob.getText().toString();
                String txt_cno = cno.getText().toString();
                final String txt_fname = fname.getText().toString();
                String txt_fcno = fcno.getText().toString();
                String txt_mname = mname.getText().toString();
                String txt_mcno = mcno.getText().toString();
                final String txt_paremail = paremail.getText().toString();
                String txt_add = add.getText().toString();
                final String txt_email = email.getText().toString();

                if (male.isChecked()){
                    txt_gender = "Male";
                }
                if (female.isChecked()) {
                    txt_gender = "Female";
                }

                if (TextUtils.isEmpty(txt_email )|| TextUtils.isEmpty(txt_branch) || TextUtils.isEmpty(txt_paremail)
                        || TextUtils.isEmpty(txt_year)|| TextUtils.isEmpty(txt_add)
                        || TextUtils.isEmpty(txt_gender)|| TextUtils.isEmpty(txt_name)
                        || TextUtils.isEmpty(txt_dob)|| TextUtils.isEmpty(txt_cno)
                        || TextUtils.isEmpty(txt_fname)|| TextUtils.isEmpty(txt_fcno)
                        || TextUtils.isEmpty(txt_mname)|| TextUtils.isEmpty(txt_mcno)){
                    Toast.makeText(EditStudent.this,"Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Student info = new Student(txt_name, txt_branch, txt_year, txt_dob, txt_gender, txt_cno, txt_fname, txt_fcno, txt_mname, txt_mcno, txt_paremail, txt_add, txt_email, txt_id);
                    ref.child(txt_name).setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            pref.child(txt_fname).child("paremail").setValue(txt_paremail);
                            pref.child(txt_fname).child(txt_name).setValue(txt_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(EditStudent.this, "Student details changed successfully!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(EditStudent.this, StudentList.class));
                                    finish();
                                }
                            });

                        }
                    });
                }

            }
        });
    }
}