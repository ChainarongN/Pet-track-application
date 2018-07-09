package com.example.ngz.pettrackapplication;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class adminListAdapter extends ArrayAdapter<Database_Register> {
    private Activity context;
    private int resource;
    private List<Database_Register> listImage_admin;

    public adminListAdapter(@NonNull Activity context, int resource, @NonNull List<Database_Register> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listImage_admin = objects;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View v = inflater.inflate(resource,null);
        TextView tvName_admin = (TextView)v.findViewById(R.id.textView_admin);


        tvName_admin.setText("Name :  "+ listImage_admin.get(position).get_username()+"\n");
        tvName_admin.append("Telephone :  "+ listImage_admin.get(position).get_Telephone()+"\n");
        tvName_admin.append("Email :  "+ listImage_admin.get(position).get_Email()+"\n");
        tvName_admin.append("Status :  "+ listImage_admin.get(position).get_status()+"\n");

        return v;

    }

}
