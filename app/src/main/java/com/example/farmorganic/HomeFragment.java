package com.example.farmorganic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmorganic.adapters.NotifiImageSliderAdapter;
import com.example.farmorganic.models.NotifiImageSlider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    RecyclerView recyclerView;
    List<NotifiImageSlider> notifiImageSliderList=new ArrayList<>();
    NotifiImageSliderAdapter notifiImageSliderAdapter;

    private List<NotifiImageSlider> notifiImageSliders ;

    private StorageReference storageReference;
    private FirebaseDatabase firebaseDatabase;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView= view.findViewById(R.id.recyclerview);
        NotifiImageSliderAdapter notifiImageSliderAdapter=new NotifiImageSliderAdapter(notifiImageSliders);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        String photoReference= storageReference.child("notifi_image_slider/logo3.jpg").getPath();





        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(notifiImageSliderAdapter);




        notifiImageSliderList=new ArrayList<NotifiImageSlider>();
        notifiImageSliderList.add(new NotifiImageSlider(photoReference));
        return view;




    }

}