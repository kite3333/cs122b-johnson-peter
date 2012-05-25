
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;


public class SAXParser_proj4 extends DefaultHandler {

	private String tempVal;

	// to maintain context
	private Book tempBook;

	boolean titleBool = false;
	String title = "";

	boolean booktitleBool = false;
	String booktitle = "";
	ArrayList booktitleList = new ArrayList();

	boolean publisherBool = false;
	String publisher = "";
	ArrayList publisherList = new ArrayList();

	boolean pagesBool = false;
	String pages = "";
	int startPage = 0;
	int endPage = 0;

	boolean yearBool = false;
	int year = 0;

	boolean volumeBool = false;
	int volume = 0;

	boolean numberBool = false;
	int number = 0;

	boolean urlBool = false;
	String url = "";

	boolean eeBool = false;
	String ee = "";

	boolean cdromBool = false;
	String cdrom = "";

	boolean citeBool = false;
	String cite = "";

	boolean crossrefBool = false;
	String crossref = "";

	boolean isbnBool = false;
	String isbn = "";

	boolean seriesBool = false;
	String series = "";

	boolean authorBool = false;
	String author = "";
	ArrayList authorList = new ArrayList();

	boolean editorBool = false;
	String editor = "";
	ArrayList editorList = new ArrayList();
	
	int editorID = 0;
	int booktitleID = 0;
	int publisherID = 0;
	int dblpID = 1;
	int authorID = 0;
	
	ArrayList multipleAuthorsList = new ArrayList();

	boolean goAheadAddAuthor = false;
	
	
	Hashtable editorTable = new Hashtable();
	Hashtable booktitleTable = new Hashtable();
	Hashtable publisherTable = new Hashtable();
	Hashtable authorTable = new Hashtable();
	
	Random generator = new Random();

	PreparedStatement psInsertRecord=null;
	PreparedStatement author_mapping_PsInsertRecord=null;
	
	ArrayList dblp_queries = new ArrayList();
	ArrayList mapping_queries = new ArrayList();
	
	int[] iNoRows=null;
	Connection connection = null;
	
	public SAXParser_proj4() {
	}

