package mytoday.dao;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import mytoday.object.Content;

import org.junit.Test;

public class AccessTest {

	@Test
	public void insertTest() {
		Content con = new Content(null, "dfdfdf", "zdf", "ss",
				"sqltest", new Date());
		assertTrue(con.insert());
	}
	
	@Test
	public void updateTest() {
		Content con = new Content(66, "asdx", "zdf", "ss",
				"sqltest", new Date());
		DBMethods.update(con);
	}
	
	@Test
	public void deleteTest() {
		Content con = new Content(66, "asdx", "zdf", "ss",
				"sqltest", new Date());
		DBMethods.delete(con);
	}
	
	@Test
	public void getTest() {
		System.out.println((Content) DBMethods.get(Content.class, 66));
	}
	
	@Test
	public void getListTest() {
		List<? extends Object> list = DBMethods.getList(Content.class);
		System.out.println(list);
	}


}
