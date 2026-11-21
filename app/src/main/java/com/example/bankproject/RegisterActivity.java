package com.example.bankproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.bankproject.models.Customer;
import com.example.bankproject.network.RetrofitClient;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText etFirstName = findViewById(R.id.etRegFirstName);
        EditText etLastName = findViewById(R.id.etRegLastName);
        EditText etEmail = findViewById(R.id.etRegEmail);
        EditText etPhone = findViewById(R.id.etRegPhone);
        EditText etPassword = findViewById(R.id.etRegPassword);
        Button btnRegister = findViewById(R.id.btnRegister);
        TextView tvGoToLogin = findViewById(R.id.tvGoToLogin);

        btnRegister.setOnClickListener(v -> {
            Customer c = new Customer();
            c.setFirstName(etFirstName.getText().toString());
            c.setLastName(etLastName.getText().toString());
            c.setEmail(etEmail.getText().toString());
            c.setPhone(etPhone.getText().toString());
            c.setPassword(etPassword.getText().toString());

            if (c.getEmail().isEmpty() || c.getPassword().isEmpty()) {
                Toast.makeText(this, "Veuillez remplir les champs obligatoires", Toast.LENGTH_SHORT).show();
                return;
            }

            RetrofitClient.getApi().addCustomer(c).enqueue(new Callback<Customer>() {
                @Override
                public void onResponse(Call<Customer> call, Response<Customer> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Inscription réussie !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Erreur lors de l'inscription", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Customer> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Erreur réseau", Toast.LENGTH_SHORT).show();
                }
            });
        });

        tvGoToLogin.setOnClickListener(v -> finish());
    }
}