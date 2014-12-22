package mytoday.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import dao.DBMethods;
import dao.Record;

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
		User user = (User) DBMethods.get(User.class, "zerohouse");
		assertNotNull(user);
		List<Record> list = DBMethods.getList(User.class, "zerohouse");
		System.out.println(list);
		System.out.println(user);
	}

	@Test
	public void getListTest() {
		System.out.println(DBMethods.getList(User.class));
	}

}
