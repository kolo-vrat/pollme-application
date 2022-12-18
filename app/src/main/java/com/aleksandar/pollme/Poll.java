package com.aleksandar.pollme;

import java.util.Date;
import java.util.HashMap;

public class Poll {

    private String title;
    private HashMap<String, HashMap<String, String>> questions;
    private Long startDate;
    private Long secondsTillEnd;
    private HashMap<String, HashMap<String, HashMap<String, String>>> answers;

    public Poll(String title, HashMap<String, HashMap<String, String>> questions, Long startDate, Long secondsTillEnd, HashMap<String, HashMap<String, HashMap<String, String>>> answers) {
        this.title = title;
        this.questions = questions;
        this.startDate = startDate;
        this.secondsTillEnd = secondsTillEnd;
        this.answers = answers;
    }

    public HashMap<String, HashMap<String, HashMap<String, String>>> getAnswers() {
        return answers;
    }

    public void setAnswers(HashMap<String, HashMap<String, HashMap<String, String>>> answers) {
        this.answers = answers;
    }

    public Poll() {}

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

    public String timeLeft() {
        Long timeNow = new Date().getTime();
        Long diff = startDate + secondsTillEnd - timeNow / 1000;
        if (diff < 0) return "00:00:00";
        String hours = Long.valueOf(Math.round(diff / 3600L)).toString();
        diff = diff % 3600;
        String minutes = Long.valueOf(Math.round(diff / 60L)).toString();
        diff = diff % 60;
        String seconds = Long.valueOf(diff).toString();
        return hours + ":" + minutes + ":" + seconds;
    }
}
