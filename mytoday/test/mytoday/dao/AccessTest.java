package mytoday.dao;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import mytoday.object.Content;

import org.junit.Test;

public class AccessTest {

	@Test
	public void insertTest() {
		Content con = new Content(66, "dfdfdf", "zdf", "ss",
				"sqltest", new Date());
		assertTrue(Access.insert(con));
	}
	
	@Test
	public void updateTest() {
		Content con = new Content(66, "asdx", "zdf", "ss",
				"sqltest", new Date());
		Access.update(con, null);
	}
	
	@Test
	public void deleteTest() {
		Content con = new Content(66, "asdx", "zdf", "ss",
				"sqltest", new Date());
		Access.delete(con, null);
	}
	
	@Test
	public void getTest() {
		System.out.println((Content) Access.get(Content.class, 66));
	}
	
	@Test
	public void getListTest() {
		List<? extends Object> list = Access.getList(Content.class);
		System.out.println(list);
	}


}
