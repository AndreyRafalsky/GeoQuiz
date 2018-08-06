package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button prevButton;
    private TextView questionTextView;

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_CLICKABLE = "clickable";
    private static final String KEY_CORRECT_ANSWERS = "correct";

    private Question[] questionBank = {
      new Question(R.string.question_australia, true),
      new Question(R.string.question_oceans, true),
      new Question(R.string.question_mideast, false),
      new Question(R.string.question_africa, false),
      new Question(R.string.question_americas, true),
      new Question(R.string.question_asia, true)
    };

    private int currentIndex = 0;
    private int correctAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionTextView = findViewById(R.id.question_text_view);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.prev_button);

        Log.d(TAG, "onCreate(Bundle) called");

        updateQuestion();

        if (savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            setClickableAnswerButtons(savedInstanceState.getBoolean(KEY_CLICKABLE, true));
            correctAnswers = savedInstanceState.getInt(KEY_CORRECT_ANSWERS, 0);

        }

        View.OnClickListener nextQuestionListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex++;
                if (currentIndex == questionBank.length){
                    currentIndex = 0;
                }
                updateQuestion();
            }
        };

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex--;
                if (currentIndex < 0){
                    currentIndex = questionBank.length - 1;
                }
                updateQuestion();
            }
        });

        nextButton.setOnClickListener(nextQuestionListener);
        questionTextView.setOnClickListener(nextQuestionListener);
    }

    private void updateQuestion() {
        setClickableAnswerButtons(true);
        questionTextView.setText(questionBank[currentIndex].getQuestionResId());
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState()");
        savedInstanceState.putInt(KEY_INDEX, currentIndex);
        savedInstanceState.putBoolean(KEY_CLICKABLE, trueButton.isClickable());
        savedInstanceState.putInt(KEY_CORRECT_ANSWERS, correctAnswers);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    private void showToastMessage(int stringResource, int gravity, Object...args){
        Toast toast = Toast.makeText(QuizActivity.this, String.format(getString(stringResource), args),
                Toast.LENGTH_SHORT);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

    private void checkAnswer(boolean userAnswer){
        setClickableAnswerButtons(false);
        boolean correctAnswer = questionBank[currentIndex].getAnswer();
        if (userAnswer == correctAnswer){
            correctAnswers++;
            showToastMessage(R.string.correct_toast, Gravity.TOP);
        }else {
            showToastMessage(R.string.incorrect_toast, Gravity.TOP);
        }

        if (currentIndex == questionBank.length - 1){
            showToastMessage(R.string.results, Gravity.BOTTOM, correctAnswers, questionBank.length);
            correctAnswers = 0;
        }
    }

    private void setClickableAnswerButtons(boolean isClickable) {
        LinearLayout answerLayout = findViewById(R.id.answer_layout);
        for (int i = 0; i < answerLayout.getChildCount(); i++){
            answerLayout.getChildAt(i).setClickable(isClickable);
        }
        nextButton.setClickable(!isClickable);
    }
}
