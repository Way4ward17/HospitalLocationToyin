package com.toyin.locatehospital.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.toyin.locatehospital.R;


public class Login extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText emailText, passwordText;

    Dialog dialog;
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.wayvytech.hospital";
    private final String LOGIN = "login";
    int value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);


        mAuth = FirebaseAuth.getInstance();
        initLoader();
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
    }

    public void login(View view) {
        loginUser();
    }


    private void loginUser(){
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
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                        preferencesEditor.putInt(LOGIN, 1);
                        preferencesEditor.apply();
                        hideLoading();
                        Toast.makeText(Login.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, MainActivity.class));
                        finish();
                    }else{
                        hideLoading();
                        Toast.makeText(Login.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    public void showLoading() {
        dialog.show();
    }

    public void hideLoading() {
        dialog.dismiss();
    }

    public void initLoader() {
        dialog = new Dialog(Login.this);
        dialog.setContentView(R.layout.dialogloading);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void close(View view) {
        finish();
    }

    public void signup(View view) {
        startActivity(new Intent(Login.this, Signup.class));
    }
}