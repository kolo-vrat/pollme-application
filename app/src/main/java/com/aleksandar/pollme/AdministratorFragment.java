package com.aleksandar.pollme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdministratorFragment extends Fragment {

    private DatabaseReference database;
    private String DATABASE_NAME;
    private EditText adminPassword;
    private Button btnOk;
    private LinearLayout lytButtons;
    private ImageView logo;
    private Button btnAddPoll;
    private Button btnOpenPolls;
    private Button btnClosedPolls;

    public AdministratorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_administrator, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DATABASE_NAME = this.getString(R.string.database);
        btnOk = (Button) getView().findViewById(R.id.login);
        adminPassword = (EditText) getView().findViewById(R.id.adminPasswordLogin);
        lytButtons = (LinearLayout) getView().findViewById(R.id.lytAdminButtons);
        logo = (ImageView) getView().findViewById(R.id.logo);
        database = FirebaseDatabase.getInstance(DATABASE_NAME).getReference();
        btnAddPoll = (Button) getView().findViewById(R.id.addpoll);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adminPassword.getText().toString().length() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "Poleto za lozinka ne smee da bide prazno", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Integer password = adminPassword.getText().toString().hashCode();
                    database.child("admin").child("password").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Integer passwordDatabase = snapshot.getValue(Integer.class);
                            if (passwordDatabase == null) {
                                Toast.makeText(getActivity().getApplicationContext(), "Greska vo databaza", Toast.LENGTH_LONG).show();
                            } else {
                                if (!password.equals(passwordDatabase)) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Pogresna lozinka", Toast.LENGTH_LONG).show();
                                } else {
                                    adminPassword.setVisibility(View.GONE);
                                    logo.setVisibility(View.GONE);
                                    btnOk.setVisibility(View.GONE);
                                    lytButtons.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getActivity().getApplicationContext(), "Greska so databaza", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        btnAddPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddPollFragment();
                ((AdministratorActivity) getActivity()).changeLayout(fragment);
            }
        });
    }
}