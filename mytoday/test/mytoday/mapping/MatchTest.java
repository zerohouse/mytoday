package mytoday.mapping;

import static org.junit.Assert.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

public class MatchTest {

	Match m;

	@Before
	public void setup() {
		m = new Match();
		m.put("s", this.getClass().getMethods()[0]);
		m.put("s/{}/{}/{}", this.getClass().getMethods()[0]);
	}

	@Test
	public void test() {
		System.out.println(m.get("s").getMethod().getName());
		System.out.println(m.get("s/sdfgf/asdf/sdf").getParams());

	}

	@Test
	public void regexTest() {
		Pattern p = Pattern.compile("s/(.*)");
		Matcher m = p.matcher("s/asdasd");
		String s = "{}{}";
		System.out.println(s.replace("{}", "(.*)"));
		assertTrue(m.matches());

	}

}
