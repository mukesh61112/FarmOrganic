package com.example.farmorganic.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmorganic.R;
import com.example.farmorganic.adapters.NotifiImageSliderAdapter;
import com.example.farmorganic.models.NotifiImageSlider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    RecyclerView notifi_image_slider_recyclerView;
   // RecyclerView order_by_cate_recylerView;

    ArrayList<NotifiImageSlider> notifiImageSliderList;
   // ArrayList<OrderByCateModel> orderByCateModelArrayList;


    NotifiImageSliderAdapter notifiImageSliderAdapter;
   // OrderByCateAdapter orderByCateAdapter;


    NotifiImageSlider notifiImageSlider;
 //   OrderByCateModel orderByCateModel;


    RecyclerView.LayoutManager notifiImageSliderlayoutManager;
   // RecyclerView.LayoutManager orderByCatelayoutManager;


    Context context;


    private StorageReference storageReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseFirestore firebaseFirestore;
    DatabaseReference databaseReference ;
    DatabaseReference firestoreDatabaseReference;



    ProgressDialog progressDialog;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);




        notifi_image_slider_recyclerView= view.findViewById(R.id.notifi_image_recyclerview);
        //order_by_cate_recylerView=view.findViewById(R.id.order_by_cate_recyclerview);



        // ..........process dialog for firebase firestore...........................
//         progressDialog=new ProgressDialog(HmeFragment.this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("fetching data");
//        progressDialog.show();



        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
/*
        DatabaseReference getImage = databaseReference.child("image");

        ImageView imageView=view.findViewById(R.id.imageView);
        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // getting a DataSnapshot for the location at the specified
                // relative path and getting in the link variable
                String link = dataSnapshot.getValue(String.class);

                // loading that data into rImage
                // variable which is ImageView
                Picasso.get().load(link).into(imageView);
            }

            // this will called when any problem
            // occurs in getting data
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // we are showing that error message in toast
                //Toast.makeText(MainActivity.this, "Error Loading Image", Toast.LENGTH_SHORT).show();
            }
        });
*/

        notifiImageSliderlayoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,true);
       // orderByCatelayoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);

        notifi_image_slider_recyclerView.setLayoutManager(notifiImageSliderlayoutManager);
       // order_by_cate_recylerView.setLayoutManager(orderByCatelayoutManager);

        notifiImageSliderList=new ArrayList<>();
      //  orderByCateModelArrayList=new ArrayList<>();



       // ClearAll();
        GetDataFromFirebaserRealtimeDatabaseInImageSliderRecyclerview();
        //GetDataFromFirebaserRealtimeDatabaseInOrderByCateRecyclerview();
        //GetDataFromFirebaseFirestore();







