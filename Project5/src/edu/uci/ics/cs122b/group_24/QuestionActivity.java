package edu.uci.ics.cs122b.group_24;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class QuestionActivity extends Activity {

	private TextView questionView;
	private TextView timeView;
	private Button buttonSubmit;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        
        Question question = new Question(this);
        timeView = (TextView) this.findViewById(R.id.timeView);
        questionView = (TextView) this.findViewById(R.id.questionView);
        buttonSubmit = (Button) this.findViewById(R.id.buttonSubmit);
        

        
        
        
        // Use db to get info for question and answers.
        
        // Display question and answers.
        
        // Start timer countdown.
        
        // What to do with submit? How to compare answer and record score?
        
        // How to transition to next question? How to determine if time's up?
    }
}
