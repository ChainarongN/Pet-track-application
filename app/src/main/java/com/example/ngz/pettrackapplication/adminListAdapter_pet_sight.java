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

public class adminListAdapter_pet_sight extends ArrayAdapter<imageUpload_sight> {

    private Activity context;
    private int resource;
    private List<imageUpload_sight> listImage_admin_pet_sight;

    public adminListAdapter_pet_sight(@NonNull Activity context, int resource, @NonNull List<imageUpload_sight> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listImage_admin_pet_sight = objects;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View v_sight = inflater.inflate(resource,null);
        TextView tvName_admin = (TextView)v_sight.findViewById(R.id.tvImageName_pet_sight);
        ImageView img = (ImageView)v_sight.findViewById(R.id.imgView_pet_sight);


        tvName_admin.setText("Email :  "+ listImage_admin_pet_sight.get(position).getPet_email_sight()+"\n");
        tvName_admin.append("Telephone :  "+ listImage_admin_pet_sight.get(position).getPet_phone_sight()+"\n");
        tvName_admin.append("Category :  "+ listImage_admin_pet_sight.get(position).getPet_Category_sight()+"\n");
        Glide.with(context).load(listImage_admin_pet_sight.get(position).getPet_img_sight()).into(img);

        return v_sight;

    }
}
