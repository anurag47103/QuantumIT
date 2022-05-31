package com.learningandroid.quantumit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.learningandroid.quantumit.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "check";
    ActivityMainBinding binding;
    FirebaseAuth mAuth;
    LoginFragment loginFragment;
    SignUpFragment signUpFragment;

    boolean login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        loginFragment = new LoginFragment();
        signUpFragment = new SignUpFragment();
        replaceFragment(loginFragment);

        binding.socialTextView.bringToFront();
        login = true;

        binding.loginCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(loginFragment);
                binding.loginCardView.setCardBackgroundColor(getColor(R.color.app_red));
                binding.loginTextView.setTextColor(getColor(R.color.white));
                binding.signupCardView.setCardBackgroundColor(getColor(R.color.white));
                binding.signupTextView.setTextColor(getColor(R.color.app_grey));
                binding.bottomloginTextView.setText("LOGIN");
                login = true;
            }
        });

        binding.signupCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpFragment = new SignUpFragment();
                replaceFragment(signUpFragment);
                binding.loginCardView.setCardBackgroundColor(getColor(R.color.white));
                binding.loginTextView.setTextColor(getColor(R.color.app_grey));
                binding.signupCardView.setCardBackgroundColor(getColor(R.color.app_red));
                binding.signupTextView.setTextColor(getColor(R.color.white));
                binding.bottomloginTextView.setText("REGISTER");
                login = false;
            }
        });

        binding.bottomLoginCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(login) {
                    String email = loginFragment.getEmail();
                    String password = loginFragment.getPassword();
                    loginemailPasswordUser(email,password);
                }
                else {
                    String email = signUpFragment.getEmail();
                    String password = signUpFragment.getPassword();
                    String username = signUpFragment.getUserName();
                    createUserEmailAccount(email,password,username);
                }
            }
        });




    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();

    }

    private void loginemailPasswordUser(String email, String pwd) {
        if(!TextUtils.isEmpty(email) &&
                !TextUtils.isEmpty(pwd)) {

            mAuth.signInWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, ListNewsActivity.class));
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                            }


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
        else {
            Toast.makeText(MainActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
        }
    }

    private void createUserEmailAccount(String email, String password, String username) {


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "cant register" , Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "auth fail - " + e);
                        Log.e(TAG, "email - "+ email);
                    }
                });
    }
}