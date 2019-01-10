package com.sri.android.taskapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.google.firebase.database.*;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Post> list, list1;
    MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Post>();


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Post p = dataSnapshot1.getValue(Post.class);
                    list.add(p);

                }

                int l = list.size();

                if (list.size() > 10) {
                    list1 = new ArrayList<>(list.subList((l - 10), l));

                } else {
                    list1 = list;
                }

                adapter = new MyAdapter(MainActivity.this, list1);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT);

            }
        });

    }
}