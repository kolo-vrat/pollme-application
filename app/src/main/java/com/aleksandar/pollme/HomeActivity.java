package com.aleksandar.pollme;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle extra = getIntent().getExtras();
        String email = extra.getString("email");
        Toast.makeText(this, "Welcome " + email, Toast.LENGTH_LONG).show();

        Fragment newFragment = new HomeFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frLayoutHome, newFragment);
        transaction.commit();
    }
}
