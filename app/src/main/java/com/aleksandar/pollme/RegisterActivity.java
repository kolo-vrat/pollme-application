package com.aleksandar.pollme;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private String DATABASE_NAME;
    private DatabaseReference database;
    private EditText ime;
    private EditText prez;
    private EditText email;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DATABASE_NAME = this.getString(R.string.database);
        database = FirebaseDatabase.getInstance(DATABASE_NAME).getReference();
    }

    public void btnRegOkClick(View view) {
        ime = (EditText) findViewById(R.id.ime);
        prez = (EditText) findViewById(R.id.prez);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        if( ime.getText().toString().trim().length() == 0 ||
            prez.getText().toString().trim().length() == 0 ||
            email.getText().toString().trim().length() == 0 ||
            password.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Site polinja se zadolzitelni", Toast.LENGTH_LONG).show();
            return;
        }

        String emailAddress = email.getText().toString().replaceAll("\\.", ",");
        database.child("users").child(emailAddress).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user == null) {
                    User usr = new User(ime.getText().toString(), prez.getText().toString(), password.getText().toString());
                    database.child("users").child(emailAddress).setValue(usr);
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    intent.putExtra("email", email.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Korisnikot " + email.getText().toString() + " vekje postoi", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RegisterActivity.this, "Greska so databaza", Toast.LENGTH_LONG).show();
            }
        });
    }
}
