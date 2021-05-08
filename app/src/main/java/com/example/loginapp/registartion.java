package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registartion extends AppCompatActivity {
EditText mName ,mEmail,mPassword,mPhone;
Button mRegisterBtn;
TextView mLogin;
FirebaseAuth fAuth;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registartion);
        mName =findViewById(R.id.name);
        mEmail=findViewById(R.id.email);
        mPhone=findViewById(R.id.phone);
        mPassword=findViewById(R.id.password1);
        mRegisterBtn=findViewById(R.id.registerBtn);;
        mLogin=findViewById(R.id.createText);

        fAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);

        if (fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

    }

    public void register(View view) {
        String email =mEmail.getText().toString().trim();
        String password=mPassword.getText().toString().trim();
        String phone=mPhone.getText().toString().trim();

        if (TextUtils.isEmpty(email)){

            mEmail.setError("Email is Required");
            return;
        }

        if (TextUtils.isEmpty(password)){

            mPassword.setError("Password is Required");
            return;
        }

        if (password.length()<6){
            mPassword.setError("Password must be > 6 Char");
            return;
        }

        if (TextUtils.isEmpty(phone)){

            mPhone.setError("phone number is Required");
            return;
        }

        if (phone.length()==7){
            mPhone.setError("phone number must be = 8 ");
            return;
        }

        progressBar.setVisibility(view.VISIBLE);

        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(registartion.this, "User create", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else{
                    Toast.makeText(registartion.this, "Error"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
            }
        });

    }

    public void login(View view) {

        startActivity(new Intent(getApplicationContext(),Login.class));

    }
}