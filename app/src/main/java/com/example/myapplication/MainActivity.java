package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.entity.Question;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";
    private static final String NUMBER_OF_QUESTION = "number_of_question";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button prevButton;
    private TextView questionText;
    private Button cheatButton;

    private Question[] questions = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            index = savedInstanceState.getInt(NUMBER_OF_QUESTION, 0);
        }
        Log.d(LOG_TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.prev_button);
        questionText = findViewById(R.id.questionText);
        questionText.setText(questions[index].getTextResId());
        cheatButton = findViewById(R.id.cheat_button);

        trueButton.setOnClickListener(view -> {
            checkAnswer(true);
            hideButtons();
        });

        falseButton.setOnClickListener(view -> {
            checkAnswer(false);
            hideButtons();
        });

        prevButton.setOnClickListener(view -> {
            updateQuestion(false);
        });

        nextButton.setOnClickListener(view -> {
            trueButton.setVisibility(View.VISIBLE);
            falseButton.setVisibility(View.VISIBLE);
            updateQuestion(true);
        });

        cheatButton.setOnClickListener(view -> {
            boolean answerIsTrue = questions[index].isAnswerTrue();
            Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
            startActivityForResult(intent, REQUEST_CODE_CHEAT);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume() called");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d(LOG_TAG, "onPostResume() called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop() called");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.d(LOG_TAG, "onPostCreate(Bundle) called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(LOG_TAG, "onSaveInstanceState");
        outState.putInt(NUMBER_OF_QUESTION, index);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy() called");
    }

    private void checkAnswer(boolean userPressedTrue) {
        int messageResId;
        if (questions[index].isCheated()) {
            messageResId = R.string.judgment_toast;
        } else {

            if ("".contentEquals(questionText.getText())) {
                updateQuestion(true);
                return;
            }
            boolean answerIsTrue = questions[index].isAnswerTrue();

            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();
    }

    private void updateQuestion(boolean isNext) {
        if (isNext) {
            index = (index + 1) % questions.length;
        } else {
            if (index == 0) {
                return;
            }
            index = (index - 1) % questions.length;
        }
        int question = questions[index].getTextResId();
        questionText.setText(question);
    }

    private void hideButtons() {
        trueButton.setVisibility(View.GONE);
        falseButton.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            questions[index].setCheated(CheatActivity.wasAnswerShown(data));
        }
    }
}
