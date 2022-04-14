package com.example.farmorganic;

import static androidx.navigation.Navigation.findNavController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.farmorganic.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.Contract;

public class MainActivity extends AppCompatActivity  {
    ActivityMainBinding binding;


    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private BottomNavigationView bnv;

    // nav_header id....................
    Button setProfileBackImage,setProfileImage;
    EditText username,about;
    ImageView profileBackImage,profileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        //..............Firebase getInstance....................
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();




        /// for default home fragment ..........
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView,new HomeFragment());
        fragmentTransaction.commit();


        // initialize all.........
        drawerLayout = findViewById(R.id.activity_main);
        navigationView = findViewById(R.id.navigationView);
        bnv=findViewById(R.id.bottomNavigationView);
        setProfileBackImage=findViewById(R.id.setProfileBackImage);
        setProfileImage=findViewById(R.id.setProfileImage);
        username=findViewById(R.id.username);
        about=findViewById(R.id.about);
        profileBackImage=findViewById(R.id.profileBackImage);
        profileImage=findViewById(R.id.profileImage);




        //for navigation drawer..................
        actionBarDrawerToggle=new ActionBarDrawerToggle(MainActivity.this,drawerLayout,R.string.open,R.string.close);
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
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                switch (item.getItemId())
                {
                    case R.id.home:

                        fragmentTransaction.replace(R.id.fragmentContainerView,new HomeFragment());

                        Toast.makeText(MainActivity.this, "home bnv", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.search:

                        fragmentTransaction.replace(R.id.fragmentContainerView,new SearchFragment());

                        Toast.makeText(MainActivity.this, "search bnv", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.cart:

                        fragmentTransaction.replace(R.id.fragmentContainerView,new CartFragment());

                        Toast.makeText(MainActivity.this, "cart bnv", Toast.LENGTH_SHORT).show();
                        break;
                }
                fragmentTransaction.commit();
                return true;//if it is false not color change of icon
            }

        });


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
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
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
}