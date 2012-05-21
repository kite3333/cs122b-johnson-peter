

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class SAXParser_proj4 extends DefaultHandler{

	List myEmpls;
	
	private String tempVal;
	
	//to maintain context
	private Book tempBook;
	
	boolean titleBool = false;
	String title = "";
	
	boolean booktitleBool = false;
	String booktitle = "";
	
	boolean publisherBool = false;
	String publisher = "";
	
	boolean pagesBool = false;
	String pages = "";
	int startPage;
	int endPage;
	
	boolean yearBool = false;
	int year;
	
	boolean volumeBool = false;
	int volume;
	
	boolean numberBool = false;
	int number;
	
	boolean urlBool = false;
	String url;
	
	boolean eeBool = false;
	String ee;
	
	boolean cdromBool = false;
	String cdrom;
	
	boolean citeBool = false;
	String cite;
	
	boolean crossrefBool = false;
	String crossref;
	
	boolean isbnBool = false;
	String isbn;
	
	boolean seriesBool = false;
	String series;
	
	boolean authorBool = false;
	ArrayList author = new ArrayList();
	
	boolean editorBool = false;
	ArrayList editor = new ArrayList();
	
	
	public SAXParser_proj4(){
		myEmpls = new ArrayList();
	}
	
	public void runExample() {
		parseDocument();
		printData();
	}

	private void parseDocument() {
		
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
			String source = "small_final-data.xml";
            String result = "small_final-data2.xml";

            System.out.println("removing lines...");
            Scanner s = new Scanner(new File(source));                      
            FileWriter w = new FileWriter(result);                  
            while(s.hasNext())                              
                    w.write(s.nextLine() + " ");                  
            w.close();                      
            System.out.println("remove successfull.");
			
			//parse the file and also register this class for call backs
			sp.parse("small_final-data2.xml", this);
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	/**
	 * Iterate through the list and print
	 * the contents
	 */
	private void printData(){
		
		System.out.println("No of Employees '" + myEmpls.size() + "'.");
		
		Iterator it = myEmpls.iterator();
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
	}
	

	//Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//reset
		tempVal = "";
		if(qName.equalsIgnoreCase("Book")) {
			//create a new instance of employee
			tempBook = new Book();
			tempBook.setType(attributes.getValue("mdate"));
		}
		if(qName.equalsIgnoreCase("title"))
		{
			titleBool = true;
		}
		if(qName.equalsIgnoreCase("booktitle"))
		{
			booktitleBool = true;
		}
		if(qName.equalsIgnoreCase("publisher"))
		{
			publisherBool = true;
		}
		if(qName.equalsIgnoreCase("pages"))
		{
			pagesBool = true;
		}
		if(qName.equalsIgnoreCase("year"))
		{
			yearBool = true;
		}
		if(qName.equalsIgnoreCase("volume"))
		{
			volumeBool = true;
		}
		if(qName.equalsIgnoreCase("number"))
		{
			numberBool = true;
		}
		if(qName.equalsIgnoreCase("url"))
		{
			urlBool = true;
		}
		if(qName.equalsIgnoreCase("ee"))
		{
			eeBool = true;
		}
		if(qName.equalsIgnoreCase("cdrom"))
		{
			cdromBool = true;
		}
		if(qName.equalsIgnoreCase("cite"))
		{
			citeBool = true;
		}
		if(qName.equalsIgnoreCase("crossref"))
		{
			crossrefBool = true;
		}
		if(qName.equalsIgnoreCase("isbn"))
		{
			isbnBool = true;
		}
		if(qName.equalsIgnoreCase("series"))
		{
			seriesBool = true;
			series = attributes.getValue("href");
		}
		if(qName.equalsIgnoreCase("author"))
		{
			authorBool = true;
		}
		if(qName.equalsIgnoreCase("editor"))
		{
			editorBool = true;
		}
	}
	

	public void characters(char[] ch, int start, int length) throws SAXException {
		
		if(titleBool)
		{
			title = new String(ch,start,length);
			title = title.trim();
			titleBool = false;
			System.out.println("title is " + title);
		}
		if(booktitleBool)
		{
			booktitle = new String(ch,start,length);
			booktitle = booktitle.trim();
			booktitleBool = false;
			System.out.println("booktitle is " + booktitle);
		}
		if(publisherBool)
		{
			publisher = new String(ch,start,length);
			publisher = publisher.trim();
			publisherBool = false;
			System.out.println("publisher is " + publisher);
		}
		if(pagesBool)
		{
			pages = new String(ch,start,length);
			pages = pages.trim();
			int index = pages.indexOf("-");
			startPage = Integer.parseInt(pages.substring(0, index));
			endPage = Integer.parseInt(pages.substring(index+1));
			pagesBool = false;
			System.out.println("pages is " + pages);
			System.out.println("start page is " + startPage + " end page is " + endPage);
		}
		if(yearBool)
		{
			String yearString = new String(ch,start,length);
			yearString = yearString.trim();
			year = Integer.parseInt(yearString);
			yearBool = false;
			System.out.println("year is " + year);
		}
		if(volumeBool)
		{
			String volumeString = new String(ch,start,length);
			volumeString = volumeString.trim();
			volume = Integer.parseInt(volumeString);
			volumeBool = false;
			System.out.println("volume is " + volume);
		}
		if(numberBool)
		{
			String numberString = new String(ch,start,length);
			numberString = numberString.trim();
			number = Integer.parseInt(numberString);
			numberBool = false;
			System.out.println("number is " + number);
		}
		if(urlBool)
		{
			url = new String(ch,start,length);
			url = url.trim();
			urlBool = false;
			System.out.println("url is " + url);
		}
		if(eeBool)
		{
			ee = new String(ch,start,length);
			ee = ee.trim();
			eeBool = false;
			System.out.println("ee is " + ee);
		}
		if(cdromBool)
		{
			cdrom = new String(ch,start,length);
			cdrom = cdrom.trim();
			cdromBool = false;
			System.out.println("cdrom is " + cdrom);
		}
		if(citeBool)
		{
			cite = new String(ch,start,length);
			cite = cite.trim();
			citeBool = false;
			System.out.println("cite is " + cite);
		}
		if(crossrefBool)
		{
			crossref = new String(ch,start,length);
			crossref = crossref.trim();
			crossrefBool = false;
			System.out.println("crossref is " + crossref);
		}
		if(isbnBool)
		{
			isbn = new String(ch,start,length);
			isbn = isbn.trim();
			isbnBool = false;
			System.out.println("isbn is " + isbn);
		}
		System.out.println("series is " + series);
		if(authorBool)
		{
			String authorTest = new String(ch,start,length);
			authorTest = authorTest.trim();
			author.add(authorTest);
			authorBool = false;
			
			for(int i = 0; i < author.size(); i++)
			{System.out.println("author is " + author.get(i));}
		}
		if(editorBool)
		{
			String editorTest = new String(ch,start,length);
			editorTest = editorTest.trim();
			editor.add(editorTest);
			editorBool = false;
			
			for(int i = 0; i < editor.size(); i++)
			{System.out.println("editor is " + editor.get(i));}
		}
		
		
		tempVal = new String(ch,start,length);

		
		//System.out.println("temp val is " + tempVal);
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if(qName.equalsIgnoreCase("Employee")) {
			//add it to the list
			myEmpls.add(tempBook);
			
		}else if (qName.equalsIgnoreCase("Title")) {
			title = "";
			
		}else if (qName.equalsIgnoreCase("booktitle")) {
			booktitle = "";
		}
		
		
		try {
			// Incorporate mySQL driver
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	            
			 // Connect to MySQL as root
			Connection connection = DriverManager.getConnection("jdbc:mysql://","root", "");
			
			// Create and execute an SQL statement to get all the database names
			Statement myDBStm = connection.createStatement();
			ResultSet resultDB = myDBStm.executeQuery("show databases");
			
			
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
	
	public static void main(String[] args){
		SAXParser_proj4 spe = new SAXParser_proj4();
		spe.runExample();
	}
	
}




