package com.example.farmorganic;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmorganic.adapters.NotifiImageSliderAdapter;
import com.example.farmorganic.adapters.OrderByCateAdapter;
import com.example.farmorganic.databinding.ActivityMainBinding;
import com.example.farmorganic.models.NotifiImageSlider;
import com.example.farmorganic.models.OrderByCateModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
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

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private BottomNavigationView bnv;

    // nav_header id....................
    Button setProfileBackImage, setProfileImage;
    EditText username, about;
    ImageView profileBackImage, profileImage;
    RecyclerView notifi_image_slider_recyclerView;
    RecyclerView order_by_cate_recylerView;


    ArrayList<NotifiImageSlider> notifiImageSliderList;
    ArrayList<OrderByCateModel> orderByCateModelArrayList;


    NotifiImageSliderAdapter notifiImageSliderAdapter;
    OrderByCateAdapter orderByCateAdapter;


    NotifiImageSlider notifiImageSlider;
    OrderByCateModel orderByCateModel;


    RecyclerView.LayoutManager notifiImageSliderlayoutManager;
    RecyclerView.LayoutManager orderByCatelayoutManager;


    Context context;


    private StorageReference storageReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseFirestore firebaseFirestore;
    DatabaseReference databaseReference;
    DatabaseReference firestoreDatabaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;


    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //..............Firebase getInstance....................
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        // initialize all.........
        drawerLayout = findViewById(R.id.activity_main);
        navigationView = findViewById(R.id.navigationView);
        bnv = findViewById(R.id.bottomNavigationView);
        setProfileBackImage = findViewById(R.id.setProfileBackImage);
        setProfileImage = findViewById(R.id.setProfileImage);
        username = findViewById(R.id.username);
        about = findViewById(R.id.about);
        profileBackImage = findViewById(R.id.profileBackImage);
        profileImage = findViewById(R.id.profileImage);


        //for navigation drawer..................
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });


        // .......show fragment when click on bnv button.....item........
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
//                    Intent intent1=new Intent(MainActivity.this,MainActivity.class);
//                    startActivity(intent1);

                        break;
                    case R.id.search:

                        Intent intent2=new Intent(MainActivity.this,SearchActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.cart:

                        Intent intent3=new Intent(MainActivity.this,CartActivity.class);
                        startActivity(intent3);
                        break;
                }

                return true;//if it is false not color change of icon
            }

        });

        //disableShiftMode(bnv);


        //............get Insert pofile image  in Firebase........................


//        setProfileBackImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                MainActivity.this.startActivityForResult(intent, 40);
//            }
//        });








        notifi_image_slider_recyclerView=findViewById(R.id.notifi_image_recyclerview);
        order_by_cate_recylerView=findViewById(R.id.order_by_cate_recyclerview);



        // ..........process dialog for firebase firestore...........................
//         progressDialog=new ProgressDialog(HmeFragment.this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("fetching data");
//        progressDialog.show();



        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        notifiImageSliderlayoutManager=new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,true);
         orderByCatelayoutManager=new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);

        notifi_image_slider_recyclerView.setLayoutManager(notifiImageSliderlayoutManager);
         order_by_cate_recylerView.setLayoutManager(orderByCatelayoutManager);

        notifiImageSliderList=new ArrayList<>();
         orderByCateModelArrayList=new ArrayList<>();



        // ClearAll();
        GetDataFromFirebaserRealtimeDatabaseInImageSliderRecyclerview();
        GetDataFromFirebaserRealtimeDatabaseInOrderByCateRecyclerview();
        //GetDataFromFirebaseFirestore();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }





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
    private void GetDataFromFirebaserRealtimeDatabaseInOrderByCateRecyclerview()
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
/*
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShifting(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }*/
}

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(data.getData()!=null)
//        {
//            Uri uri=data.getData();
//            profileBackImage.setImageURI(uri);
//            final StorageReference storageReference=firebaseStorage.getReference()
//                    .child("profileBackImage").child(firebaseAuth.getUid());
//            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(MainActivity.this, "image uploaded", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        }
//    }
