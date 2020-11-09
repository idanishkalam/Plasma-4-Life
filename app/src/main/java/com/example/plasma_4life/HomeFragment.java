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

   DatabaseReference reference;
   long count_donor;
   TextView count;
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
        reference=FirebaseDatabase.getInstance().getReference().child("Donor");
        reference.addValueEventListener(new ValueEventListener() {
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
        return view;
    }
}