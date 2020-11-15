package com.example.plasma_4life;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DonorListAdapter extends ArrayAdapter<DonorList> {

    private Context mContext;
    int mResource;

    public DonorListAdapter(@NonNull Context context, int resource, ArrayList<DonorList> objects) {
        super(context, resource, objects);
        mContext=context;
        mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name=getItem(position).getName();
        String group=getItem(position).getGroup();
        String num=getItem(position).getPhone_num();
        DonorList donorList=new DonorList(name,group,num);
        LayoutInflater inflater=LayoutInflater.from(mContext);
        convertView=inflater.inflate(mResource,parent,false);

        TextView t_name=(TextView)convertView.findViewById(R.id.d_name);
        TextView t_group=(TextView)convertView.findViewById(R.id.d_group);
        t_name.setText(name);
        t_group.setText(group);
        return convertView;
    }
}
