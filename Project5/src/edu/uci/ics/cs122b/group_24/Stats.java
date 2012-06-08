package edu.uci.ics.cs122b.group_24;

import java.util.HashSet;
import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;


public class Stats {

	public static int numberCorrect;
	public static int numberIncorrect;
	public static int numberOfQuizzes;
	public static int averageTime;
	
	public static int quizTime;
	
	public void setNumberCorrect(int c)
	{
		numberCorrect = c;
	}
	public void setNumberIncorrect(int c)
	{
		numberIncorrect = c;
	}
	public void setNumberOfQuizzes(int c)
	{
		numberOfQuizzes = c;
	}
	public void computeAverage(int c)
	{
		
	}
	
	public int getNumberCorrect()
	{
		return numberCorrect;
	}
	public int getNumberIncorrect()
	{
		return numberIncorrect;
	}
	public int getNumberOfQuizzes()
	{
		return numberOfQuizzes;
	}
	public int getAverageTime()
	{
		return averageTime;
	}
	
	
	
}
