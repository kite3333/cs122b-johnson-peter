package edu.uci.ics.cs122b.group_24;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class StatsActivity extends Activity {

	Stats stats;
	private TextView numOfQuizTextView;
	private TextView numOfCorrectTextView;
	private TextView numOfIncorrectTextView;
	private TextView avgTimeTextView;
	
//	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);

//        stats = new Stats();
        
        numOfQuizTextView = (TextView) this.findViewById(R.id.numOfQuizTextView);
        numOfCorrectTextView = (TextView) this.findViewById(R.id.numOfCorrectTextView);
        numOfIncorrectTextView = (TextView) this.findViewById(R.id.numOfIncorrectTextView);
        avgTimeTextView = (TextView) this.findViewById(R.id.avgTimeTextView);
//        
        numOfQuizTextView.setText("" + Stats.numberOfQuizzes);
        numOfCorrectTextView.setText("" + Stats.numberCorrect);
        numOfIncorrectTextView.setText("" + Stats.numberIncorrect);
        avgTimeTextView.setText("" + Stats.averageTime);
    }
	//the statistics about the user's overall score so far
	
	//the number of quizzes taken
	
	//the number of correct answers
	
	//number of incorrect answers
	
	//average time spent to answer each question
	
}
