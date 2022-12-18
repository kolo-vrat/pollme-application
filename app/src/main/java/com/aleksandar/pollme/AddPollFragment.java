package com.aleksandar.pollme;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class AddPollFragment extends Fragment {

    private DatabaseReference database;
    private String DATABASE_NAME;
    private LinearLayout lytQuestions;
    private Button btnAddQuestion;
    private Button btnAddAnswer;
    private Button btnAddPoll;
    private EditText pollTitle;
    private EditText endTime;

    public AddPollFragment() {
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
        return inflater.inflate(R.layout.fragment_add_poll, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DATABASE_NAME = this.getString(R.string.database);
        lytQuestions = (LinearLayout) getView().findViewById(R.id.pollQuestions);
        btnAddQuestion = (Button) getView().findViewById(R.id.addQuestion);
        btnAddAnswer = (Button) getView().findViewById(R.id.questionAddAnswer);
        btnAddPoll = (Button) getView().findViewById(R.id.addThePoll);
        pollTitle = (EditText) getView().findViewById(R.id.pollTitle);
        endTime = (EditText) getView().findViewById(R.id.endTime);
        database = FirebaseDatabase.getInstance(DATABASE_NAME).getReference();

        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numQuestions = lytQuestions.getChildCount();
                LinearLayout newQuestion = new  LinearLayout(getActivity().getApplicationContext());
                newQuestion.setOrientation(LinearLayout.VERTICAL);

                EditText questionTitle = new EditText(getActivity().getApplicationContext());
                questionTitle.setHint("Prashanje " + Integer.toString(numQuestions+1));

                TextView answersText = new TextView(getActivity().getApplicationContext());
                answersText.setText("Odgovori");
                answersText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                answersText.setTypeface(null, Typeface.BOLD);

                LinearLayout answers = new LinearLayout(getActivity().getApplicationContext());
                answers.setOrientation(LinearLayout.VERTICAL);

                EditText firstAnswer = new EditText(getActivity().getApplicationContext());
                firstAnswer.setHint("Odgovor");

                EditText secondAnswer = new EditText(getActivity().getApplicationContext());
                secondAnswer.setHint("Odgovor");

                answers.addView(firstAnswer);
                answers.addView(secondAnswer);

                Button addAnswer = new Button(getActivity().getApplicationContext());
                addAnswer.setText("Dodadi odgovor");
                addAnswer.setBackgroundColor(0xFF6200EE);
                addAnswer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText newAnswer = new EditText(getActivity().getApplicationContext());
                        newAnswer.setHint("Odgovor");
                        answers.addView(newAnswer);
                    }
                });

                newQuestion.addView(questionTitle);
                newQuestion.addView(answersText);
                newQuestion.addView(answers);
                newQuestion.addView(addAnswer);

                lytQuestions.addView(newQuestion);
            }
        });

        btnAddAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout questionAnswers = (LinearLayout) getView().findViewById(R.id.questionAnswers);
                EditText newAnswer = new EditText(getActivity().getApplicationContext());
                newAnswer.setHint("Odgovor");
                questionAnswers.addView(newAnswer);
            }
        });

        btnAddPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInput();
                String title = pollTitle.getText().toString();
                HashMap<String, HashMap<String,String>> questions = new HashMap<>();
                int numQuestions = lytQuestions.getChildCount();
                for(int i = 0; i < numQuestions; i++) {
                    LinearLayout question = (LinearLayout) lytQuestions.getChildAt(i);
                    EditText questionTitle = (EditText) question.getChildAt(0);
                    LinearLayout questionAnswers = (LinearLayout) question.getChildAt(2);
                    int numAnswers = questionAnswers.getChildCount();
                    HashMap<String, String> answersMap = new HashMap<>();
                    for(int j = 0; j < numAnswers; j++) {
                        EditText answer = (EditText) questionAnswers.getChildAt(j);
                        answersMap.put("Odgovor"+Integer.toString(j), answer.getText().toString());
                    }
                    questions.put(questionTitle.getText().toString(), answersMap);
                }
                Long startDate = (new Date().getTime())/1000;
                String strEndTime = endTime.getText().toString();
                String[] times = strEndTime.split(":");
                Long secondsTillEnd = Long.valueOf(times[0])*3600 + Long.valueOf(times[1])*60 + Long.valueOf(times[2]);

                database.child("polls").child(title).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Poll poll = new Poll(title, questions, startDate, secondsTillEnd);
                        database.child("polls").child(title).setValue(poll);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "Greska so databaza", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    private void checkInput() {
        String title = pollTitle.getText().toString();
        if(title.length() == 0) {
            Toast.makeText(getActivity().getApplicationContext(), "Naslovot ne smee da bide prazen", Toast.LENGTH_LONG).show();
            return;
        }

        String time = endTime.getText().toString();
        if(time.length() == 0) {
            Toast.makeText(getActivity().getApplicationContext(), "Vremetraenjeto ne smee da bide prazno", Toast.LENGTH_LONG).show();
            return;
        }

//        int numOptions = lytQuestions.getChildCount();
//        for(int i = 0; i < numOptions; i++) {
//            EditText option = (EditText) lytQuestions.getChildAt(i);
//            String optionText = option.getText().toString();
//            if(optionText.length() == 0) {
//                Toast.makeText(getActivity().getApplicationContext(), "Mora site polinja za opcii da bidat popolneti", Toast.LENGTH_LONG).show();
//                return;
//            }
//        }
    }
}