package com.hanahzac.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentList extends AppCompatActivity {

    private Button add;
    private EditText searchBar;
    private ListView listView;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Student");
    final ArrayList<Student> list = new ArrayList<Student>();
    ArrayAdapter<String> adapter;
    String[] names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        add = findViewById(R.id.addButton);
        listView = findViewById(R.id.listView);
        searchBar = findViewById(R.id.searchBar);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentList.this, studentRegisterActivity.class));
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Student Student = snapshot.getValue(Student.class);
                    list.add(Student);
                }
                names = new String[list.size()];

                for (int i=0; i<names.length; i++)
                    names[i] = list.get(i).getName();

                adapter = new ArrayAdapter<String>(StudentList.this, android.R.layout.simple_list_item_1,names);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (StudentList.this).adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student Student = list.get(position);
                Intent intent = new Intent(StudentList.this, ViewStudent.class);
                intent.putExtra("studObj", Student);
                startActivity(intent);
            }
        });

    }
}