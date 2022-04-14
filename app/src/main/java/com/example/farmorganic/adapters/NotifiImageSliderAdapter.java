package com.example.farmorganic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmorganic.R;
import com.example.farmorganic.models.NotifiImageSlider;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//public class NotifiImageSliderAdapter extends FirebaseRecyclerAdapter<NotifiImageSlider,NotifiImageSliderAdapter.NISAViewHolder> {
public class NotifiImageSliderAdapter extends RecyclerView.Adapter<NotifiImageSliderAdapter.NISAViewHolder> {

   //private static final Tag ="NotifiImageSlider";

    ArrayList<NotifiImageSlider> notifiImageSliderList;
    Context context;

    public NotifiImageSliderAdapter(Context context, ArrayList<NotifiImageSlider> notifiImageSliderList) {
        this.notifiImageSliderList = notifiImageSliderList;
        this.context=context;
    }

    @NonNull
    @Override
    public NotifiImageSliderAdapter.NISAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_notifi_image_slider_recycler,parent,false);
        return new NISAViewHolder(itemview);

    }

    @Override
    public void onBindViewHolder(@NonNull NotifiImageSliderAdapter.NISAViewHolder holder, int position) {

        NotifiImageSlider notifiImageSlider=notifiImageSliderList.get(position);

        Picasso.get().load(notifiImageSlider.getImgUrl()).into(holder.imageView);
        //Glide.with(HomeFragment.this).load(notifiImageSliderList.get(position).getImgUrl()).into(holder.imageView);
}



    @Override
    public int getItemCount() {
        return notifiImageSliderList.size();
    }
    public static class NISAViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public NISAViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView=itemView.findViewById(R.id.notifi_sliderImage);
        }
    }
}
