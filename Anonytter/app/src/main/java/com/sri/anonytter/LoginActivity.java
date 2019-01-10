package com.sri.anonytter;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = ItemOneFragment.newInstance();
                                break;
                            case R.id.action_item2:
                                selectedFragment = ItemTwoFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, ItemOneFragment.newInstance());
        transaction.commit();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();


    }





    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (mAuth.getCurrentUser() != null) {
            onAuthSuccess(mAuth.getCurrentUser());
        }
    }

    private void onAuthSuccess(FirebaseUser user) {


        // Go to MainActivity
        Intent intent = new Intent(LoginActivity.this , MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);


        finish();
    }


    public void createAccount (View view){

        EditText edittext5 = (EditText)findViewById(R.id.editText5);
        final EditText edittext6 = (EditText)findViewById(R.id.editText6);
        final EditText edittext = (EditText)findViewById(R.id.editText);
        final String email = edittext5.getText().toString();
        String password = edittext6.getText().toString();
        String repass = edittext.getText().toString();


        if(edittext.getText().length() == 0 )
        {

            edittext.setError("Field cannot be left blank");
        }


        if( edittext5.getText().length() == 0)
        {
            edittext5.setError("Field cannot be left blank");

        }

        if( edittext6.getText().length() == 0)
        {
            edittext6.setError("Field cannot be left blank");

        }

        else {


            if (password.equals(repass))

            {

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user1 = mAuth.getCurrentUser();
                                    String userId = user1.getUid();

                                    User user = new User("",email);

                                    mDatabase.child("users").child(userId).setValue(user);
                                    Log.d(TAG,"user stored in database");


                                    onAuthSuccess(task.getResult().getUser());
                                }


                                else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Account creation failed.",
                                            Toast.LENGTH_LONG).show();
                                }

                                // ...
                            }
                        });
            }





            else {

                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);


                builder.setMessage("Password don't match")
                        .setTitle("Error");


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        edittext.setText("");
                        edittext6.setText("");
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        }

    }



    public void signIn (View view) {

        EditText edittext7 = (EditText)findViewById(R.id.editText7);
        EditText edittext8 = (EditText)findViewById(R.id.editText8);

        String email = edittext7.getText().toString();
        String password = edittext8.getText().toString();


        if( edittext7.getText().length() == 0)
        {
            edittext7.setError("Field cannot be left blank");

        }

        if( edittext8.getText().length() == 0)
        {
            edittext8.setError("Field cannot be left blank");

        }

        else {


            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_LONG).show();
                            }

                            // ...
                        }
                    });



        }



    }


}

