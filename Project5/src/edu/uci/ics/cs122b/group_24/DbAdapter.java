package edu.uci.ics.cs122b.group_24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbAdapter extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "moviedb";
	private static final int DATABASE_VERSION = 1;
	public static final String MOVIES_NAME = "movies";
	public static final String STARS_NAME = "stars";
	public static final String STARS_IN_MOVIES_NAME = "stars_in_movies";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_MOVIE_ID = "movie_id";
	public static final String COLUMN_STAR_ID = "star_id";
	public static final String COLUMN_DIRECTOR = "director";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_YEAR = "year";
	public static final String COLUMN_FIRST_NAME = "first_name";
	public static final String COLUMN_LAST_NAME = "last_name";
	private SQLiteDatabase mDb;
	private Context mContext;
	private static final String TAG = "DBAdapter";
	
	// SQL Queries
	private static final String CREATE_TABLE_STARS = "create table stars(id integer primary key NOT NULL AUTO_INCREMENT, " +
			"first_name varchar(50) NOT NULL, last_name varchar(50) NOT NULL);";

	private static final String CREATE_TABLE_MOVIES = "create table movies (id integer primary key NOT NULL AUTO_INCREMENT, " +
			"title varchar(100) NOT NULL, year integer NOT NULL, director varchar(100) NOT NULL);";

	// Foreign key parts not included because not supported by SQLite
	private static final String CREATE_TABLES_STARS_IN_MOVIES = "create table stars_in_movies (star_id integer NOT NULL, " + 
			"movie_id integer NOT NULL);";
	
	public DbAdapter(Context ctx){
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		mContext = ctx;
		this.mDb = getWritableDatabase();
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		// Create tables
		db.execSQL(CREATE_TABLE_STARS);
		db.execSQL(CREATE_TABLE_MOVIES);
		db.execSQL(CREATE_TABLES_STARS_IN_MOVIES);
		
		// Populate tables
		try {
			String line;
			StringTokenizer tokenizer = null;
			//stars
			BufferedReader in = new BufferedReader(new InputStreamReader(mContext.getAssets().open("stars.csv")));
			while((line=in.readLine())!=null) {
				tokenizer = new StringTokenizer(line, ",");
				ContentValues values = new ContentValues();
				values.put(COLUMN_ID, tokenizer.nextToken());
				values.put(COLUMN_FIRST_NAME, tokenizer.nextToken());
				values.put(COLUMN_LAST_NAME, tokenizer.nextToken());
				db.insert(STARS_NAME, null, values);
			}
			//movies
			in = new BufferedReader(new InputStreamReader(mContext.getAssets().open("movies.csv")));
			while((line=in.readLine())!=null) {
				tokenizer = new StringTokenizer(line, ",");
				ContentValues values = new ContentValues();
				values.put(COLUMN_ID, tokenizer.nextToken());
				values.put(COLUMN_TITLE, tokenizer.nextToken());
				values.put(COLUMN_YEAR, tokenizer.nextToken());
				values.put(COLUMN_DIRECTOR, tokenizer.nextToken());
				db.insert(MOVIES_NAME, null, values);
			}
			//stars_in_movies
			in = new BufferedReader(new InputStreamReader(mContext.getAssets().open("stars_in_movies.csv")));
			while((line=in.readLine())!=null) {
				tokenizer = new StringTokenizer(line, ",");
				ContentValues values = new ContentValues();
				values.put(COLUMN_STAR_ID, tokenizer.nextToken());
				values.put(COLUMN_MOVIE_ID, tokenizer.nextToken());
				db.insert(STARS_IN_MOVIES_NAME, null, values);
			}
		} catch (IOException e) {
			Log.v(TAG, "ERROR");
			e.printStackTrace();
		}		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + STARS_IN_MOVIES_NAME + ", " + STARS_NAME + ", " + MOVIES_NAME + ";");
		onCreate(db);	
	}
	
//	public Cursor fetchAll() {
//		return mDb.query(TABLE_NAME, new String[] {FRUIT_NAME}, null, null, null, null, null);
//	}

}
