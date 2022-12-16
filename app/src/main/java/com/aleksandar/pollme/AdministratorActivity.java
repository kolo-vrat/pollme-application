package com.aleksandar.pollme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdministratorActivity extends AppCompatActivity {

    private DatabaseReference database;
    private String DATABASE_NAME;
    private EditText adminPassword;
    private Button btnOk;
    private LinearLayout lytButtons;
    private ImageView logo;
    private Button btnAddPoll;
    private Button btnOpenPolls;
    private Button btnClosedPolls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);

        DATABASE_NAME = this.getString(R.string.database);
        btnOk = (Button) findViewById(R.id.login);
        adminPassword = (EditText) findViewById(R.id.adminPasswordLogin);
        lytButtons = (LinearLayout) findViewById(R.id.lytAdminButtons);
        logo = (ImageView) findViewById(R.id.logo);
        database = FirebaseDatabase.getInstance(DATABASE_NAME).getReference();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adminPassword.getText().toString().length() == 0) {
                    Toast.makeText(AdministratorActivity.this, "Poleto za lozinka ne smee da bide prazno", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Integer password = adminPassword.getText().toString().hashCode();
                    database.child("admin").child("password").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Integer passwordDatabase = snapshot.getValue(Integer.class);
                            if (passwordDatabase == null) {
                                Toast.makeText(AdministratorActivity.this, "Greska vo databaza", Toast.LENGTH_LONG).show();
                            } else {
                                if (!password.equals(passwordDatabase)) {
                                    Toast.makeText(AdministratorActivity.this, "Pogresna lozinka", Toast.LENGTH_LONG).show();
                                } else {
                                    closeKeyboard();
                                    adminPassword.setVisibility(View.GONE);
                                    logo.setVisibility(View.GONE);
                                    btnOk.setVisibility(View.GONE);
                                    lytButtons.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(AdministratorActivity.this, "Greska so databaza", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if(view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}