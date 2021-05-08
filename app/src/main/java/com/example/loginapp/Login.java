package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Login extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mCreateBtn , forgotText;
    FirebaseAuth fAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password1);
        mLoginBtn=findViewById(R.id.registerBtn);;
        mCreateBtn=findViewById(R.id.createText);

        fAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar2);
        forgotText=findViewById(R.id.forget);


    }

    public void register(View view) {
        String email =mEmail.getText().toString().trim();
        String password=mPassword.getText().toString().trim();

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
        progressBar.setVisibility(View.VISIBLE);

        // authentication

        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //send verification link
                    FirebaseUser user =fAuth.getCurrentUser();
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Login.this, "verification Email has been send", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            //Toast.makeText(Login.this, "Email not send" +e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("tag", "onFailure: email not send "+e.getMessage());
                        }
                    });

                    Toast.makeText(Login.this, "Login successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else {
                    Toast.makeText(Login.this, "Error"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }

            }
        });

    }

    public void create(View view) {
        startActivity(new Intent(getApplicationContext(),registartion.class));

    }

    public void forgetPass(View view) {
        EditText restMail=new EditText(view.getContext());
        AlertDialog.Builder passwordRestDialog = new AlertDialog.Builder(view.getContext());
        passwordRestDialog.setTitle("Rest Password");
        passwordRestDialog.setMessage("Enter your Email to Recevied Rest link");
        passwordRestDialog.setView(restMail);
        passwordRestDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Extract email and send rest link
                String mail = restMail.getText().toString();
                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Login.this, "Rest link send to your Email", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "Error ! Rest link is not send"+ e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
        passwordRestDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // close the dialog

            }
        });
        passwordRestDialog.create().show();
    }
}