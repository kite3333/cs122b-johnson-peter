import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			String input = in.readLine();
			System.out.println(">" + input + "<");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
