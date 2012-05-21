

public class Book {

	private String name;

	private String editor;
	
	private int id;

	private String type;
	
	public Book(){
		
	}
	
	public Book(String name, int id, String editor,String type) {
		this.name = name;
		this.editor = editor;
		this.id  = id;
		this.type = type;
		
	}
	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}	
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Employee Details - ");
		sb.append("Name:" + getName());
		sb.append(", ");
		sb.append("Type:" + getType());
		sb.append(", ");
		sb.append("Id:" + getId());
		sb.append(", ");
		sb.append("Editor:" + getEditor());
		sb.append(".");
		
		return sb.toString();
	}
}
