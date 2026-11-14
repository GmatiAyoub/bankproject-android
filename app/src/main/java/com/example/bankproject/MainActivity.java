package com.example.bankproject;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.bankproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // On récupère la BottomNavigationView grâce à l'ID défini dans activity_main.xml
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Configuration avec les ID de TON nav_graph.xml
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.FirstFragment, R.id.SecondFragment, R.id.nav_customer,R.id.nav_account)
                .build();

        // On récupère le NavController grâce à l'ID du fragment défini dans activity_main.xml
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
}