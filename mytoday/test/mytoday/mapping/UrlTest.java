package mytoday.mapping;

import static org.junit.Assert.*;

import org.junit.Test;

public class UrlTest {

	@Test
	public void test() {
		assertEquals(Url.getGetMapper().findMethod("/home.my").getName(), "home");
	}

}
