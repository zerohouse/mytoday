package mytoday.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import mytoday.object.Content;

import org.junit.Test;

public class DBMethodsTest {

	@Test
	public void insertTest() {
		Content con = new Content(null, "dfdfdf", "zdf", "ss", "sqltest",
				new Date());
		DBMethods.insert(con);
	}

	@Test
	public void updateTest() {
		Content con = new Content(66, "asdx", "zdf", "ss", "sqltest",
				new Date());
		DBMethods.update(con);
	}

	@Test
	public void getTest() {
		assertNotNull(DBMethods.get(Content.class, 69));
	}

	@Test
	public void deleteTest() {
		Content con = new Content(66, "asdx", "zdf", "ss", "sqltest",
				new Date());
		DBMethods.delete(con);
	}

	@Test
	public void getListTest() {
		List<? extends Object> list = DBMethods.getList(Content.class);
		assertEquals(list.size(), 10);
	}

	@Test
	public void test() {
		assertNotNull((Content) DBMethods.get(Content.class, 69));
	}

}
