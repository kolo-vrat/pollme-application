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

public class LoginActivity extends AppCompatActivity {

    private String DATABASE_NAME;
    private DatabaseReference database;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DATABASE_NAME = this.getString(R.string.database);
        database = FirebaseDatabase.getInstance(DATABASE_NAME).getReference();
    }

    public void btnLoginOkClick(View view) {
        email = (EditText) findViewById(R.id.emailLogin);
        password = (EditText) findViewById(R.id.passwordLogin);

        if( email.getText().toString().trim().length() == 0 ||
            password.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vnesi gi site informacii", Toast.LENGTH_LONG).show();
            return;
        }

        String emailAddress = email.getText().toString().replaceAll("\\.", ",");
        database.child("users").child(emailAddress).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user == null) {
                    Toast.makeText(LoginActivity.this, "Korisnikot ne postoi", Toast.LENGTH_LONG).show();
                } else {
                    if (password.getText().toString().hashCode() != user.getPassword()) {
                        Toast.makeText(LoginActivity.this, "Pogresna lozinka", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("email", email.getText().toString());
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Greska so databaza", Toast.LENGTH_LONG).show();
            }
        });
    }
}
