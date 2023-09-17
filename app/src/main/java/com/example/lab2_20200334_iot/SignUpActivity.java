package com.example.lab2_20200334_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab2_20200334_iot.databinding.ActivityMainBinding;
import com.example.lab2_20200334_iot.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toast.makeText(SignUpActivity.this, "Create Account page", Toast.LENGTH_SHORT).show();
        binding.createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameEditText =  binding.nameTextField.getEditText();
                EditText lastNameEditText =  binding.lastNameTextField.getEditText();
                EditText emailEditText =  binding.emailTextField.getEditText();
                EditText passwordEditText =  binding.passwordTextField.getEditText();
                boolean checkBoxBoolean = binding.checkBox.isChecked();
                boolean allFieldsFilled = (nameEditText!=null) && (lastNameEditText!=null) && (emailEditText!=null) && (passwordEditText!=null) && checkBoxBoolean;
                if (allFieldsFilled) {
                    Intent intent = new Intent(SignUpActivity.this, MenuActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignUpActivity.this, "Complete todos los campos para continuar", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}