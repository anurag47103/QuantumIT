package com.learningandroid.quantumit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.learningandroid.quantumit.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.socialTextView.bringToFront();

        binding.loginCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new LoginFragment());
                binding.loginCardView.setCardBackgroundColor(getColor(R.color.app_red));
                binding.loginTextView.setTextColor(getColor(R.color.white));
                binding.signupCardView.setCardBackgroundColor(getColor(R.color.white));
                binding.signupTextView.setTextColor(getColor(R.color.app_grey));
                binding.bottomloginTextView.setText("LOGIN");
            }
        });

        binding.signupCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new SignUpFragment());
                binding.loginCardView.setCardBackgroundColor(getColor(R.color.white));
                binding.loginTextView.setTextColor(getColor(R.color.app_grey));
                binding.signupCardView.setCardBackgroundColor(getColor(R.color.app_red));
                binding.signupTextView.setTextColor(getColor(R.color.white));
                binding.bottomloginTextView.setText("REGISTER");
            }
        });
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();

    }
}