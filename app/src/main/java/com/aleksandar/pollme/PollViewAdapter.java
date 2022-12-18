package com.aleksandar.pollme;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class PollViewAdapter extends RecyclerView.Adapter<PollViewAdapter.MyViewHolder> {

    ArrayList<Poll> pollList;

    public PollViewAdapter(ArrayList<Poll> pollList) {
        this.pollList = pollList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private LinearLayout allAnswers;
        private TextView timeLeft;

        public MyViewHolder(final View view){
            super(view);
            title = view.findViewById(R.id.titlePoll);
            allAnswers = view.findViewById(R.id.answersFromUsers);
            timeLeft = view.findViewById(R.id.timeLeft);
        }
    }

    @NonNull
    @Override
    public PollViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.poll_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PollViewAdapter.MyViewHolder holder, int position) {
        String title = pollList.get(position).getTitle();
        String pollTimeLeft = pollList.get(position).timeLeft();
        if (pollList.get(position).getAnswers() != null) {
            Set<String> keys = pollList.get(position).getAnswers().keySet();
            for (String item : keys) {
                HashMap<String, HashMap<String, String>> userAnswers = pollList.get(position).getAnswers().get(item);
                TextView userEmail = new TextView(holder.allAnswers.getContext());
                userEmail.setText(item);
                holder.allAnswers.addView(userEmail);
                for (String qstn : userAnswers.keySet()) {
                    TextView questionText = new TextView(holder.allAnswers.getContext());
                    questionText.setText(qstn);
                    holder.allAnswers.addView(questionText);
                    for (String answ : userAnswers.get(qstn).keySet()) {
                        TextView answerText = new TextView(holder.allAnswers.getContext());
                        answerText.setText(userAnswers.get(qstn).get(answ));
                    }
                }
            }
        } else {
            holder.allAnswers.removeAllViews();
        }

        holder.title.setText(title);
        holder.timeLeft.setText(pollTimeLeft);

    }

    @Override
    public int getItemCount() {
        return pollList.size();
    }
}
