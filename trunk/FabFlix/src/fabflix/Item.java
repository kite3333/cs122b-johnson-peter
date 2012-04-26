package fabflix;

public class Item {
	public String title; //Included for user ease of reading (vs ID).
	public int id;
	public int quantity;
	
	public Item(String newTitle, int newID, int newQuant) {
		title = newTitle;
		id = newID;
		quantity = newQuant;
	}
}
