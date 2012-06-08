package edu.uci.ics.cs122b.group_24;

import java.util.HashSet;
import java.util.Vector;

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
		question = "No Question";
		correctAnswer = "N/A";
		choices = new String[4];
		choices[0] = "Ans 1";
		choices[1] = "Ans 2";
		choices[2] = "Ans 3";
		choices[3] = "Ans 4";
	}
	
	public String getQuestion() {
		return question;
	}
	
	public String getAnswer(int choice) {
		return choices[choice].replaceAll("\"", "");
	}
	
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	
	public void test() {
		int choice = 0; //getRandInt(3.0);
		choices = new String[4];
		Cursor cursor = null;
		int correctRowNum = -1;
		String answer = null;
		int totalCount = -1;
		cursor = db.getTable(DbAdapter.MOVIES_NAME);
		totalCount = cursor.getCount();
		question = "" + totalCount;
		// Randomly choose the movie of interest
	}
	
	public void generateQuestion() {
		int choice = 2; //getRandInt(3.0);
		choices = new String[4];
		Cursor cursor = null;
		int correctRowNum = -1;
		String answer = null;
		int totalCount = -1;
		
		// The expected end result of any of these cases is that question will have the question to be asked,
		// choices will have the 4 choices that the user can choose from, and correctAnswer will contain the
		// correct response.
		switch (choice) {
			case 0: // Who directed the movie X?
				cursor = db.getTable(DbAdapter.MOVIES_NAME);
				totalCount = cursor.getCount();
				// Randomly choose the movie of interest
				correctRowNum = getRandInt((double) totalCount);
				cursor.moveToPosition(correctRowNum);
				question = "Who directed the movie, " + cursor.getString(DbAdapter.COLUMN_NUM_TITLE) + " (" +
						cursor.getString(DbAdapter.COLUMN_NUM_YEAR) + ")?";
				// Assign correct answer
				correctAnswer = cursor.getString(DbAdapter.COLUMN_NUM_DIRECTOR);
				choices[0] = correctAnswer;
				HashSet<String> usedNames = new HashSet<String>();
				usedNames.add(correctAnswer);
				// Randomly choose and add the 3 wrong answers
				for (int i = 1; i <= 3; i++) {
					int rowNum = -1;
					do {
						rowNum = getRandInt(totalCount);
						cursor.moveToPosition(rowNum);
						answer = cursor.getString(DbAdapter.COLUMN_NUM_DIRECTOR);
					} while (rowNum == correctRowNum || !usedNames.add(answer));
					choices[i] = answer;
				}
				break;
			case 1: // When was the movie X released?
				cursor = db.getTable(DbAdapter.MOVIES_NAME);
				totalCount = cursor.getCount();
				// Randomly choose a movie of interest
				correctRowNum = getRandInt((double) totalCount);
				cursor.moveToPosition(correctRowNum);
				question = "What year was the movie, \"" + cursor.getString(DbAdapter.COLUMN_NUM_TITLE) + ", released?";
				// Assign the correct answer
				correctAnswer = cursor.getString(DbAdapter.COLUMN_NUM_YEAR);
				choices[0] = correctAnswer;
				// Get 3 wrong answers by adding an integer between -10 and -1 or 1 and 10 to the correct year
				int correctYear = Integer.parseInt(correctAnswer);
				int adjustment = 0;
				for (int i = 1; i <= 3; i++) {
					//Math.random() chooses a double between 0.0 and 1.0
					adjustment = Math.random() > 0.5 ? getRandInt(10.0) + 1 : getRandInt(10.0) - 10;
					choices[i] = "" + (correctYear + adjustment);
					for (int j = i - 1; j >= 0; j--) {
						if (choices[i].equals(choices[j])) {
							i--;
							break;
						}
					}
				}
				break;
			case 2: // Which star was in the movie X?
				cursor = db.getTable(DbAdapter.MOVIES_NAME);
				totalCount = cursor.getCount();
				// Randomly choose a movie of interest
				cursor.moveToPosition(getRandInt((double) totalCount));
				int movieID = cursor.getInt(DbAdapter.COLUMN_NUM_ID);
				question = "Which actor starred in the movie, \"" + cursor.getString(DbAdapter.COLUMN_NUM_TITLE) + "\"?";
				cursor = db.query(DbAdapter.STARS_IN_MOVIES_NAME, DbAdapter.COLUMN_MOVIE_ID + "= '" + movieID + "'");
				totalCount = cursor.getCount();
				// Choose the correct answer's ID and add to starIDs
				cursor.moveToPosition(getRandInt((double) totalCount));
				int[] starIDs = new int[4];
				starIDs[0] = cursor.getInt(DbAdapter.COLUMN_NUM_STAR_ID);
				// Add all the stars of this movie to a list for checking later
				cursor.moveToFirst();
				HashSet<Integer> usedIDs = new HashSet<Integer>(); 
				while (!cursor.isAfterLast()) {
					usedIDs.add(cursor.getInt(DbAdapter.COLUMN_NUM_STAR_ID));
					cursor.moveToNext();
				}
				// Get a list of all entries minus the ones with the movie_id of the movie in question.
				cursor = db.query(DbAdapter.STARS_IN_MOVIES_NAME, DbAdapter.COLUMN_NUM_MOVIE_ID + "!= '" + movieID + "'");
				totalCount = cursor.getCount();
				// Choose 3 incorrect answers from the list
				for (int i = 1; i <= 3; i++) {
					cursor.moveToPosition(getRandInt((double) totalCount));
					starIDs[i] = cursor.getInt(DbAdapter.COLUMN_NUM_STAR_ID);
					if (!usedIDs.add(starIDs[i])) { // By adding the id, duplicates as well as correct ids will be checked for.
						i--;
					}
				}
				int count = 0;
				// Get the names of the stars that were chosen
				for (int i = 0; i <= 3; i++) {
					cursor = db.query(DbAdapter.STARS_NAME, DbAdapter.COLUMN_ID + "= '" + starIDs[i] + "'");
					cursor.moveToFirst();
					if (cursor.getString(DbAdapter.COLUMN_NUM_FIRST_NAME) != null) {
						choices[i] = cursor.getString(DbAdapter.COLUMN_NUM_FIRST_NAME) + " " + cursor.getString(DbAdapter.COLUMN_NUM_LAST_NAME);
					}
					else {
						choices[i] = cursor.getString(DbAdapter.COLUMN_NUM_LAST_NAME);
					}
				}
				correctAnswer = choices[0];
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
				question = "No Question.";
		}
	}
	
	private int getRandInt(double maxExclusive) {
		return (int) Math.ceil(Math.random() * maxExclusive) - 1;
	}
	
}
