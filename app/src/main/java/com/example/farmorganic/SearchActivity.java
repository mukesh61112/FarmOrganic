package com.example.farmorganic;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        BottomNavigationView bnv=findViewById(R.id.bottomNavigationView);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent1=new Intent(SearchActivity.this,MainActivity.class);
                        startActivity(intent1);

                        break;
                    case R.id.search:

//                        Intent intent2=new Intent(SearchActivity.this,SearchActivity.class);
//                        startActivity(intent2);

                        break;
                    case R.id.cart:

                        Intent intent3=new Intent(SearchActivity.this,CartActivity.class);

                        startActivity(intent3);
                        break;
                }


                return true;//if it is false not color change of icon
            }

        });

    }
}