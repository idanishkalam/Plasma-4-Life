package com.example.plasma_4life;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private EditText loc,group;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_search, container, false);
        Button search=view.findViewById(R.id.donor_search_button);
        loc=view.findViewById(R.id.donor_location);
        group=view.findViewById(R.id.donor_blood_group);
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        DatabaseReference rootref= FirebaseDatabase.getInstance().getReference();
        ListView donor_search_result=view.findViewById(R.id.donor_list);
        ArrayList<DonorList> donorLists=new ArrayList<DonorList>();
        DonorListAdapter adapter=new DonorListAdapter(getContext(),R.layout.search_item,donorLists);
        donor_search_result.setAdapter(adapter);
        donor_search_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               DonorList d=(DonorList) parent.getItemAtPosition(position);
               String num=d.getPhone_num();
               Intent intent=new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",num,null));
               startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location=loc.getText().toString();
                String blood_group=group.getText().toString();
               rootref.child("Donor").orderByChild("blood").equalTo(blood_group).addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                     donorLists.clear();
                       for(DataSnapshot data:snapshot.getChildren())
                       {
                           if(data.child("location").getValue().toString().equalsIgnoreCase(location)) {
                               DonorList donor = new DonorList(data.child("name").getValue().toString(), blood_group, data.child("phone").getValue().toString());
                               donorLists.add(donor);
                           }
                       }
                    adapter.notifyDataSetChanged();
                       if(donorLists.isEmpty())
                       {
                           Toast.makeText(getContext(),"No Donor Found",Toast.LENGTH_SHORT).show();
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
            }
        });
return view;
    }
}