//      /*  notifiImageSliderList=new ArrayList<NotifiImageSlider>();
//        notifiImageSliderList.add(new NotifiImageSlider(photoReference));

     /*   firebaseFirestore.collection("notifi_image_slider").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                // inside the on success method we are running a for loop
                // and we are getting the data from Firebase Firestore
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                    // after we get the data we are passing inside our object class.
                   // SliderData sliderData = documentSnapshot.toObject(SliderData.class);
                    NotifiImageSlider notifiImageSliderData=documentSnapshot.toObject(NotifiImageSlider.class);
                  //  SliderData model = new SliderData();
                    NotifiImageSlider notifiImageSlider=new NotifiImageSlider();

                    // below line is use for setting our
                    // image url for our modal class.
                  //  model.setImgUrl(sliderData.getImgUrl());
                    notifiImageSlider.setImgUrl(notifiImageSliderData.getImgUrl());

                    // after that we are adding that
                    // data inside our array list.
                  //  sliderDataArrayList.add(model);
                    notifiImageSliderAdapter =new NotifiImageSliderAdapter(HomeFragment.this,notifiImageSliderList);
                    recyclerView.setAdapter(notifiImageSliderAdapter);
                    notifiImageSliderList.add(notifiImageSlider);
                    // after adding data to our array list we are passing
                    // that array list inside our adapter class.
                   // adapter = new SliderAdapter(MainActivity.this, sliderDataArrayList);
                   // notifiImageSliderAdapter =new NotifiImageSliderAdapter(notifiImageSliderList);
                    // belows line is for setting adapter
                    // to our slider view
                  //  sliderView.setSliderAdapter(adapter);
                    //recyclerView.setAdapter(notifiImageSliderAdapter);
                    // below line is for setting animation to our slider.
//                    sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
//
//                    // below line is for setting auto cycle duration.
//                    sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
//
//                    // below line is for setting
//                    // scroll time animation
//                    sliderView.setScrollTimeInSec(3);
//
//                    // below line is for setting auto
//                    // cycle animation to our slider
//                    sliderView.setAutoCycle(true);
//
//                    // below line is use to start
//                    // the animation of our slider view.
//                    sliderView.startAutoCycle();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // if we get any error from Firebase we are
                // displaying a toast message for failure
               // Toast.makeText(HomeFragment.this, "Fail to load slider data..", Toast.LENGTH_SHORT).show();
            }
        });

        */


        return view;
    }
  ///.......................... GET IMAGES IN RECYCLERVEIW FROM FIREBASE REALTIME DATABASE.........................................
  private void GetDataFromFirebaserRealtimeDatabaseInImageSliderRecyclerview()
    {
        DatabaseReference databaseReference=firebaseDatabase.getReference();
        Query query=databaseReference.child("images");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    NotifiImageSlider notifiImageSlider=new NotifiImageSlider();
                    notifiImageSlider.setImgUrl(snapshot.getValue().toString());
                    notifiImageSliderList.add(notifiImageSlider);
                }
             notifiImageSliderAdapter=new NotifiImageSliderAdapter(context,notifiImageSliderList);
                notifi_image_slider_recyclerView.setAdapter(notifiImageSliderAdapter);
                notifiImageSliderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void ClearAll()
    {
           if(notifiImageSliderList!=null) {
               notifiImageSliderList.clear();
               if(notifiImageSliderAdapter!=null)
               {
                   notifiImageSliderAdapter.notifyDataSetChanged();
               }
           }
           notifiImageSliderList=new ArrayList<>();
    }
   /* private void GetDataFromFirebaserRealtimeDatabaseInOrderByCateRecyclerview()
    {
        DatabaseReference databaseReference=firebaseDatabase.getReference();
        Query query=databaseReference.child("order_by_cate");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAllInOrderByCateRecyclerView();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    OrderByCateModel orderByCateModel=new OrderByCateModel();
                    orderByCateModel.setImgUrl(snapshot.child("cate_image").getValue().toString());
                    orderByCateModel.setCateName(snapshot.child("cate_name").getValue().toString());
                    orderByCateModelArrayList.add(orderByCateModel);


                }

                orderByCateAdapter=new OrderByCateAdapter(orderByCateModelArrayList, context);
                order_by_cate_recylerView.setAdapter(orderByCateAdapter);
                orderByCateAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void ClearAllInOrderByCateRecyclerView()
    {
        if(orderByCateModelArrayList!=null) {
            orderByCateModelArrayList.clear();
            if(orderByCateAdapter!=null)
            {
                orderByCateAdapter.notifyDataSetChanged();
            }
        }
        orderByCateModelArrayList=new ArrayList<>();
    }
private void GetDataFromFirebaseFirestore() {
    /*
    firebaseFirestore.collection("notify_image_slider").addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
            if (error != null) {
//                if (progressDialog.isShowing())
//                    progressDialog.dismiss();

                Log.e("firestore error", error.getMessage());
                return;
            }
            for (DocumentChange dc : value.getDocumentChanges()) {
                if (dc.getType() == DocumentChange.Type.ADDED) {
                    notifiImageSliderList.add(dc.getDocument().toObject(NotifiImageSlider.class));
                }
                notifiImageSliderAdapter.notifyDataSetChanged();
//                if (progressDialog.isShowing())
//                    progressDialog.dismiss();
            }

           notifiImageSliderAdapter.notifyDataSetChanged();
        }
    });

   }*/



   /* firebaseFirestore.collection("SliderData").get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    // after getting the data we are calling on success method
                    // and inside this method we are checking if the received
                    // query snapshot is empty or not.
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // if the snapshot is not empty we are hiding our
                        // progress bar and adding our data in a list.
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            // after getting this list we are passing
                            // that list to our object class.
                            notifiImageSliderList.add(d.toObject(NotifiImageSlider.class));
                            // after getting data from Firebae we are
                            // storing that data in our array list

                        }
                        // below line is use to add our array list to adapter class.
                       NotifiImageSliderAdapter notifiImageSliderAdapter=new NotifiImageSliderAdapter(HomeFragment.this,notifiImageSliderList);

                        // below line is use to set our
                        // adapter to our view pager.
                      recyclerView.setAdapter(notifiImageSliderAdapter);
                        // we are storing the size of our
                        // array list in a variable.

                    } else {
                        // if the snapshot is empty we are displaying a toast message.
                        //Toast.makeText(MainActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                    }
                }
            });
}

    */


}