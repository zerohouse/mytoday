package mytoday.object;

import static org.junit.Assert.*;

import mytoday.dao.DBMethods;

import org.junit.Test;

public class UserTest {

	@Test
	public void insertTest() {
		User user = new User();
		user.set("zz", "ss");
		DBMethods.insert(user);
	}

	@Test
	public void updateTest() {
		User user = new User();
		DBMethods.update(user);
	}
	
	@Test
	public void setTest() {
		User user = new User();
		user.set("zz", "ss");
		assertEquals(user.getId(), "zz");
	}

	@Test
	public void getTest() {
		System.out.println(DBMethods.get(User.class, "zerohouse"));
		assertNotNull(DBMethods.get(User.class, "zerohouse"));
	}

	@Test
	public void getListTest() {
		System.out.println(DBMethods.getList(User.class));
	}

}
