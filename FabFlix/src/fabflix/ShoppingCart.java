package fabflix;

import java.util.HashMap;
import java.util.Set;

public class ShoppingCart {

	//Stores the items. Key = Movie ID, Value = Quantity.
	private HashMap<Integer, Item> cart;
	private String customerID;
	
	public ShoppingCart(String custID) {
		customerID = custID;
		cart = new HashMap<Integer, Item>();
	}
	
	public void addItem(String title, int movieID, int quantity) {
		if (cart.containsKey(movieID)) { //Add quantity to existing item
			cart.get(movieID).setQuant(cart.get(movieID).getQuant() + quantity);
		}
		else { //Insert new item
			cart.put(movieID, new Item(title, movieID, quantity));
		}
	}
	
	public void addItem(String title, int movieID) {
		addItem(title, movieID, 1);
	}
	
	public void addItem(Item newItem) {
		cart.put(newItem.getID(), newItem);
	}
	
	//Removes item from cart completely. To change quantity, see updateItem
	public void removeItem(int movieID) {
		cart.remove(movieID);
	}
	
	public void updateItem(int movieID, int newQuant) {
		if (cart.containsKey(movieID)) {
			if (newQuant > 0) {
				cart.get(movieID).setQuant(newQuant);
			}
			else {
				cart.remove(movieID);
			}
		} //WARNING: NO notification if condition fails
	}
	
	public boolean isEmpty() {
		if (size() > 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public int size() {
		return cart.size();
	}
	
	public Set<Integer> getMovieIDs() {
		return cart.keySet();
	}
	
	public String getCustID() {
		return customerID;
	}
	
	//Returns Item or null identified by movieID
	public Item getItem(int movieID) {
		return cart.get(movieID);
	}
	
	//Makes an INSERT query for one cart item.
	public String makeSQLQuery(int movieID) {
		if (cart.containsKey(movieID)) {
			return ("INSERT INTO shoppingcarts (custID, movieID, title, quant)"
				+ "VALUES(" + customerID + ", " + movieID + ", '" + cart.get(movieID).getTitle()
				+ "', " + cart.get(movieID).getQuant());
		}
		else {
			return null; //WARNING: Might lead to null errors
		}
	}
	
	//For inserting a shopping cart into the database. Returns null if the cart is empty.
	public String[] makeSQLQueryArray() {
		String[] queries = null;
		if (cart.size() > 0) {
			queries = new String[cart.size()];
			//I want an array so that I can iterate using int i
			Integer[] movieIDs = cart.keySet().toArray(new Integer[0]);
			for (int i = 0; i < movieIDs.length; i++) {
				queries[i] = makeSQLQuery(movieIDs[i].intValue());
			}
		}
		return queries;
	}
	
}
