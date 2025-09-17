package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsapp.Models.Users;
import com.example.whatsapp.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Please wait...");

        binding.btnSignUp.setOnClickListener(v -> {
            if (!binding.txtUsername.getText().toString().isEmpty() &&
                    !binding.txtEmail.getText().toString().isEmpty() &&
                    !binding.txtPassword.getText().toString().isEmpty()) {

                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(
                        binding.txtEmail.getText().toString(),
                        binding.txtPassword.getText().toString()
                ).addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        String id = task.getResult().getUser().getUid();
                        Users user = new Users();
                        user.setUserId(id);
                        user.setUserName(binding.txtUsername.getText().toString());
                        user.setMail(binding.txtEmail.getText().toString());
                        user.setStatus("Hey there! I am using WhatsApp");

                        // Save user in database (without password for security)
                        database.getReference().child("Users").child(id).setValue(user);

                        Toast.makeText(SignUp.this, "Sign up successful!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(SignUp.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(SignUp.this, "Enter all details!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.txtAlreadyAccount.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, SignIn.class);
            startActivity(intent);
            finish();
        });
    }
}
