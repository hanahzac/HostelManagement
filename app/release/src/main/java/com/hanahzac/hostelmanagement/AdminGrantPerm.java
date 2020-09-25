package com.hanahzac.hostelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminGrantPerm extends AppCompatActivity {

    private Button grant, deny;
    private TextView name, reason, place, leave, leaveTime, rtrn, rtrnTime, adPerm, pPerm;
    String txt_name, txt_reason, txt_place, txt_leaveTime, txt_leave, txt_rtrn, txt_rtrnTime, txt_adPerm, txt_pPerm;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_grant_perm);
        final Permission perm = (Permission) getIntent().getSerializableExtra("permObj");

        grant = findViewById(R.id.grantButton);
        deny = findViewById(R.id.denyButton);
        name = findViewById(R.id.studNameText);
        place = findViewById(R.id.placeText);
        reason = findViewById(R.id.reasonText);
        leave = findViewById(R.id.leaveText);
        leaveTime = findViewById(R.id.leaveTimeText);
        rtrn = findViewById(R.id.rtrnText);
        rtrnTime = findViewById(R.id.rtrnTimeText);
        adPerm = findViewById(R.id.adminAppText);
        pPerm = findViewById(R.id.parentAppText);

        txt_name = perm.getName();
        txt_reason = perm.getReason();
        txt_place = perm.getPlace();
        txt_leaveTime = perm.getLeaveTime();
        txt_leave = perm.getLeave();
        txt_rtrn = perm.getRtrn();
        txt_rtrnTime = perm.getRtrnTime();
        txt_adPerm = perm.getAdPerm();
        txt_pPerm = perm.getpPerm();

        ref = FirebaseDatabase.getInstance().getReference("Permissions").child(txt_name).child(txt_leave);

        name.setText(txt_name);
        place.setText(txt_place);
        reason.setText(txt_reason);
        leave.setText(txt_leave);
        leaveTime.setText(txt_leaveTime);
        rtrn.setText(txt_rtrn);
        rtrnTime.setText(txt_rtrnTime);
        adPerm.setText(txt_adPerm);
        pPerm.setText(txt_pPerm);

        if (txt_adPerm.equals("Permission Granted!")) {
            adPerm.setTextColor(Color.parseColor("#092BEA"));
        }

        if (txt_pPerm.equals("Permission Granted!")) {
            pPerm.setTextColor(Color.parseColor("#092BEA"));
        }

        grant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child("adPerm").setValue("Permission Granted!");
                Toast.makeText(AdminGrantPerm.this, "Permission granted!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child("adPerm").setValue("Permission Not Approved!");
                Toast.makeText(AdminGrantPerm.this, "Permission denied!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}