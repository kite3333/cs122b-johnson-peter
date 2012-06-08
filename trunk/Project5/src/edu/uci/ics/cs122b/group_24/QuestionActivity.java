package edu.uci.ics.cs122b.group_24;

import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.os.CountDownTimer;

public class QuestionActivity extends Activity {

	private TextView questionView;
	private TextView timeView;
	private Button buttonSubmit;
	private RadioButton radio0;
	private RadioButton radio1;
	private RadioButton radio2;
	private RadioButton radio3;
	private RadioGroup answerField;
	private int correctRadioID;
	private CountDownTimer timer;
	private Question question;
	private int timeRemaining;
	private int time;
	
	private static final int THREE_MINUTES = 180000;
	private static final int ONE_SECOND = 1000;
	
	int count = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);

        timeRemaining = THREE_MINUTES;
        question = new Question(this);
        timeView = (TextView) this.findViewById(R.id.timeView);
        questionView = (TextView) this.findViewById(R.id.questionView);
        radio0 = (RadioButton) this.findViewById(R.id.radio0);
        radio1 = (RadioButton) this.findViewById(R.id.radio1);
        radio2 = (RadioButton) this.findViewById(R.id.radio2);
        radio3 = (RadioButton) this.findViewById(R.id.radio3);
        answerField = (RadioGroup) this.findViewById(R.id.answerField);
        timer = new CountDownTimer(THREE_MINUTES, ONE_SECOND) {
        	public void onTick(long millisUntilFinished) {
        		timeView.setText((millisUntilFinished / 1000) + "");
        		count++;
        		time = (int) millisUntilFinished / 1000;
        	}
        		public void onFinish() {
        			Intent intentStats = new Intent(QuestionActivity.this, StatsActivity.class);
        			startActivity(intentStats);
        		}
        	}.start();
        
        getNextQuestion();
        buttonSubmit = (Button) this.findViewById(R.id.buttonSubmit);
        this.buttonSubmit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Change the button image
				if (answerField.getCheckedRadioButtonId() == correctRadioID) {
					getNextQuestion();
					Stats.numberCorrect++;
					Stats.quizTime = count;
					Stats.averageTime = (Stats.averageTime + count) / 2;
					count = 0;
				}
				else if(answerField.getCheckedRadioButtonId() != correctRadioID)
				{
					getNextQuestion();
					Stats.numberIncorrect++;
					Stats.quizTime = count;
					Stats.averageTime = (Stats.averageTime + count) / 2;
					count = 0;
				}
			}
        });
		Stats.numberOfQuizzes++;
    }
    

    @Override
    protected void onResume() {
        super.onResume();
//        timer = new CountDownTimer(timeRemaining, ONE_SECOND) {
//          	public void onTick(long millisUntilFinished) {
//          		timeView.setText((millisUntilFinished / 1000) + "");
//          		count++;
//          	}
//          		public void onFinish() {
//          			Intent intentStats = new Intent(QuestionActivity.this, StatsActivity.class);
//          			startActivity(intentStats);
//          		}
//          	}.start();
        // The activity has become visible (it is now "resumed").
    }
    @Override
    protected void onPause() {
        super.onPause();
        timeRemaining = time;
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
      // Save UI state changes to the savedInstanceState.
      // This bundle will be passed to onCreate if the process is
      // killed and restarted.

      savedInstanceState.putInt("numOfQuizzes", Stats.numberOfQuizzes);
      savedInstanceState.putInt("numOfCorrect", Stats.numberCorrect);
      savedInstanceState.putInt("numOfIncorrect", Stats.numberIncorrect);
      savedInstanceState.putInt("avgTime", Stats.averageTime);
      savedInstanceState.putString("currentQuestion", question.getQuestion());
      savedInstanceState.putInt("currentTime", time);
      // etc.
      super.onSaveInstanceState(savedInstanceState);
    }
    
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
      super.onRestoreInstanceState(savedInstanceState);
      // Restore UI state from the savedInstanceState.
      // This bundle has also been passed to onCreate.
      Stats.numberOfQuizzes = savedInstanceState.getInt("numOfQuizzes");
      Stats.numberCorrect = savedInstanceState.getInt("numOfCorrect");
      Stats.numberIncorrect = savedInstanceState.getInt("numOfIncorrect");
      Stats.averageTime = savedInstanceState.getInt("avgTime");
      timer = new CountDownTimer(savedInstanceState.getInt("currentTime"), ONE_SECOND) {
      	public void onTick(long millisUntilFinished) {
      		timeView.setText((millisUntilFinished / 1000) + "");
      		count++;
      	}
      		public void onFinish() {
      			Intent intentStats = new Intent(QuestionActivity.this, StatsActivity.class);
      			startActivity(intentStats);
      		}
      	}.start();
      savedInstanceState.getString("currentQuestion");
    }
    private void getNextQuestion() {
    	question.generateQuestion();
        Vector<Integer> choices = new Vector<Integer>();
        int choice = -1;
        choices.add(0);
        choices.add(1);
        choices.add(2);
        choices.add(3);
        choice = choices.remove(Question.getRandInt((double) choices.size()));
        radio0.setText(question.getAnswer(choice));
        if (choice == 0) {
        	correctRadioID = radio0.getId();
        }
        choice = choices.remove(Question.getRandInt((double) choices.size()));
        radio1.setText(question.getAnswer(choice));
        if (choice == 0) {
        	correctRadioID = radio1.getId();
        }
        choice = choices.remove(Question.getRandInt((double) choices.size()));
        radio2.setText(question.getAnswer(choice));
        if (choice == 0) {
        	correctRadioID = radio2.getId();
        }
        choice = choices.remove(0);
        radio3.setText(question.getAnswer(choice));
        if (choice == 0) {
        	correctRadioID = radio3.getId();
        }
        questionView.setText(question.getQuestion());
    }
}
