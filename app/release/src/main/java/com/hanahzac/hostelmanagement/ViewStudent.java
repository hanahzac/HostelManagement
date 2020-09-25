package com.hanahzac.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewStudent extends AppCompatActivity {

    private TextView name, branch, year, dob, gender, cno, fname, fcno, mname, mcno, paremail, add, email, idno;
    private Button permissions, delete, log, edit;

    private DatabaseReference ref, pref, perref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        final Student student = (Student) getIntent().getSerializableExtra("studObj");

        name = findViewById(R.id.name);
        branch = findViewById(R.id.branch);
        idno = findViewById(R.id.idno);
        year = findViewById(R.id.year);
        dob = findViewById(R.id.dob);
        cno = findViewById(R.id.cno);
        fname = findViewById(R.id.fathername);
        fcno = findViewById(R.id.fcno);
        mname = findViewById(R.id.mothername);
        mcno = findViewById(R.id.mcno);
        paremail = findViewById(R.id.paremail);
        add = findViewById(R.id.address);
        email = findViewById(R.id.email);
        gender = findViewById(R.id.gender);

        permissions = findViewById(R.id.permissionsButton);
        delete = findViewById(R.id.deleteButton);
        log = findViewById(R.id.logButton);
        edit = findViewById(R.id.editButton);

        ref = FirebaseDatabase.getInstance().getReference("Student").child(student.getName());
        pref = FirebaseDatabase.getInstance().getReference("Parent").child(student.getFname()).child(student.getName());
        perref = FirebaseDatabase.getInstance().getReference("Permissions").child(student.getName());

        ref.addValueEventListener(new ValueEventListener() {
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
                gender.setText(txt_gender);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref.removeValue();
                pref.removeValue();
                perref.removeValue();
                Toast.makeText(ViewStudent.this, "Student removed successfully!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ViewStudent.this, StudentList.class));

            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewStudent.this, logHistory.class);
                intent.putExtra("Name", student.getName());
                startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewStudent.this, EditStudent.class);
                intent.putExtra("Name", student.getName());
                startActivity(intent);
            }
        });

        permissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewStudent.this, AdminPermHistory.class);
                intent.putExtra("name", student.getName());
                startActivity(intent);
            }
        });

    }
}