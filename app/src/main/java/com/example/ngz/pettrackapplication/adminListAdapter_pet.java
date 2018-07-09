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

public class adminListAdapter_pet extends ArrayAdapter<imageUpload> {

    private Activity context;
    private int resource;
    private List<imageUpload> listImage_admin_pet;

    public adminListAdapter_pet(@NonNull Activity context, int resource, @NonNull List<imageUpload> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listImage_admin_pet = objects;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View v_lost = inflater.inflate(resource,null);
        TextView tvName_admin = (TextView)v_lost.findViewById(R.id.tvImageName_pet);
        ImageView img = (ImageView)v_lost.findViewById(R.id.imgView_pet);


        tvName_admin.setText("Name :  "+ listImage_admin_pet.get(position).getPet_name()+"\n");
        tvName_admin.append("Telephone :  "+ listImage_admin_pet.get(position).getPet_phone()+"\n");
        tvName_admin.append("Email :  "+ listImage_admin_pet.get(position).getPet_email()+"\n");
        tvName_admin.append("Category :  "+ listImage_admin_pet.get(position).getPet_Category()+"\n");
        Glide.with(context).load(listImage_admin_pet.get(position).getPet_img()).into(img);

        return v_lost;

    }
}
