package com.example.farmorganic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class FlashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);

        getSupportActionBar().hide();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser()!=null)
                {

                    Thread td=new Thread() {
                        public void run() {
                            try {
                                //sleep(5);

                            } catch (Exception e) {
                                e.printStackTrace();

                            } finally {
                                Intent intent=new Intent(FlashScreenActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    };td.start();

                }
                else
                {
                    Intent intent=new Intent(FlashScreenActivity.this,EmailLoginActivity.class);
                    startActivity(intent);

                }

            }
        } ,3000);
    }
}