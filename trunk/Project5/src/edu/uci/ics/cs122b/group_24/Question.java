package edu.uci.ics.cs122b.group_24;

import android.content.Context;
import android.database.Cursor;

public class Question {

	private DbAdapter db;
	private String question;
	private String answer1;
	private String answer2;
	private String answer3;
	private String correctAnswer;
	private Cursor results;
	
    // Use random number to determine question type.
	
	public Question(Context context) {
		db = new DbAdapter(context);
	}
	
	private void generateQuestion() {
		int choice = (int) Math.ceil(Math.random() * 10.0) - 1;
		Cursor cursor = null;
		switch (choice) {
			case 0: // Who directed the movie X?
				
				break;
			case 1: // When was the movie X released?
				break;
			case 2: // Which star was in the movie X?
				break;
			case 3: // Which star was not in the movie X?
				break;
			case 4: // In which movie did X and Y appear together?
				break;
			case 5: // Who directed the movie X?
				break;
			case 6: // Who didn't direct the movie X?
				break;
			case 7: // Which star appears in both movies X and Y?
				break;
			case 8: // Which star did not appear in the same movie with X?
				break;
			case 9: // Who directed the star X in year Y?
				break;
			default:
		}
	}
	
}
