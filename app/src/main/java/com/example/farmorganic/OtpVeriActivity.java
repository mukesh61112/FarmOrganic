package com.example.farmorganic;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class OtpVeriActivity extends AppCompatActivity {



    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    CountryCodePicker countryCodePicker;
    String verificationId;
    EditText enterNum,enterOtp;
    Button getOtp,verifyOtp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_veri);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();


        getOtp=findViewById(R.id.get_otp);
        verifyOtp=findViewById(R.id.veritfy_otp);
        enterNum=findViewById(R.id.enter_num);
        enterOtp=findViewById(R.id.enter_otp);
        countryCodePicker=findViewById(R.id.countypicker);
        getOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(enterNum.getText().toString()))
                {
                    Toast.makeText(OtpVeriActivity.this," enter phone number",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String phone="+91"+enterNum.getText().toString();
                    sendVerificationCode(phone);
                }
            }
        });
        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(enterOtp.getText().toString()))
                {
                    Toast.makeText(OtpVeriActivity.this,"please enter otp",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    verifyCode(enterOtp.getText().toString());
                }
            }
        });

    }
    public void sendVerificationCode(String phone)
    {
        PhoneAuthOptions phoneAuthOptions=PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallBack)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s,PhoneAuthProvider.ForceResendingToken forceResendingToken)
        {
            super.onCodeSent(s,forceResendingToken);
            verificationId=s;
        }
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            final String code=phoneAuthCredential.getSmsCode();
            if(code!=null)
            {
                enterOtp.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OtpVeriActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();;

        }
    };
    private void verifyCode(String code)
    {
        PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(verificationId,code);
        signInWithCredential(phoneAuthCredential);
    }
    private void signInWithCredential(PhoneAuthCredential phoneAuthCredential)
    {
        firebaseAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            Intent intent=new Intent(OtpVeriActivity.this, MainActivity.class);
                            startActivity(intent);

                        }
                        else{
                            Toast.makeText(OtpVeriActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}