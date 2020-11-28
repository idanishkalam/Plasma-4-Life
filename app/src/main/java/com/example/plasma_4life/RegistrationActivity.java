package com.example.plasma_4life;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrationActivity extends AppCompatActivity {

    private Button mDonor;
    private Button mPatient;
    private Button mHospital;
    private Button mBank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mDonor=(Button)findViewById(R.id.id_donor);
        mPatient=(Button)findViewById(R.id.id_patient);
        mBank=(Button)findViewById(R.id.id_bank);
        mHospital=(Button)findViewById(R.id.id_hospital);

        mDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,DonorActivity.class));
            }
        });
        mPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,RequestActivity.class));
            }
        });

    }
}