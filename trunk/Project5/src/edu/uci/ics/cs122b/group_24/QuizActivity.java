package edu.uci.ics.cs122b.group_24;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class QuizActivity extends Activity {
	
	private Button buttonStart;
	private Button buttonStats;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Retrieve the button and add an event listener
        this.buttonStart = (Button) this.findViewById(R.id.buttonStart);
        this.buttonStart.setOnClickListener(new OnClickListener() {
    		
    		public void onClick(View v) {
    			// Change the button image
    			Intent intent = new Intent(QuizActivity.this, QuestionActivity.class);
    			startActivity(intent);
    		}
            });
        
        // Retrieve the button and add an event listener
        this.buttonStats = (Button)this.findViewById(R.id.buttonStats);
        this.buttonStats.setOnClickListener(new OnClickListener() {
    		
    		public void onClick(View v) {
    			// Change the button image
    			Intent intent = new Intent(QuizActivity.this, StatsActivity.class);
    			startActivity(intent);
    		}
            });
    } 
}