package com.example.farmorganic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.farmorganic.databinding.ActivityEmailRegiBinding;
import com.example.farmorganic.models.Users;
import com.google.android.gms.auth.api.identity.SignInClient;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class EmailRegiActivity extends AppCompatActivity {

    ActivityEmailRegiBinding binding;
    FirebaseAuth firebaseAuth ;
    FirebaseFirestore firebaseFirestore;
    FirebaseDatabase firebaseDatabase;
    GoogleSignInClient mGoogleSignInClient;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmailRegiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(EmailRegiActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating your account");

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);




        Objects.requireNonNull(getSupportActionBar()).hide();




        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();
                String email = binding.email.getText().toString();
                String reEnterPassword = binding.reEnterPassword.getText().toString();
                String username = binding.username.getText().toString();
                String enterPassword = binding.enterPassword.getText().toString();


                if (TextUtils.isEmpty(username)) {
                    binding.username.setError("please enter username");
                    // Toast.makeText(EmailRegiActivity.this, " please enter username", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(email)) {
                    binding.email.setError("please enter email");
                    //  Toast.makeText(EmailRegiActivity.this, " please enter email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(enterPassword)) {
                    binding.enterPassword.setError("please enter password");
                    /// Toast.makeText(EmailRegiActivity.this, " please enter password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(reEnterPassword)) {
                    binding.reEnterPassword.setError("please re-enter password");
                    // Toast.makeText(EmailRegiActivity.this, " please enter password again", Toast.LENGTH_SHORT).show();
                } else if (!enterPassword.equals(reEnterPassword)) {
                    // binding.reEnterPassword.setError("password do not equal");
                    Toast.makeText(EmailRegiActivity.this, " password do not equal", Toast.LENGTH_SHORT).show();
                } else {


                    firebaseAuth.createUserWithEmailAndPassword(binding.email.getText().toString(), binding.reEnterPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(EmailRegiActivity.this, "User Created Successfully", Toast.LENGTH_SHORT).show();

                                Users users = new Users(username, email, reEnterPassword);

                                String id = task.getResult().getUser().getUid();
                                firebaseDatabase.getReference().child("Users").child(id).setValue(users);

                                Intent intent = new Intent(EmailRegiActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(EmailRegiActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                }

            }

        });




        binding.forSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmailRegiActivity.this, EmailLoginActivity.class);
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
                Intent intent=new Intent(EmailRegiActivity.this,OtpVeriActivity.class);
                startActivity(intent);
            }
        });





        if(firebaseAuth.getCurrentUser()!=null)
        {
            Intent intent=new Intent(EmailRegiActivity.this,MainActivity.class);
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
                            // Sign in success, update UI with the signed-in user's information
                            //Toast.makeText(EmailRegiActivity.this,"Google registretion is successfull",Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent=new Intent(EmailRegiActivity.this,MainActivity.class);
                            startActivity(intent);
                            //  updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            //   updateUI(null);
                        }
                    }
                });
    }
}