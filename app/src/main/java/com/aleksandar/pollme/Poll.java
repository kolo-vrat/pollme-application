package com.aleksandar.pollme;

import java.util.HashMap;

public class Poll {

    private String title;
    private HashMap<String, HashMap<String, String>> questions;
    private Long startDate;
    private Long secondsTillEnd;

    public Poll() {}

    public Poll(String title, HashMap<String, HashMap<String, String>> questions, Long startDate, Long secondsTillEnd) {
        this.title = title;
        this.questions = questions;
        this.startDate = startDate;
        this.secondsTillEnd = secondsTillEnd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getSecondsTillEnd() {
        return secondsTillEnd;
    }

    public void setSecondsTillEnd(Long secondsTillEnd) {
        this.secondsTillEnd = secondsTillEnd;
    }

    public HashMap<String, HashMap<String, String>> getQuestions() {
        return questions;
    }

    public void setQuestions(HashMap<String, HashMap<String, String>> questions) {
        this.questions = questions;
    }
}
