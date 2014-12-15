package mytoday.object;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class ContentTest {

	@Test
	public void test() {
		Content con = new Content(10);
		System.out.println(con);
		con.set();
		System.out.println(con);
	}

	@Test
	public void insertTest() {
		Content con = new Content(null, "free", "zerohouse", "sqltest",
				"sqltest", new Date());
		assertTrue(con.insert());
	}
	
	@Test
	public void updateTest() {
		Content con = new Content(64, "free", "zdf", "ss",
				"sqltest", new Date());
		assertTrue(con.update());
	}
	
	@Test
	public void updateTestParam() {
		Content con = new Content(64, "sdgg", "zdf", "ss",
				"sqltest", new Date());
		assertTrue(con.update("userid='zdf'"));
	}
	
	@Test
	public void deleteTestParam() {
		Content con = new Content(63, "sdgg", "zdf", "ss",
				"sqltest", new Date());
		assertTrue(con.delete("userid='zerohouse'"));
	}
}
