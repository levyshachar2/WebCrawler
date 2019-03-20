package webcrawler.testUnits;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegulerExpressionTests {
	private static String regExEmail = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
	
	 @Test
	  public void emailShouldMatch() {

	    // MyClass is tested
	    String email = "israel@gmail.com";

	    Pattern pattern = Pattern.compile(regExEmail, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(email);
	    assertNotNull(matcher.matches());
	  }
}
