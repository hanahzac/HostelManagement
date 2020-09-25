package com.hanahzac.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminHome extends AppCompatActivity {

    private Button adminRegister, studentManage, usersignOut, securityRegister, logHistory;
    private TextView username;
    private ProgressBar progress;
    private ListView listView;
    private FirebaseAuth auth;
    private String user;
    int flag=0;
    private DatabaseReference ref, permref;
    ArrayList<Permission> permList = new ArrayList<Permission>();
    ArrayList<String> kids = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        adminRegister = findViewById(R.id.adminRegisterButton);
        securityRegister = findViewById(R.id.securityRegisterButton);
        logHistory = findViewById(R.id.dailyLogButton);
        studentManage = findViewById(R.id.studentManageButton);
        username = findViewById(R.id.usernameText);
        usersignOut = findViewById(R.id.signoutButton);
        progress = findViewById(R.id.progress);
        listView = findViewById(R.id.listView);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference("Admin").child(user).child("fullName");
        permref = FirebaseDatabase.getInstance().getReference("Permissions");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                progress.setVisibility(View.GONE);
                username.setText("Hi " + name + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        permref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kids.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    kids.add(snapshot.getKey());
                }
                ParentPermListAdapter adapter = new ParentPermListAdapter(AdminHome.this, R.layout.perm_row_admin, permList);
                listView.setAdapter(adapter);
                permList.clear();
                for (String kid: kids) {
                    for (DataSnapshot snap: dataSnapshot.child(kid).getChildren()) {
                        if (snap.child("adPerm").getValue().equals("Waiting approval") && snap.child("pPerm").getValue().equals("Permission Granted!")) {
                            flag = 0;
                            Permission perm = snap.getValue(Permission.class);
                            for (Permission permission: permList) {
                                if (permission.getName().equals(perm.getName()) && permission.getReason().equals(perm.getReason())
                                        && permission.getPlace().equals(perm.getPlace()) && permission.getLeave().equals(perm.getLeave()))
                                {
                                    flag = 1;
                                }
                            }
                            if (flag == 0) {
                                permList.add(perm);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Permission perm = permList.get(position);
                Intent intent = new Intent(AdminHome.this, AdminGrantPerm.class);
                intent.putExtra("permObj", perm);
                startActivity(intent);
            }
        });

        studentManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, StudentList.class));
            }
        });

        adminRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this , RegisterActivity.class));
            }
        });

        securityRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this , SecurityRegister.class));
            }
        });

        logHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, MasterLogHistpry.class));
            }
        });

        usersignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getInstance().signOut();
                Intent intent = new Intent(AdminHome.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}