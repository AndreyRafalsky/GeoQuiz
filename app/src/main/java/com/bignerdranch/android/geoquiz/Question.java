package com.bignerdranch.android.geoquiz;

public class Question {
    private int questionResId;
    private boolean answer;

    public Question(int questionResId, boolean answer) {
        this.questionResId = questionResId;
        this.answer = answer;
    }

    public int getQuestionResId() {
        return questionResId;
    }

    public void setQuestionResId(int questionResId) {
        this.questionResId = questionResId;
    }

    public boolean getAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
}
