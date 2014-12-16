package mytoday.dao;

import static org.junit.Assert.*;

import java.util.Date;

import mytoday.object.Content;

import org.junit.Test;

public class AccessTest {

	@Test
	public void insertTest() {
		Content con = new Content(null, "dfdfdf", "zdf", "ss",
				"sqltest", new Date());
		assertTrue(Access.insert(con));
	}
	
	@Test
	public void updateTest() {
		Content con = new Content(67, "asdx", "zdf", "ss",
				"sqltest", new Date());
		Access.update(con, null);
	}
	
	@Test
	public void deleteTest() {
		Content con = new Content(67, "asdx", "zdf", "ss",
				"sqltest", new Date());
		Access.delete(con, null);
	}
	
	@Test
	public void getTest() {
		System.out.println(Access.get(Content.class, 10));
	}

}
