package com.example.plasma_4life;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class HospitalActivity extends AppCompatActivity {

    private EditText email,password;
    private FirebaseAuth authorisation;
    private Button mbut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        authorisation=FirebaseAuth.getInstance();
        email=findViewById(R.id.hospital_email);
        password=findViewById(R.id.hospital_password);
        mbut=findViewById(R.id.hospital_register);
        mbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authorisation.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(HospitalActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Log.i("HospitalActivity","Auth sucess");

                        }
                        else
                        {
                            Log.i("hospital","auth failed");
                        }
                    }
                });
            }
        });

    }
}