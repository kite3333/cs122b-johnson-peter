import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		int size = 0;
		String largestTitle;
		String nextString;
		BufferedReader reader = new BufferedReader(new FileReader("big_dblp-data.xml"));
		while (reader.ready()) {
			 nextString = reader.readLine();
			if (nextString.contains("<title>")) {
				nextString = nextString.substring(nextString.indexOf("<title>") + 7, nextString.indexOf("</title>"));
				if (size < nextString.length()) {
					largestTitle = nextString;
					size = nextString.length();
					System.out.println("New Largest String: " + largestTitle);
				}
			}
		}
		System.out.println("Largest Size: " + size);		

	}

}
