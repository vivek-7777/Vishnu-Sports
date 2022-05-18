package com.example.vishnusports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.example.vishnusports.RegisterActivity2.gender;
import static com.example.vishnusports.RegisterActivity2.interest;
import static com.example.vishnusports.RegisterActivity2.username;


public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ProgressBar progressBar;
    String userID;
    String rollno;
    boolean temp=false;

    protected String email,password,confirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final ProgressBar[] progressBar = {findViewById(R.id.progressBar)};

        TextView btnEmail =  findViewById(R.id.inputEmail);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                email =btnEmail.getText().toString();
            }
        });

        TextView btnPassword = findViewById(R.id.inputPasswordEditText);
        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password=btnPassword.getText().toString();
            }
        });

        TextView btnConfirmPassword = findViewById(R.id.inputConfirmPasswordEditText);
        btnConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmPassword=btnConfirmPassword.getText().toString();
            }
        });

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar[0] = findViewById(R.id.progressBar);

        /*if(fAuth.getCurrentUser() != null){
           // startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }*/

        TextView btn1 = findViewById(R.id.otpRegister);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email =btnEmail.getText().toString();
                password=btnPassword.getText().toString();


                confirmPassword=btnConfirmPassword.getText().toString();
                if(email.length()<1)
                    btnEmail.setError("Email cannot be empty");
                else if(!(email.contains("bvrit.ac.in")) || (email.contains("placements")) || (email.contains("placementcoordinators")))
                    btnEmail.setError("Please use valid college mail");
                else if(email.length()<=19)
                    btnEmail.setError("Please use valid college mail");
                else if(password.length()<8 || confirmPassword.length()<8){
                    btnPassword.setError("password must contain minimum 8 characters");
                    btnConfirmPassword.setError("password must contain minimum 8 characters");
                }
                else if(!(password.compareTo(confirmPassword)==0)){
                    btnPassword.setError("password does not match");
                    btnConfirmPassword.setError("password does not match");
                }
                else if(temp){
                    btnEmail.setError("User exists!");
                }
                else {
                    progressBar[0].setVisibility(View.VISIBLE);
                    btn1.setVisibility(View.INVISIBLE);

                    fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                // send verification link
                                FirebaseUser fuser = fAuth.getCurrentUser();
                                fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(RegisterActivity.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                        final String email = btnEmail.getText().toString().trim();
                                        String Email=email;
                                        progressBar[0].setVisibility(View.INVISIBLE);
                                        btn1.setVisibility(View.VISIBLE);
                                        Intent i=(new Intent(RegisterActivity.this,RegisterConfirmation.class));

                                        startActivity(i);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                    }
                                });

                                userID = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("users").document(userID);
                                Map<String, String> user = new HashMap<>();
                                dataholder obj=new dataholder(username,gender,email,password,interest[0],interest[1],interest[2]);
                                rollno=email.substring(0,email.length()-12);
                                user.put("rollno",rollno);
                                user.put("email",email);
                                user.put("username",username);
                                user.put("gender",gender);
                                user.put("game1",interest[0]);
                                user.put("game2",interest[1]);
                                user.put("game3",interest[2]);
                                user.put("status","false");

                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user Profile is created for " + userID + "of rollno"+rollno);

                                        dataholder obj=new dataholder(username,gender,email,password,interest[0],interest[1],interest[2]);
                                        rollno=email.substring(0,email.length()-12);
                                        FirebaseDatabase database = FirebaseDatabase.getInstance("https://vishnu-sports-default-rtdb.asia-southeast1.firebasedatabase.app/");
                                        DatabaseReference myRef = database.getReference("Users");

                                        myRef.child(rollno).child("username").setValue(username);
                                        myRef.child(rollno).child("email").setValue(email);
                                        myRef.child(rollno).child("gender").setValue(gender);
                                        myRef.child(rollno).child("game1").setValue(interest[0]);
                                        myRef.child(rollno).child("game2").setValue(interest[1]);
                                        myRef.child(rollno).child("game3").setValue(interest[2]);
                                        myRef.child(rollno).child("rollno").setValue(rollno);
                                        myRef.child(rollno).child("status").setValue("false");

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }
                                });
                                //startActivity(new Intent(getApplicationContext(), OtpRegister.class));

                            } else {
                                Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar[0].setVisibility(View.INVISIBLE);
                                btn1.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                }
            }
        });

        TextView btn = findViewById(R.id.Login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=(new Intent(RegisterActivity.this,LoginActivity.class));

                startActivity(i);
            }
        });

    }
}