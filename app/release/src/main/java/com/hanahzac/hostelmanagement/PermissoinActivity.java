package com.hanahzac.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class PermissoinActivity extends AppCompatActivity {

    private EditText reason, leave, leaveTime, rtrn, rtrnTime, place;
    private Button submit;
    int lHour, lMin, rHour, rMin;
    private DatabaseReference ref;
    private FirebaseAuth auth;
    String  txt_leave, txt_leaveTime, txt_rtrn, txt_rtrnTime, txt_reason, txt_place, txt_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissoin);

        reason = findViewById(R.id.reasonText);
        place = findViewById(R.id.placeText);
        leave = findViewById(R.id.leaveText);
        leaveTime = findViewById(R.id.leaveTimeText);
        rtrn = findViewById(R.id.returnText);
        rtrnTime = findViewById(R.id.returnTimeText);
        submit = findViewById(R.id.submitButton);

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("Permissions");

        final Calendar calender = Calendar.getInstance();
        final int year = calender.get(Calendar.YEAR);
        final int month = calender.get(Calendar.MONTH);
        final int day = calender.get(Calendar.DAY_OF_MONTH);

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        PermissoinActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        txt_leave = day + "-" + month + "-" + year;
                        leave.setText(txt_leave);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        leaveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        PermissoinActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        lHour = hourOfDay;
                        lMin = minute;
                        calender.set(0,0,0, lHour, lMin);
                        leaveTime.setText(DateFormat.format("hh:mm aa", calender));
                        txt_leaveTime = leaveTime.getText().toString();
                    }
                }, 12, 0, false);
                timePickerDialog.updateTime(lHour, lMin);
                timePickerDialog.show();
            }
        });

        rtrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        PermissoinActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        txt_rtrn = day + "-" + month + "-" + year;
                        rtrn.setText(txt_rtrn);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        rtrnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        PermissoinActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        rHour = hourOfDay;
                        rMin = minute;
                        calender.set(0,0,0, rHour, rMin);
                        rtrnTime.setText(DateFormat.format("hh:mm aa", calender));
                        txt_rtrnTime = rtrnTime.getText().toString();
                    }
                }, 12, 0, false);
                timePickerDialog.updateTime(lHour, lMin);
                timePickerDialog.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt_name = getIntent().getExtras().getString("Name");
                txt_reason = reason.getText().toString();
                txt_place = place.getText().toString();

                if (TextUtils.isEmpty(txt_place) || TextUtils.isEmpty(txt_reason)) {
                    Toast.makeText(PermissoinActivity.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    Permission perm = new Permission("Waiting approval", txt_leave,  txt_leaveTime, txt_name, "Waiting approval",  txt_place, txt_reason, txt_rtrn, txt_rtrnTime);
                    ref.child(txt_name).child(txt_leave).setValue(perm).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(PermissoinActivity.this, "Request submitted successfully!!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(PermissoinActivity.this, "Failed to submit!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }
}