	public void runExample() {
		parseDocument();

		try {
			HashSet hs = new HashSet();
			hs.addAll(authorList);
			authorList.clear();
			authorList.addAll(hs);

			HashSet hs2 = new HashSet();
			hs2.addAll(editorList);
			editorList.clear();
			editorList.addAll(hs2);

			HashSet hs3 = new HashSet();
			hs3.addAll(booktitleList);
			booktitleList.clear();
			booktitleList.addAll(hs3);

			HashSet hs4 = new HashSet();
			hs4.addAll(publisherList);
			publisherList.clear();
			publisherList.addAll(hs4);

			// Incorporate mySQL driver
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			// Connect to MySQL as root
			 connection = DriverManager.getConnection(
					"jdbc:mysql://", "root", "");
			 connection.setAutoCommit(false);
			// Create and execute an SQL statement to get all the database names
			Statement myDBStm = connection.createStatement();
			ResultSet resultDB = myDBStm.executeQuery("use moviedb");
			String insertRecord = "";
			
			myDBStm.executeUpdate("insert into tbl_genres (id, genre_name) values(1, 'article');");
			myDBStm.executeUpdate("insert into tbl_genres (id, genre_name) values(2, 'inproceedings');");
			myDBStm.executeUpdate("insert into tbl_genres (id, genre_name) values(3, 'proceedings');");
			myDBStm.executeUpdate("insert into tbl_genres (id, genre_name) values(4, 'book');");
			myDBStm.executeUpdate("insert into tbl_genres (id, genre_name) values(5, 'incollection');");
			myDBStm.executeUpdate("insert into tbl_genres (id, genre_name) values(6, 'phdthesis');");
			myDBStm.executeUpdate("insert into tbl_genres (id, genre_name) values(7, 'mastersthesis');");
			myDBStm.executeUpdate("insert into tbl_genres (id, genre_name) values(8, 'www');");
			
			
//			for(int i = 0; i < dblp_queries.size(); i++)
//			{
//				myDBStm.executeUpdate(dblp_queries.get(i).toString());
//			}
//			
//			for(int i = 0; i < mapping_queries.size(); i++)
//			{
//				myDBStm.executeUpdate(mapping_queries.get(i).toString());
//			}
			
			
//			psInsertRecord.executeBatch();
//			author_mapping_PsInsertRecord.executeBatch();

			
//			for (int i = 0; i < authorList.size(); i++) {
//				insertRecord = "insert into tbl_people (id, name) values(" + authorTable.get(authorList.get(i)) + "," + 
//						+ '"' + authorList.get(i) + '"' + ");";
//				myDBStm.executeUpdate(insertRecord);
//				System.out.println(insertRecord);
//			}

			//this is for all people editors and authors...
			for (int i = 0; i < editorList.size(); i++) {
				insertRecord = "insert into tbl_people (id, name) values(" + 
						editorTable.get(editorList.get(i)) + "," + '"' + editorList.get(i) + '"' + ");";
				myDBStm.executeUpdate(insertRecord);
				//System.out.println(insertRecord);
			}

			for (int i = 0; i < booktitleList.size(); i++) {
				insertRecord = "insert into tbl_booktitle (id, title) values(" + booktitleTable.get(booktitleList.get(i)) + ","
						+ '"' + booktitleList.get(i) + '"' + ");";
				try{
					myDBStm.executeUpdate(insertRecord);
				}
				catch(SQLException e)
				{
					System.out.println("found a duplicate key in insert: " + insertRecord);
				}
				//System.out.println(insertRecord);
			}

			for (int i = 0; i < publisherList.size(); i++) {
				insertRecord = "insert into tbl_publisher (id, publisher_name) values(" + publisherTable.get(publisherList.get(i)) + ","
						+ '"' + publisherList.get(i) + '"' + ");";
				try{
					myDBStm.executeUpdate(insertRecord);
				}
				catch(SQLException e)
				{
					System.out.println("found a duplicate key in publisher: " + insertRecord);
				}
			}
			
//			connection.commit();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parseDocument() {

		// get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			// get a new instance of parser
			SAXParser sp = spf.newSAXParser();

//			String source = "small_final-data.xml";
//			String result = "small_final-data2.xml";

			String source = "big_dblp-data.xml";
			String result = "big_dblp-data2.xml";
			
			System.out.println("removing lines...");
			Scanner s = new Scanner(new File(source));
			FileWriter w = new FileWriter(result);
			while (s.hasNext())
				w.write(s.nextLine() + " ");
			w.close();
			System.out.println("remove successfull.");

			// parse the file and also register this class for call backs
			sp.parse("big_dblp-data2.xml", this);
			sp.parse("small_final-data2.xml", this);

		} catch (SAXException se) {
			se.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}



	// Event Handlers
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// reset
		tempVal = "";
		if (qName.equalsIgnoreCase("Book")) {
			// create a new instance of employee
			tempBook = new Book();
			tempBook.setType(attributes.getValue("mdate"));
		}
		if (qName.equalsIgnoreCase("title")) {
			titleBool = true;
		}
		if (qName.equalsIgnoreCase("booktitle")) {
			booktitleBool = true;
		}
		if (qName.equalsIgnoreCase("publisher")) {
			publisherBool = true;
		}
		if (qName.equalsIgnoreCase("pages")) {
			pagesBool = true;
		}
		if (qName.equalsIgnoreCase("year")) {
			yearBool = true;
		}
		if (qName.equalsIgnoreCase("volume")) {
			volumeBool = true;
		}
		if (qName.equalsIgnoreCase("number")) {
			numberBool = true;
		}
		if (qName.equalsIgnoreCase("url")) {
			urlBool = true;
		}
		if (qName.equalsIgnoreCase("ee")) {
			eeBool = true;
		}
		if (qName.equalsIgnoreCase("cdrom")) {
			cdromBool = true;
		}
		if (qName.equalsIgnoreCase("cite")) {
			citeBool = true;
		}
		if (qName.equalsIgnoreCase("crossref")) {
			crossrefBool = true;
		}
		if (qName.equalsIgnoreCase("isbn")) {
			isbnBool = true;
		}
		if (qName.equalsIgnoreCase("series")) {
			seriesBool = true;
			series = attributes.getValue("href");
		}
		if (qName.equalsIgnoreCase("author")) {
			authorBool = true;
		}
		if (qName.equalsIgnoreCase("editor")) {
			editorBool = true;
		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (titleBool) {
			title = new String(ch, start, length);
			title = title.trim();
			if(title.contains("'") || title.contains("\"")){
				title = title.replace("'", "");
				title = title.replace("\"", "");
			}
			titleBool = false;
			//System.out.println("title is " + title);
		}
		if (booktitleBool) {
			booktitle = new String(ch, start, length);
			booktitle = booktitle.trim();
			booktitleBool = false;
			booktitleList.add(booktitle);
			//System.out.println("booktitle is " + booktitle);
		}
		if (publisherBool) {
			publisher = new String(ch, start, length);
			publisher = publisher.trim();
			publisherList.add(publisher);
			publisherBool = false;
			//System.out.println("publisher is " + publisher);
		}
		if (pagesBool) {
			int te = pages.indexOf("-")+1;
			pages = new String(ch, start, length);
			if(pages.contains("-"))
			{
				pages = pages.trim();
				int index = pages.indexOf("-");
				try{
				startPage = Integer.parseInt(pages.substring(0, index));
				endPage = Integer.parseInt(pages.substring(index + 1));
				}
				catch(NumberFormatException e)
				{
					System.out.println("found an inconsistency with pages: " + pages);
					startPage = 0; 
					endPage = 0;
				}
				pagesBool = false;
//				System.out.println("pages is " + pages);
//				System.out.println("start page is " + startPage + " end page is "
//						+ endPage);
			}
		}
		if (yearBool) {
			String yearString = new String(ch, start, length);
			yearString = yearString.trim();
			year = Integer.parseInt(yearString);
			yearBool = false;
			//System.out.println("year is " + year);
		}
		if (volumeBool) {
			String volumeString = new String(ch, start, length);
			volumeString = volumeString.trim();
			try{
			volume = Integer.parseInt(volumeString);
			}
			catch(NumberFormatException e)
			{
				System.out.println("found an inconsistency in volume: " + volumeString);
				volume = 0;
			}
			volumeBool = false;
			//System.out.println("volume is " + volume);
		}
		if (numberBool) {
			String numberString = new String(ch, start, length);
			numberString = numberString.trim();
			number = Integer.parseInt(numberString);
			numberBool = false;
			//System.out.println("number is " + number);
		}
		if (urlBool) {
			url = new String(ch, start, length);
			url = url.trim();
			urlBool = false;
			//System.out.println("url is " + url);
		}
		if (eeBool) {
			ee = new String(ch, start, length);
			ee = ee.trim();
			eeBool = false;
			//System.out.println("ee is " + ee);
		}
		if (cdromBool) {
			cdrom = new String(ch, start, length);
			cdrom = cdrom.trim();
			cdromBool = false;
			//System.out.println("cdrom is " + cdrom);
		}
		if (citeBool) {
			cite = new String(ch, start, length);
			cite = cite.trim();
			citeBool = false;
			//System.out.println("cite is " + cite);
		}
		if (crossrefBool) {
			crossref = new String(ch, start, length);
			crossref = crossref.trim();
			crossrefBool = false;
			//System.out.println("crossref is " + crossref);
		}
		if (isbnBool) {
			isbn = new String(ch, start, length);
			isbn = isbn.trim();
			isbnBool = false;
			//System.out.println("isbn is " + isbn);
		}
		//System.out.println("series is " + series);
		if (authorBool) {
			String authorTest = new String(ch, start, length);
			authorTest = authorTest.trim();
			editorList.add(authorTest);
			author = authorTest;
			authorBool = false;
		}
		if (editorBool) {
			String editorTest = new String(ch, start, length);
			editorTest = editorTest.trim();
			editorList.add(editorTest);
			editor = editorTest;
			editorBool = false;
		}

		tempVal = new String(ch, start, length);

		// System.out.println("temp val is " + tempVal);
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		try {

			// Incorporate mySQL driver
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			// Connect to MySQL as root
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://", "root", "");

			// Create and execute an SQL statement to get all the database names
			Statement myDBStm = connection.createStatement();
			ResultSet resultDB = myDBStm.executeQuery("use moviedb");
			String insertRecord = "";

			if(qName.equalsIgnoreCase("editor"))
			{
				if(!editorTable.containsValue(editor))
				{
					editorTable.put(editor, editorID);
					//System.out.println("we put in editor " + editor + editorID);
				}
			}
			
			else if (qName.equalsIgnoreCase("author")) {
				int test = generator.nextInt();
				if(!editorTable.containsValue(author))
				{
					editorTable.put(author, test);
//					System.out.println("we put in editor " + editor + editorID);
				}
				multipleAuthorsList.add(test);
			} 
			else if (qName.equalsIgnoreCase("booktitle")) {
				int test = generator.nextInt();
				if(!booktitleTable.containsValue(booktitle))
				{
					booktitleTable.put(booktitle, test);
					//System.out.println("we put in booktitle " + booktitle + booktitleID);
				}

			} else if (qName.equalsIgnoreCase("publisher")) {
				if(!publisherTable.containsValue(publisher))
				{
					publisherTable.put(publisher, publisherID);
//					System.out.println("we put in  " + booktitle + booktitleID);
				}
			}
			else if(qName.equalsIgnoreCase("article") || qName.equalsIgnoreCase("inproceedings")
					|| qName.equalsIgnoreCase("proceedings") || qName.equalsIgnoreCase("book") || 
					qName.equalsIgnoreCase("incollection") || qName.equalsIgnoreCase("phdthesis") || 
					qName.equalsIgnoreCase("mastersthesis") || qName.equalsIgnoreCase("www"))
			{
				int genreID = -99999;
				if(qName.equals("article"))
				{
					genreID = 1;
				}
				else if(qName.equals("inproceedings"))
				{
					genreID = 2;
				}
				else if(qName.equals("proceedings"))
				{
					genreID = 3;
				}
				else if(qName.equals("books"))
				{
					genreID = 4;
				}
				else if(qName.equals("incollection"))
				{
					genreID = 5;
				}
				else if(qName.equals("phdthesis"))
				{
					genreID = 6;
				}
				else if(qName.equals("mastersthesis"))
				{
					genreID = 7;
				}
				else if(qName.equals("www"))
				{
					genreID = 8;
				}				
				

				
				insertRecord = "insert into tbl_dblp_document (id, title, start_page, end_page, year, " +
						"volume, number, url, ee, cdrom, cite, crossref, isbn, series, editor_id, " +
						"booktitle_id, genre_id, publisher_id) values(" + 
						'"' + dblpID + '"' + "," + 
						'"' + title + '"' + "," + 
						'"' + startPage + '"' + "," +
						'"' + endPage + '"' + "," + 
						'"' + year + '"' + "," + 
						'"' + volume + '"' + "," + 
						'"' + number + '"' + "," + 
						'"' + url + '"' + "," + 
						'"' + ee + '"' + "," + 
						'"' + cdrom + '"' + "," + 
						'"' + cite + '"' + "," + 
						'"' + crossref + '"' + "," + 
						'"' + isbn + '"' + "," +
						'"' + series + '"' + "," + 
						editorTable.get(editor) + "," +
						booktitleTable.get(booktitle) + "," +
						genreID + "," +
						publisherTable.get(publisher) + ");";
//						'"' + editorTable.get(editor) + '"' + "," + 
//						'"' + booktitleTable.get(booktitle) + '"' + "," + 
//						'"' + genreID + '"' + "," + 
//						'"' + publisherTable.get(publisher) + '"' + ");";
				
				dblpID++;
				
				
//				dblp_queries.add(insertRecord);
				
//				connection.setAutoCommit(false);
//				psInsertRecord=connection.prepareStatement(insertRecord);
//				psInsertRecord.addBatch();
				
				
				myDBStm.executeUpdate(insertRecord);
				//System.out.println(insertRecord);
				editorID++;
				booktitleID++;
				publisherID++;
//				authorID++;
				//	connection.setAutoCommit(false);
//				psInsertRecord = connection.prepareStatement(insertRecord);
				for(int i = 0; i < multipleAuthorsList.size(); i++)
				{
					String author_doc_record = "insert into tbl_author_document_mapping (id, doc_id, author_id) values (null," + dblpID + "," + multipleAuthorsList.get(i) + ");";

//					mapping_queries.add(author_doc_record);
//					connection.setAutoCommit(false);
//					author_mapping_PsInsertRecord=connection.prepareStatement(author_doc_record);
//					author_mapping_PsInsertRecord.addBatch();
					
					myDBStm.executeUpdate(author_doc_record);
					//System.out.println(author_doc_record);
				}
				multipleAuthorsList.clear();
//				author_mapping_PsInsertRecord.executeBatch();
//				psInsertRecord.executeBatch();
//				connection.commit();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("something is inconsistent when parsing document end tag: " + e.getErrorCode());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		 long startTime = System.currentTimeMillis();
		SAXParser_proj4 spe = new SAXParser_proj4();
		spe.runExample();
		  long endTime = System.currentTimeMillis();
		  System.out.println("Total elapsed time in execution of method callMethod() is :"+ (endTime-startTime));
		  
		  
	}

}
