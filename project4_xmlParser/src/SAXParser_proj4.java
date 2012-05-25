import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
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
	private StringBuilder insertRecord = new StringBuilder();
	private StringBuilder inconsistencies = new StringBuilder();
	private StringBuilder author_doc_record = new StringBuilder();

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
		insertRecord.append("INSERT INTO tbl_dblp_document (id, title, start_page, end_page, year, ");
		insertRecord.append("volume, number, url, ee, cdrom, cite, crossref, isbn, series, editor_id, ");
		insertRecord.append("booktitle_id, genre_id, publisher_id) VALUES");
	}

	public void runExample() throws IOException {
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
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", "root", "");
			Statement myDBStm = connection.createStatement();

			BufferedWriter writer = new BufferedWriter(new FileWriter("test.sql"));
			
//			System.out.println(inconsistencies.toString());
			insertRecord.replace(insertRecord.length() - 1, insertRecord.length(), ";");
			writer.write("----Insert Record Query----");
			writer.newLine();
			writer.write(insertRecord.toString());
			writer.newLine();
			myDBStm.executeUpdate(insertRecord.toString());
			author_doc_record.replace(author_doc_record.length() - 1, author_doc_record.length(), ";");
			writer.write("----Author-Doc Record Query----");
			writer.newLine();
			writer.write(author_doc_record.toString());
			writer.newLine();
			myDBStm.executeUpdate(author_doc_record.toString());
			myDBStm.executeUpdate("insert into tbl_genres (id, genre_name) values(1, 'article'), (2, 'inproceedings')," + 
					"(3, 'proceedings'), (4, 'book'), (5, 'incollection'), (6, 'phdthesis'), (7, 'mastersthesis'), (8, 'www');");
			
			//this is for all people editors and authors...
			insertRecord = new StringBuilder();
			String insertRecord = null;
			for (int i = 0; i < editorList.size(); i++) {
				insertRecord = "insert into tbl_people (id, name) values(" + 
						editorTable.get(editorList.get(i)) + "," + '"' + editorList.get(i) + '"' + ");";
				try {
					myDBStm.executeUpdate(insertRecord);
				}
				catch (SQLException e) {
					System.out.println("found a duplicate key in insert: " + insertRecord);
				}
			}
			System.out.println("Finished editor list");
			for (int i = 0; i < booktitleTable.size(); i++) {
				insertRecord = "insert into tbl_booktitle (id, title) values(" + booktitleTable.get(booktitleList.get(i)) + ","
						+ '"' + booktitleList.get(i) + '"' + ");";
				try{
					myDBStm.executeUpdate(insertRecord);
				}
				catch(SQLException e)
				{
					System.out.println("found a duplicate key in insert: " + insertRecord);
				}
			}
			System.out.println("Finished book title list");

			for (int i = 0; i < publisherTable.size(); i++) {
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
			System.out.println("remove successful.");

			// parse the file and also register this class for call backs
			sp.parse("big_dblp-data2.xml", this);
//			sp.parse("small_final-data2.xml", this);

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
		}
		if (booktitleBool) {
			booktitle = new String(ch, start, length);
			booktitle = booktitle.trim();
			booktitleBool = false;
			booktitleList.add(booktitle);
		}
		if (publisherBool) {
			publisher = new String(ch, start, length);
			publisher = publisher.trim();
			publisherList.add(publisher);
			publisherBool = false;
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
					inconsistencies.append("found an inconsistency with pages: " + pages + "\r\n");
					startPage = 0; 
					endPage = 0;
				}
				pagesBool = false;
			}
		}
		if (yearBool) {
			String yearString = new String(ch, start, length);
			yearString = yearString.trim();
			year = Integer.parseInt(yearString);
			yearBool = false;
		}
		if (volumeBool) {
			String volumeString = new String(ch, start, length);
			volumeString = volumeString.trim();
			try{
			volume = Integer.parseInt(volumeString);
			}
			catch(NumberFormatException e)
			{
				inconsistencies.append("found an inconsistency in volume: " + volumeString + "\r\n");
				volume = 0;
			}
			volumeBool = false;
		}
		if (numberBool) {
			String numberString = new String(ch, start, length);
			numberString = numberString.trim();
			number = Integer.parseInt(numberString);
			numberBool = false;
		}
		if (urlBool) {
			url = new String(ch, start, length);
			url = url.trim();
			urlBool = false;
		}
		if (eeBool) {
			ee = new String(ch, start, length);
			ee = ee.trim();
			eeBool = false;
		}
		if (cdromBool) {
			cdrom = new String(ch, start, length);
			cdrom = cdrom.trim();
			cdromBool = false;
		}
		if (citeBool) {
			cite = new String(ch, start, length);
			cite = cite.trim();
			citeBool = false;
		}
		if (crossrefBool) {
			crossref = new String(ch, start, length);
			crossref = crossref.trim();
			crossrefBool = false;
		}
		if (isbnBool) {
			isbn = new String(ch, start, length);
			isbn = isbn.trim();
			isbnBool = false;
		}
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

	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {

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

		} 
		else if (qName.equalsIgnoreCase("publisher")) {
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
						
			insertRecord.append("(\"" + dblpID + "\",\"" + title + "\",\"" + startPage + "\",\"");
			insertRecord.append(endPage + "\",\"" + year + "\",\"" + volume + "\",\"" + number + "\",\"");
			insertRecord.append(url + "\",\"" + ee + "\",\"" + cdrom + "\",\"" + cite + "\",\"" + crossref + "\",\""); 
			insertRecord.append(isbn + "\",\"" + series + "\"," + editorTable.get(editor) + "," + booktitleTable.get(booktitle) + ",");
			insertRecord.append(genreID + "," + publisherTable.get(publisher) + "),");
			
			dblpID++;
			editorID++;
			booktitleID++;
			publisherID++;
			authorID++;
			for(int i = 0; i < multipleAuthorsList.size(); i++)
			{
				if (author_doc_record.length() == 0) {
				author_doc_record.append("insert into tbl_author_document_mapping (id, doc_id, author_id) values");
				}
				author_doc_record.append("(null," + dblpID + "," + multipleAuthorsList.get(i) + "),");		
			}
			multipleAuthorsList.clear();
		}
	}

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		SAXParser_proj4 spe = new SAXParser_proj4();
		spe.runExample();
		long endTime = System.currentTimeMillis();
		System.out.println("Total elapsed time: less than "+ ((endTime-startTime) / 60000 + 1) + " minutes."); 
	}

}
