package com.example.myapplication.entity;

public class Question {

    private int textResId;

    private boolean isAnswerTrue;

    public Question(int textResId, boolean answerTrue) {
        this.textResId = textResId;
        this.isAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return textResId;
    }

    public void setTextResId(int textResId) {
        this.textResId = textResId;
    }

    public boolean isAnswerTrue() {
        return isAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        isAnswerTrue = answerTrue;
    }
}
