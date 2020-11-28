package com.example.plasma_4life;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RequestActivity extends AppCompatActivity {

    private EditText patient_name,patient_age,patient_gender,patient_blood,patient_call,patient_address,patient_email,patient_password,patient_attendent;
    private Button register;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        patient_name=findViewById(R.id.patient_name);
        patient_age=findViewById(R.id.patient_age);
        patient_gender=findViewById(R.id.patient_genderId);
        patient_blood=findViewById(R.id.patient_blood_group);
        patient_call=findViewById(R.id.patient_call);
        patient_email=findViewById(R.id.patient_emailID);
        patient_password=findViewById(R.id.patient_passwordID);
        patient_address=findViewById(R.id.patient_hospital_address);
        patient_attendent=findViewById(R.id.patient_attendent);
        pd=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        register=findViewById(R.id.patient_registery);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p_email=patient_email.getText().toString();
                String p_password=patient_password.getText().toString();
                String p_name=patient_name.getText().toString();
                String  p_age=patient_age.getText().toString();
                String p_gender=patient_gender.getText().toString();
                String p_phone=patient_call.getText().toString();
                String p_group=patient_blood.getText().toString();
                String p_attendent=patient_attendent.getText().toString();
                String p_address=patient_address.getText().toString();

                if(TextUtils.isEmpty(p_email)||TextUtils.isEmpty(p_password))
                {
                    patient_email.setError("Required Field");
                    patient_email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(p_email).matches())
                {
                    patient_email.setError("Invalid Email");
                    patient_email.requestFocus();
                    return;
                }
                if(p_password.length()<6)
                {
                    patient_password.setError("Password too short");
                    patient_password.requestFocus();
                    return;
                }
                else {
                    registerUser(p_email,p_password,p_name,p_age,p_phone,p_group,p_gender,p_attendent,p_address);
                }
            }
        });
    }
    private void registerUser(String email,String password,String name,String age,String phone,String group,String gender,String attendent,String address){
        pd.setMessage("please wait");
        pd.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String,Object> p_map=new HashMap<>();
                p_map.put("Name",name);
                p_map.put("Email",email);
                p_map.put("Blood",group);
                p_map.put("phone",phone);
                p_map.put("Address",address);
                p_map.put("Attendent",attendent);
                databaseReference.child("Patient").child(firebaseAuth.getCurrentUser().getUid()).setValue(p_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            pd.dismiss();
                            Intent intent=new Intent(RequestActivity.this,HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else
                        {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(RequestActivity.this,"User Already registered",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }
}