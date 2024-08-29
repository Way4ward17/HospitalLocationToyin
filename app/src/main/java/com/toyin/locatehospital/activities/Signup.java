package com.toyin.locatehospital.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.wayvytech.hospitallocations.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    Dialog dialog;
    FirebaseAuth mAuth;
    EditText emailText, passwordText, fullname, matricnumber;
    String push;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initLoader();
        mAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        fullname = findViewById(R.id.fullname);
        matricnumber = findViewById(R.id.matric);
    }

    private void createUser(){
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (TextUtils.isEmpty(email)){
            emailText.setError("Email cannot be empty");
            emailText.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            passwordText.setError("Password cannot be empty");
            passwordText.requestFocus();
        }else{
            showLoading();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        hideLoading();
                        submitInformation();
                        Toast.makeText(Signup.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Signup.this, Login.class));
                        finish();
                    }else{
                        hideLoading();
                        Toast.makeText(Signup.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void signup(View view) {
        createUser();
    }

    public void back(View view) {
        finish();
    }

    public void showLoading() {
        dialog.show();
    }

    public void hideLoading() {
        dialog.dismiss();
    }

    public void initLoader() {
        dialog = new Dialog(Signup.this);
        dialog.setContentView(R.layout.dialogloading);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    private void submitInformation(){

        String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
                "Oct", "Nov", "Dec"};
        GregorianCalendar gcalendar = new GregorianCalendar();
        String date = months[gcalendar.get(Calendar.MONTH)] + " " + gcalendar.get(Calendar.DATE) + " " + gcalendar.get(Calendar.YEAR);


        push = String.valueOf(System.currentTimeMillis());
        Map<String, String> map = new HashMap<>();
        map.put("id", push);
        map.put("name", fullname.getText().toString());
        map.put("matricnumber", matricnumber.getText().toString());
        map.put("date", date);



        FirebaseDatabase.getInstance().getReference().child("student").child(FirebaseAuth.getInstance().getUid()).child(push)
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        hideLoading();
                        fullname.setText("");
                        matricnumber.setText("");

                        Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();
                        push = String.valueOf(System.currentTimeMillis());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideLoading();
                        Toast.makeText(getApplicationContext(), "Could not insert", Toast.LENGTH_LONG).show();
                    }
                });


    }
}