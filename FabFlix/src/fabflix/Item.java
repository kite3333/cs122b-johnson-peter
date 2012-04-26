package fabflix;

public class Item {
	private String title; //Included for user ease of reading (vs ID).
	private int id;
	private int quantity;
	
	public Item(String newTitle, int newID, int newQuant) {
		title = newTitle;
		id = newID;
		quantity = newQuant;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getID() {
		return id;
	}
	
	public int getQuant() {
		return quantity;
	}
	
	public void setQuant(int newQuant) {
		quantity = newQuant;
	}
}
