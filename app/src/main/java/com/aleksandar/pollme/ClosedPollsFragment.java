package com.aleksandar.pollme;

import android.os.Bundle;

import androidx.annotation.NonNull;
import android.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClosedPollsFragment extends Fragment {

    private DatabaseReference database;
    private String DATABASE_NAME;
    private RecyclerView recView;

    public ClosedPollsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_closed_polls, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DATABASE_NAME = this.getString(R.string.database);
        ArrayList<Poll> pollList = new ArrayList<Poll>();
        database = FirebaseDatabase.getInstance(DATABASE_NAME).getReference();
        recView = getView().findViewById(R.id.recViewClosedPolls);

        pollList.clear();
        database.child("polls").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    Poll poll = postSnapshot.getValue(Poll.class);
                    pollList.add(poll);
                }
                ArrayList<Poll> tempPollList = new ArrayList<Poll>();
                for(Poll poll : pollList) {
                    if(poll.timeLeft().equals("00:00:00")) {
                        tempPollList.add(poll);
                    }
                }
                PollViewAdapter adapter = new PollViewAdapter(tempPollList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                recView.setLayoutManager(layoutManager);
                recView.setItemAnimator(new DefaultItemAnimator());
                recView.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Greska", Toast.LENGTH_SHORT).show();
            }
        });
    }
}