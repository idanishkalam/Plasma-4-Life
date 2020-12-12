package com.example.plasma_4life;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    FloatingActionButton newRequest;
   DatabaseReference reference1,reference2;
   long count_donor;
   long count_req;
   ProgressDialog pd;
   TextView count;
   TextView count_reqView,total_cases,active_cases,recover_cases,death_cases;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_home, container, false);
        pd=new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        pd.show();
        newRequest=view.findViewById(R.id.newRequest);
        count=view.findViewById(R.id.donor_count);
        total_cases=view.findViewById(R.id.total_cases);
        active_cases=view.findViewById(R.id.active_cases);
        recover_cases=view.findViewById(R.id.recover_cases);
        death_cases=view.findViewById(R.id.death_cases);
        count_reqView=view.findViewById(R.id.requests_count);
        reference1=FirebaseDatabase.getInstance().getReference().child("Donor");
        reference2=FirebaseDatabase.getInstance().getReference().child("Patient");
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api=retrofit.create(Api.class);
        Call<CovidData> call=api.getData();
        call.enqueue(new Callback<CovidData>() {
            @Override
            public void onResponse(Call<CovidData> call, Response<CovidData> response) {
             CovidData data=response.body();
             if(data!=null)
             {
                 total_cases.setText(""+data.getCases());
                 active_cases.setText(""+data.getActive());
                 recover_cases.setText(""+data.getRecovered());
                 death_cases.setText(""+data.getDeaths());
             }
             else
                 Toast.makeText(getContext(),"Covid Data Not Found",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CovidData> call, Throwable t) {
                Toast.makeText(getContext(),"Covid Data Not Found",Toast.LENGTH_SHORT).show();
            }
        });

        newRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),RequestPlasmaActivity.class));
            }
        });
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count_donor=snapshot.getChildrenCount();
                pd.dismiss();
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