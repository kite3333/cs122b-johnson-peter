
public class playground {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String test = "bob's diary\"s";
		
		if(test.contains("'") || test.contains("\""))
		{

			test = test.replace("'", "");
			test = test.replace("\"", "");
			System.out.println(test);
		}
		
		
		
	}

}
