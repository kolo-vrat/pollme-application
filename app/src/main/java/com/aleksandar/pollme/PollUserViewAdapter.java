package com.aleksandar.pollme;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PollUserViewAdapter extends RecyclerView.Adapter<PollUserViewAdapter.MyViewHolder> {

    ArrayList<Poll> pollList;

    public PollUserViewAdapter(ArrayList<Poll> pollList) {
        this.pollList = pollList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView timeLeft;
        private RelativeLayout lytContainer;

        public MyViewHolder(final View view) {
            super(view);
            title = view.findViewById(R.id.pollTitleHome);
            timeLeft = view.findViewById(R.id.timeLeftHome);
            lytContainer = view.findViewById(R.id.pollContainer);
        }
    }

    @NonNull
    @Override
    public PollUserViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.poll_user_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PollUserViewAdapter.MyViewHolder holder, int position) {
        String title = pollList.get(position).getTitle();
        String pollTimeLeft = pollList.get(position).timeLeft();
        holder.title.setText(title);
        holder.timeLeft.setText(pollTimeLeft);
        Context con = holder.lytContainer.getContext();
        holder.lytContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(con, VoteActivity.class);
                intent.putExtra("pollTitle", title);
                con.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pollList.size();
    }
}
