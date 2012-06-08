package edu.uci.ics.cs122b.group_24;

import java.util.HashSet;
import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

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
		return correctAnswer.replaceAll("\"", "");
	}
	
	public void generateQuestion() {
		int choice = getRandInt(5.0);
		choices = new String[4];
		Cursor cursor = null;
		int correctRowNum = -1;
		String answer = null;
		int totalCount = -1;
		int movieID = 0;
		HashSet<Integer> usedIDs;
		int[] answerIDs;
		
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
				
			/////// CASE 1 /////////
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
			
			/////// CASE 2 /////////
			case 2: // Which star was in the movie X?
				cursor = db.getTable(DbAdapter.MOVIES_NAME);
				totalCount = cursor.getCount();
				// Randomly choose a movie of interest
				cursor.moveToPosition(getRandInt((double) totalCount));
				movieID = cursor.getInt(DbAdapter.COLUMN_NUM_ID);
				question = "Which actor starred in the movie, \"" + cursor.getString(DbAdapter.COLUMN_NUM_TITLE) + "\"?";
				cursor = db.query(DbAdapter.STARS_IN_MOVIES_NAME, DbAdapter.COLUMN_MOVIE_ID + "= '" + movieID + "'");
				totalCount = cursor.getCount();
				// Choose the correct answer's ID and add to starIDs
				cursor.moveToPosition(getRandInt((double) totalCount));
				answerIDs = new int[4];
				answerIDs[0] = cursor.getInt(DbAdapter.COLUMN_NUM_STAR_ID);
				// Add all the stars of this movie to a list for checking later
				cursor.moveToFirst();
				usedIDs = new HashSet<Integer>(); 
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
					answerIDs[i] = cursor.getInt(DbAdapter.COLUMN_NUM_STAR_ID);
					if (!usedIDs.add(answerIDs[i])) { // By adding the id, duplicates as well as correct ids will be checked for.
						i--;
					}
				}
				// Get the names of the stars that were chosen
				for (int i = 0; i <= 3; i++) {
					cursor = db.query(DbAdapter.STARS_NAME, DbAdapter.COLUMN_ID + "= '" + answerIDs[i] + "'");
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
				
			////////CASE 3/////////
			case 3: // Which star was not in the movie X?
				cursor = db.getTable(DbAdapter.MOVIES_NAME);
				totalCount = cursor.getCount();
				// Randomly choose a movie of interest
				cursor.moveToPosition(getRandInt((double) totalCount));
				movieID = cursor.getInt(DbAdapter.COLUMN_NUM_ID);
				question = "Which actor did NOT star in the movie, " + cursor.getString(DbAdapter.COLUMN_NUM_TITLE) + "?";
				cursor = db.query(DbAdapter.STARS_IN_MOVIES_NAME, DbAdapter.COLUMN_MOVIE_ID + " != '" + movieID + "'");
				totalCount = cursor.getCount();
				// Choose the correct answer's ID and add to starIDs
				cursor.moveToPosition(getRandInt((double) totalCount));
				answerIDs = new int[4];
				usedIDs = new HashSet<Integer>();
				answerIDs[0] = cursor.getInt(DbAdapter.COLUMN_NUM_STAR_ID);
				// Add the wrong answers (those that did star in the movie)
				cursor = db.query(DbAdapter.STARS_IN_MOVIES_NAME, DbAdapter.COLUMN_MOVIE_ID + " = '" + movieID + "'");
				totalCount = cursor.getCount();
				// If there are more than 3 stars in the movie of interest, choose 3 at random
				if (totalCount > 3) {
					for (int i = 1; i <= 3; i++) {
						cursor.moveToPosition(getRandInt((double) totalCount));
						answerIDs[i] = cursor.getInt(DbAdapter.COLUMN_NUM_STAR_ID);
						if (!usedIDs.add(answerIDs[i])) { // By adding the id, duplicates as well as correct ids will be checked for
							i--;
						}
					}
				}
				else { // Otherwise add all of the stars from the movie and add id = 0 to make up for movies with less than 3 stars
					cursor.moveToFirst();
					for (int i = 1; i <= totalCount; i++) {
						answerIDs[i] = cursor.getInt(DbAdapter.COLUMN_NUM_STAR_ID);
						cursor.moveToNext();
					}
					for (int i = totalCount + 1; i <= 3; i++) {
						answerIDs[i] = 0;
					}
				}
				// Get the names of the stars
				for (int i = 0; i <= 3; i++) {
					if (answerIDs[i] != 0) {
						cursor = db.query(DbAdapter.STARS_NAME, DbAdapter.COLUMN_ID + " = '" + answerIDs[i] + "'");
						cursor.moveToFirst();
						if (!cursor.getString(DbAdapter.COLUMN_NUM_FIRST_NAME).equals("none")) {
							choices[i] = cursor.getString(DbAdapter.COLUMN_NUM_FIRST_NAME) + " " + cursor.getString(DbAdapter.COLUMN_NUM_LAST_NAME);
						}
						else {
							choices[i] = cursor.getString(DbAdapter.COLUMN_NUM_LAST_NAME);
						}
					}
					else {
						choices[i] = "N/A";
					}
				}
				correctAnswer = choices[0];
				break;
				
			/////////// CASE 4 /////////////
			case 4: // In which movie did X and Y appear together?
				Cursor cursor2 = null;
				cursor = db.getTable(DbAdapter.MOVIES_NAME);
				totalCount = cursor.getCount();
				answerIDs = new int[4];
				choices = new String[4];
				boolean gotMovie = false;
				// Randomly choose a movie of interest
				while (!gotMovie) {
					cursor.moveToPosition(getRandInt((double) totalCount));
					answerIDs[0] = cursor.getInt(DbAdapter.COLUMN_NUM_ID);
					cursor2 = db.query(DbAdapter.STARS_IN_MOVIES_NAME, DbAdapter.COLUMN_MOVIE_ID + " = '" + answerIDs[0] + "'");
					if (cursor2.getCount() > 1) { // Chosen movie has at least 2 stars or get another one
						gotMovie = true;
					}
				}
				totalCount = cursor2.getCount();
				usedIDs = new HashSet<Integer>();
				usedIDs.add(answerIDs[0]);
				// Pick the two stars
				cursor2.moveToPosition(getRandInt((double) totalCount));
				int[] starIDs = new int[2];
				String[] starNames = new String[2];
				starIDs[0] = cursor2.getInt(DbAdapter.COLUMN_NUM_STAR_ID);
				starIDs[1] = 0;
				do {
					cursor2.moveToPosition(getRandInt((double) totalCount));
					starIDs[1] = cursor2.getInt(DbAdapter.COLUMN_NUM_STAR_ID);
				} while (starIDs[0] == starIDs[1]);
				// Get the names of the two stars
				for (int i = 0; i <= 1; i++) {
					cursor = db.query(DbAdapter.STARS_NAME, DbAdapter.COLUMN_ID + " = '" + starIDs[i] + "'");
					cursor.moveToFirst();
					starNames[i] = cursor.getString(DbAdapter.COLUMN_NUM_FIRST_NAME).replaceAll("\"", "");
					if (starNames[i] != null && !starNames[i].equals("none")) {
						starNames[i] += " " + cursor.getString(DbAdapter.COLUMN_NUM_LAST_NAME).replaceAll("\"", "");
					}
					else {
						starNames[i] = cursor.getString(DbAdapter.COLUMN_NUM_LAST_NAME).replaceAll("\"", "");
					}
				}
				question = "In which movie did " + starNames[0] + " and " + starNames[1] + " appear in together?";
				// Pick 3 wrong answers
				// Get 3 movies where neither star has performed
				cursor = db.query(DbAdapter.STARS_IN_MOVIES_NAME, DbAdapter.COLUMN_STAR_ID + " != '" + starIDs[0] + "' || " + 
						DbAdapter.COLUMN_STAR_ID + " != '" + starIDs[1] + "'");
				totalCount = cursor.getCount();
				for (int i = 1; i <= 3; i++) {
					cursor.moveToPosition(getRandInt((double) totalCount));
					answerIDs[i] = cursor.getInt(DbAdapter.COLUMN_NUM_MOVIE_ID);
					if (!usedIDs.add(answerIDs[i])) {
						i--;
					}
				}
				// Get names for movies
				for (int i = 0; i <= 3; i++) {
					cursor = db.query(DbAdapter.MOVIES_NAME, DbAdapter.COLUMN_ID + "= '" + answerIDs[i] + "'");
					cursor.moveToFirst();
					choices[i] = cursor.getString(DbAdapter.COLUMN_NUM_TITLE);
				}
				correctAnswer = choices[0];
				break;
				
///////////// CASE 5 ///////////////
			case 5: // Who directed the movie X?
//				cursor = db.getTable(DbAdapter.MOVIES_NAME);
//				totalCount = cursor.getCount();
//				// Randomly choose a movie of interest
//				cursor.moveToPosition(getRandInt((double) totalCount));
//				int movieID100 = cursor.getInt(DbAdapter.COLUMN_NUM_ID);
//				question = "Who directed the movie, \"" + cursor.getString(DbAdapter.COLUMN_NUM_TITLE) + "\"?";
//				cursor = db.query(DbAdapter.MOVIES_NAME, DbAdapter.COLUMN_ID + "= '" + movieID100 + "'");
//				totalCount = cursor.getCount();
//				// Choose the correct answer's ID and add to DIRECTOR
//				cursor.moveToPosition(getRandInt((double) totalCount));
//				int[] starIDs100 = new int[4];
//				starIDs100[0] = cursor.getInt(DbAdapter.COLUMN_NUM_DIRECTOR);
//				// Add all the stars of this movie to a list for checking later
//				cursor.moveToFirst();
//				HashSet<Integer> usedIDs100 = new HashSet<Integer>(); 
//				while (!cursor.isAfterLast()) {
//					usedIDs100.add(cursor.getInt(DbAdapter.COLUMN_NUM_DIRECTOR));
//					cursor.moveToNext();
//				}
//				// Get a list of all entries minus the ones with the movie_id of the movie in question.
//				cursor = db.query(DbAdapter.MOVIES_NAME, DbAdapter.COLUMN_ID + "!= '" + movieID100 + "'");
//				totalCount = cursor.getCount();
//				// Choose 3 incorrect answers from the list
//				for (int i = 1; i <= 3; i++) {
//					cursor.moveToPosition(getRandInt((double) totalCount));
//					starIDs100[i] = cursor.getInt(DbAdapter.COLUMN_NUM_DIRECTOR);
//					if (!usedIDs100.add(starIDs100[i])) { // By adding the id, duplicates as well as correct ids will be checked for.
//						i--;
//					}
//				}
//
//				// Get the names of the stars that were NOT chosen
//				for (int i = 0; i <= 3; i++) {
//					cursor = db.query(DbAdapter.MOVIES_NAME, DbAdapter.COLUMN_ID + "!= '" + starIDs100[i] + "'");
//					cursor.moveToFirst();
//					if (cursor.getString(DbAdapter.COLUMN_NUM_DIRECTOR) != null) {
//						choices[i] = cursor.getString(DbAdapter.COLUMN_NUM_DIRECTOR);
//					}
//					else {
//						choices[i] = cursor.getString(DbAdapter.COLUMN_NUM_DIRECTOR);
//					}
//				}
//				correctAnswer = choices[0];
				
				
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
	
	public static int getRandInt(double maxExclusive) {
		return (int) Math.ceil(Math.random() * maxExclusive) - 1;
	}
	
}
