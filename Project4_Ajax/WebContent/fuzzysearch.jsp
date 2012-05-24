<%@ page language="java" 
	import = "coreservlets.ServletUtilities" 
%>

<!-- source here 
http://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java -->
<script type="text/javascript">
function ed(
  a, b, // the 2 strings to compare
           // now the placeholder arguments:
  c, d, // two row of the distance matrix
  e, f, // counters to loop through a and b
  g // the last computed distance
){
  for(d=[e=0];a[e];e++) // loop through a and reset the 1st distance
    for(c=[f=0];b[++f];) // loop through b and reset the 1st col of the next row
      g=
      d[f]=
        e? // not the first row ?
        1+Math.min( // then compute the cost of each change
          d[--f],
          c[f]-(a[e-1]==b[f]),
          c[++f]=d[f] // and copy the previous row of the distance matrix
        )
        : // otherwise
        f; // init the very first row of the distance matrix
  return g
}
</script>

<script type="text/javascript">
document.write(ed("a","Miss Congeniality 2: ARMED AND FABULOUS"));
document.write(" SKIP ");
document.write(ed("a","Star Wars: Episode III - Revenge of the Sith"));
document.write(" SKIP ");
document.write(ed("troy","terminator"));

</script>
<%

out.print(ServletUtilities.headWithTitle("Fabflix - Search"));
%>
<h1>Search</h1>
<form action="./checkout.jsp">
<input type="submit" value="Go to Checkout" /></form>
<form name="search" action="./fuzzymovieList.jsp" method="get">
	Title: <input type="text" name="title" /><br />
	Year: <input type="text" name="year" /><br />
	Director: <input type="text" name="director"><br />
	Actor's First Name: <input type="text" name="actor_first"><br />
	Actor's Last Name: <input type="text" name="actor_last"><br />
	<input type="submit" value="Submit" />
</form>
<% out.println(ServletUtilities.pageEnd()); %>