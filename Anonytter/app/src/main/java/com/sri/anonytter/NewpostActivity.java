package com.sri.anonytter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class NewpostActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpost);

        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public void submit(View view) {
        EditText edittext2 = (EditText) findViewById(R.id.editText2);
        EditText edittext3 = (EditText) findViewById(R.id.editText3);
        String title = edittext3.getText().toString();
        String body = edittext2.getText().toString();

        if (edittext2.getText().length() == 0) {
            edittext2.setError("Required");
        }

        if (edittext3.getText().length() == 0) {
            edittext3.setError("Required");
        } else {
            ProgressDialog progressdialog = new ProgressDialog(this);
            progressdialog.setMessage("Loading");
            progressdialog.show();

            final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            String key = mDatabase.child("posts").push().getKey();
            Post post = new Post(userId, title, body);
            Map<String, Object> postValues = post.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/posts/" + key, postValues);
            mDatabase.updateChildren(childUpdates);

            Intent intent = new Intent(NewpostActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
