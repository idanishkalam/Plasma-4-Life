package com.example.plasma_4life;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private Button logout;
    private FirebaseAuth mAuth;
    private DatabaseReference mReference;
    private TextInputEditText email,phone,blood_group,status,location;
    private TextView name;
    private ImageView user_pic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        View rootView=inflater.inflate(R.layout.fragment_profile, container, false);
        email=rootView.findViewById(R.id.user_email);
        phone=rootView.findViewById(R.id.user_phone);
        blood_group=rootView.findViewById(R.id.user_blood);
        status=rootView.findViewById(R.id.user_status);
        name=rootView.findViewById(R.id.user_name);
        location=rootView.findViewById(R.id.user_location);
        user_pic=rootView.findViewById(R.id.user_image);
        String uid=mAuth.getCurrentUser().getUid();
        if(HomeActivity.STATUS==100){
        mReference= FirebaseDatabase.getInstance().getReference().child("Donor").child(uid);
            mReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    name.setText(snapshot.child("name").getValue().toString());
                    user_pic.setImageResource(R.drawable.donor_pic);
                    email.setText(snapshot.child("email").getValue().toString());
                    phone.setText(snapshot.child("phone").getValue().toString());
                    blood_group.setText(snapshot.child("blood").getValue().toString());
                    status.setText(snapshot.child("Status").getValue().toString());
                    location.setText(snapshot.child("location").getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else if(HomeActivity.STATUS==101)
        {
            DatabaseReference new_ref=FirebaseDatabase.getInstance().getReference().child("Patient").child(uid);
            new_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    name.setText(snapshot.child("Name").getValue().toString());
                    user_pic.setImageResource(R.drawable.patient_icon);
                    email.setText(snapshot.child("Email").getValue().toString());
                    phone.setText(snapshot.child("phone").getValue().toString());
                    blood_group.setText(snapshot.child("Blood").getValue().toString());
                    status.setText(snapshot.child("Status").getValue().toString());
                    location.setText(snapshot.child("Address").getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        Button logout=rootView.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();

            }
        });

        return rootView;
    }
    private void logoutUser()
    {
        mAuth.signOut();
        Intent intent=new Intent(getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}