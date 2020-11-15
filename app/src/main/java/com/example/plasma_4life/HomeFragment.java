package com.example.plasma_4life;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

   DatabaseReference reference1,reference2;
   long count_donor;
   long count_req;
   TextView count;
   TextView count_reqView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_home, container, false);
        count=view.findViewById(R.id.donor_count);
        count_reqView=view.findViewById(R.id.requests_count);
        reference1=FirebaseDatabase.getInstance().getReference().child("Donor");
        reference2=FirebaseDatabase.getInstance().getReference().child("Patient");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count_donor=snapshot.getChildrenCount();
                count.setText(""+count_donor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
              //Do rightful
            }
        });
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count_req=snapshot.getChildrenCount();
                count_reqView.setText(""+count_req);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}