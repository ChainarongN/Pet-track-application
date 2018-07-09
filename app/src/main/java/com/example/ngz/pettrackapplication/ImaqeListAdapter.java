package com.example.ngz.pettrackapplication;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
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

public class ImaqeListAdapter extends ArrayAdapter<imageUpload> {
    private Activity context;
    private int resource;
    private List<imageUpload> listImage;

    public ImaqeListAdapter(@NonNull Activity context, int resource, @NonNull List<imageUpload> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listImage = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View v = inflater.inflate(resource,null);
        TextView tvName = (TextView)v.findViewById(R.id.tvImageName);
        ImageView img = (ImageView)v.findViewById(R.id.imgView);

        tvName.setText("Name Pet :  "+listImage.get(position).getPet_name()+"\n");
        tvName.append("Telephone :  "+listImage.get(position).getPet_phone()+"\n");
        tvName.append("Email :  "+listImage.get(position).getPet_email()+"\n");
        tvName.append("Category :  "+listImage.get(position).getPet_Category()+"\n");
        Glide.with(context).load(listImage.get(position).getPet_img()).into(img);

        return v;

    }
}
