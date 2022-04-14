package com.example.farmorganic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.farmorganic.databinding.ActivityEmailLoginBinding;
import com.example.farmorganic.models.Users;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

import java.util.Objects;

public class EmailLoginActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    ProgressDialog progressDialog;
    ActivityEmailLoginBinding binding;

    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEmailLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog=new ProgressDialog(EmailLoginActivity.this);
        progressDialog.setTitle("Sign In Account");
        progressDialog.setMessage("We are sign in  your account");


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        binding.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             progressDialog.show();
             firebaseAuth.signInWithEmailAndPassword(binding.email.getText().toString(),binding.enterPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     progressDialog.dismiss();
                     if (task.isSuccessful()) {
                         Intent intent = new Intent(EmailLoginActivity.this, MainActivity.class);
                         startActivity(intent);
                     } else {
                         Toast.makeText(EmailLoginActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 }
             });
            }
        });

     binding.forRegister.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent=new Intent(EmailLoginActivity.this,EmailRegiActivity.class);
             startActivity(intent);
         }
     });



     binding.google.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
           signIn();

         }
     });

        binding.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EmailLoginActivity.this,OtpVeriActivity.class);
                startActivity(intent);
            }
        });




        if(firebaseAuth.getCurrentUser()!=null)
        {
            Intent intent=new Intent(EmailLoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }
    int RC_SIGN_IN=65;
    private void signIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching ntent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            firebaseUser=firebaseAuth.getCurrentUser();
                            Users users=new Users();
                            users.setUserid(firebaseUser.getUid());
                            users.setUsername(firebaseUser.getDisplayName());
                            users.setProfileImg(firebaseUser.getPhotoUrl().toString());

                            firebaseDatabase.getReference().child("Users").child(firebaseUser.getUid()).setValue(users);






                            // Sign in success, update UI with the signed-in user's information
                          //  Toast.makeText(EmailLoginActivity.this,"Google registretion is successfull",Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "signInWithCredential:success");

                          //  updateUI(user);
                            Intent intent=new Intent(EmailLoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                         //   updateUI(null);
                        }
                    }
                });
    }
}