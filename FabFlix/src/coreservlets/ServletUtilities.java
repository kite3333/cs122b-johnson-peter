package coreservlets;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.*;

/** Some simple time savers. Static methods. */

public class ServletUtilities {
	
	public static String headWithTitle(String title) {
		String topPart = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">" +
	    		"\n<html>";
	    String head = "\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">" +
    		"<link rel=\"stylesheet\" href=\"./css/styles.css\" type=\"text/css\"/>\n<title>" + title + "</title>" + 
    		"\n</head>\n<body>";
      return topPart + head;
  }
	
	public static String pageEnd() {
		return "</body>\n</html>";
	}

  /** Read a parameter with the specified name, convert it
   *  to an int, and return it. Return the designated default
   *  value if the parameter doesn't exist or if it is an
   *  illegal integer format.
   */
  
	public static void printResults(ResultSet result, PrintWriter out) throws SQLException {
		if (result.next()) {
			result.beforeFirst();
			while (result.next()) {
			    out.println("<img src=\"" + result.getString(5) + "\"><br />");
			    out.println("<b>Movie Id:</b> " + result.getInt(1) + "<br />");
			    out.println("<b>Title:</b> " + result.getString(2) + "<br />");
			    out.println("<b>Year:</b> " + result.getInt(3) + "<br />");
			    out.println("<b>Director:</b> " + result.getString(4) + "<br />");
			    out.println("<b>Trailer URL:</b> " + result.getString(6) + "<br />");
			    out.println("<br />");
			}
		} else {
			out.println("<p>No Results.</p>");
		}
	}
  
  public static int getIntParameter(HttpServletRequest request,
                                    String paramName,
                                    int defaultValue) {
    String paramString = request.getParameter(paramName);
    int paramValue;
    try {
      paramValue = Integer.parseInt(paramString);
    } catch(Exception nfe) { // null or bad format
      paramValue = defaultValue;
    }
    return(paramValue);
  }

  /** Reads param and converts to double. Default if problem. */
  
  public static double getDoubleParameter(HttpServletRequest request,
                                          String paramName,
                                          double defaultValue) {
    String paramString = request.getParameter(paramName);
    double paramValue;
    try {
      paramValue = Double.parseDouble(paramString);
    } catch(Exception nfe) { // null or bad format
      paramValue = defaultValue;
    }
    return(paramValue);
  }

  /** Replaces characters that have special HTML meanings
   *  with their corresponding HTML character entities.
   *  Specifically, given a string, this method replaces all 
   *  occurrences of  
   *  {@literal
   *  '<' with '&lt;', all occurrences of '>' with
   *  '&gt;', and (to handle cases that occur inside attribute
   *  values), all occurrences of double quotes with
   *  '&quot;' and all occurrences of '&' with '&amp;'.
   *  Without such filtering, an arbitrary string
   *  could not safely be inserted in a Web page.
   *  }
   */

  public static String filter(String input) {
    if (!hasSpecialChars(input)) {
      return(input);
    }
    StringBuilder filtered = new StringBuilder(input.length());
    char c;
    for(int i=0; i<input.length(); i++) {
      c = input.charAt(i);
      switch(c) {
        case '<': filtered.append("&lt;"); break;
        case '>': filtered.append("&gt;"); break;
        case '"': filtered.append("&quot;"); break;
        case '&': filtered.append("&amp;"); break;
        default: filtered.append(c);
      }
    }
    return(filtered.toString());
  }

  private static boolean hasSpecialChars(String input) {
    boolean flag = false;
    if ((input != null) && (input.length() > 0)) {
      char c;
      for(int i=0; i<input.length(); i++) {
        c = input.charAt(i);
        switch(c) {
          case '<': flag = true; break;
          case '>': flag = true; break;
          case '"': flag = true; break;
          case '&': flag = true; break;
        }
      }
    }
    return(flag);
  }
  
  private ServletUtilities() {} // Uninstantiatable class
}
