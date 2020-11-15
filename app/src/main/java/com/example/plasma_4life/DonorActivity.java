package com.example.plasma_4life;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class DonorActivity extends AppCompatActivity {

    private EditText mEmail,mPassword,b_group,mName,mBirth,mNumber,mLocation;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private Button register;
    private Spinner group_spin,gender_spin;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);

        mAuth = FirebaseAuth.getInstance();
        mEmail=(EditText)findViewById(R.id.patient_email);
        b_group=findViewById(R.id.patient_blood);
        mNumber=findViewById(R.id.patient_phone);
        mLocation=findViewById(R.id.hospital_address);
        mName=findViewById(R.id.patientName);
        mBirth=findViewById(R.id.patient_dob);
        gender_spin=findViewById(R.id.gender_spin);
        group_spin=findViewById(R.id.blood_spin);
        register=(Button)findViewById(R.id.patient_register_button);
        pd=new ProgressDialog(this);
        mPassword=(EditText)findViewById(R.id.patient_password);
        setupSpinner();
        //mVerify=findViewById(R.id.verify);
        mReference=FirebaseDatabase.getInstance().getReference();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString();
                String pass=mPassword.getText().toString();
                String age=mBirth.getText().toString();
                String group=b_group.getText().toString();
                String name=mName.getText().toString();
                String phone=mNumber.getText().toString();
                String location=mLocation.getText().toString();
                if(TextUtils.isEmpty(email)||TextUtils.isEmpty(pass))
                {
                    Toast.makeText(DonorActivity.this,"Invalid Ceredentials",Toast.LENGTH_SHORT).show();
                    mEmail.setError("Email is required");
                    mEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mEmail.setError("Enter Valid Email");
                    mEmail.requestFocus();
                    return;
                }
                if(pass.length()<6)
                {
                    mPassword.setError("Password too short");
                    mPassword.requestFocus();
                    return;
                }
                else
                    registerUser(name,email,pass,age,group,phone,location);
            }
        });
    }
    private void registerUser(String name,String email,String pass,String age,String group,String phone,String location)
    {
        pd.setMessage("Please wait");
        pd.show();
        mAuth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String,Object> map=new HashMap<>();
                map.put("name",name);
                map.put("dob",age);
                map.put("email",email);
                map.put("blood",group);
                map.put("phone",phone);
                map.put("location",location);
                mReference.child("Donor").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            pd.dismiss();
                            Log.i("DonorActivity","Successful connection");
                            Toast.makeText(DonorActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(DonorActivity.this,HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else
                        {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(DonorActivity.this,"User Already Registered",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

            }
        });

    }

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter bloodGroupAdapter = ArrayAdapter.createFromResource(this,
                R.array.Blood_group, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        bloodGroupAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        group_spin.setAdapter(bloodGroupAdapter);

        // Set the integer mSelected to the constant values
        group_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals("A+")) {
                        b_group.setText("A+");
                    } else if (selection.equals("A-")) {
                        b_group.setText("A-");
                    } else if (selection.equals("B-")) {
                        b_group.setText("B-");
                    } else if (selection.equals("O-")) {
                        b_group.setText("O-");
                    } else if (selection.equals("AB-")) {
                        b_group.setText("AB-");
                    } else if (selection.equals("AB+")) {
                        b_group.setText("AB+");
                    } else if (selection.equals("B+")) {
                        b_group.setText("B+");
                    } else if (selection.equals("O+")) {
                        b_group.setText("O+");
                    }
                    else {
                       b_group.setText("K+");
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                b_group.setText("O+");
            }
        });
    }

}