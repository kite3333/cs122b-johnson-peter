package edu.uci.ics.cs122b.group_24;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuestionActivity extends Activity {

	private TextView questionView;
	private TextView timeView;
	private Button buttonSubmit;
	private RadioButton radio0;
	private RadioButton radio1;
	private RadioButton radio2;
	private RadioButton radio3;
	private String[] answers;
	private RadioGroup answerField;
	private int correctRadioID;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        
        Question question = new Question(this);
        timeView = (TextView) this.findViewById(R.id.timeView);
        questionView = (TextView) this.findViewById(R.id.questionView);
        radio0 = (RadioButton) this.findViewById(R.id.radio0);
        correctRadioID = R.id.radio0;
        radio1 = (RadioButton) this.findViewById(R.id.radio1);
        radio2 = (RadioButton) this.findViewById(R.id.radio2);
        radio3 = (RadioButton) this.findViewById(R.id.radio3);
        answerField = (RadioGroup) this.findViewById(R.id.answerField);
        
        question.generateQuestion();
        radio0.setText(question.getAnswer(0));
        radio1.setText(question.getAnswer(1));
        radio2.setText(question.getAnswer(2));
        radio3.setText(question.getAnswer(3));
        questionView.setText(question.getQuestion());
        buttonSubmit = (Button) this.findViewById(R.id.buttonSubmit);
        
this.buttonSubmit.setOnClickListener(new OnClickListener() {
    		
    		public void onClick(View v) {
    			// Change the button image
    			if (answerField.getCheckedRadioButtonId() == correctRadioID) {
    				questionView.setText("You're Correct!");
    			}
    			else {
    				questionView.setText("You're Incorrect!");
    			}
    		}
            });
        

        
        
        
        // Use db to get info for question and answers.
        
        // Display question and answers.
        
        // Start timer countdown.
        
        // What to do with submit? How to compare answer and record score?
        
        // How to transition to next question? How to determine if time's up?
    }
}
