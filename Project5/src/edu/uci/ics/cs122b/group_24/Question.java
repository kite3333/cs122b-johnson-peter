package edu.uci.ics.cs122b.group_24;

import android.content.Context;
import android.database.Cursor;

public class Question {

	private DbAdapter db;
	private String question;
	private String[] choices;
	private String correctAnswer;
	private Cursor results;
	
    // Use random number to determine question type.
	
	public Question(Context context) {
		db = new DbAdapter(context);
	}
	
	private void generateQuestion() {
		int choice = getRandInt(10.0);
		choices = new String[4];
		Cursor cursor = null;
		int correctRowNum = -1;
		String answer = null;
		int totalCount = -1;
		
		switch (choice) {
			case 0: // Who directed the movie X?
				cursor = db.getQ1Data();
				totalCount = cursor.getCount();
				correctRowNum = getRandInt((double) totalCount);
				cursor.moveToPosition(correctRowNum);
				question = "Who directed the movie, \"" + cursor.getString(DbAdapter.COLUMN_NUM_TITLE) + ", " +
						cursor.getString(DbAdapter.COLUMN_NUM_YEAR) + "\"?";
				correctAnswer = cursor.getString(DbAdapter.COLUMN_NUM_DIRECTOR);
				choices[0] = correctAnswer;
				for (int i = 1; i <= 3; i++) {
					int rowNum = -1;
					do {
						rowNum = getRandInt(totalCount);
						cursor.moveToPosition(rowNum);
						answer = cursor.getString(DbAdapter.COLUMN_NUM_DIRECTOR);
					} while (rowNum == correctRowNum || answer.equals(correctAnswer));
					choices[i] = answer;
				}
				break;
			case 1: // When was the movie X released?
				cursor = db.getQ1Data();
				totalCount = cursor.getCount();
				correctRowNum = getRandInt((double) totalCount);
				cursor.moveToPosition(correctRowNum);
				question = "What year was the movie, \"" + cursor.getString(DbAdapter.COLUMN_NUM_TITLE) + ", released?";
				correctAnswer = cursor.getString(DbAdapter.COLUMN_NUM_YEAR);
				choices[0] = correctAnswer;
				int correctYear = Integer.parseInt(correctAnswer);
				int adjustment = 0;
				for (int i = 1; i < 3; i++) {
					adjustment = Math.random() > 0.5 ? getRandInt(10.0) + 1 : getRandInt(10.0) - 10;
					choices[i] = "" + correctYear + adjustment;
					for (int j = i - 1; j <= 0; j--) {
						if (choices[i].equals(choices[j])) {
							i--;
						}
					}
				}
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
	
	private int getRandInt(double maxExclusive) {
		return (int) Math.ceil(Math.random() * maxExclusive) - 1;
	}
	
}
