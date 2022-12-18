package com.aleksandar.pollme;

import android.app.FragmentTransaction;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;

public class AdministratorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);

        Fragment newFragment = new AdministratorFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frLayout, newFragment);
        transaction.commit();
    }

    protected void changeLayout(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frLayout, fragment);
        transaction.commit();
    }
}