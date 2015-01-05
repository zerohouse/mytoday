package easyjdbc.query.excute;

import mytoday.object.Type;
import mytoday.object.User;

import org.junit.Before;
import org.junit.Test;

import easyjdbc.query.execute.DeleteQuery;
import easyjdbc.query.execute.InsertQuery;
import easyjdbc.query.execute.UpdateQuery;

public class ExecuteableQueryTest {
	User user = new User();
	Type type = new Type();

	@Before
	public void setup() {
		user.setEmail("123");
		user.setId("zerohouse");
		user.setPassword("password");
		type.setColor("black");
		type.setId(3);
		type.setUserId("aer");
		type.setName("as");
	}

	@Test
	public void insertTest() {
		InsertQuery iq = new InsertQuery(type);
		System.out.println(iq.getSql());
		System.out.println(iq.getParameters());
	}

	@Test
	public void updateTest() {
		UpdateQuery iq = new UpdateQuery(type);
		System.out.println(iq.getSql());
		System.out.println(iq.getParameters());
	}

	@Test
	public void deleteTest() {
		DeleteQuery iq = new DeleteQuery(Type.class, 1, "zerohouse");
		System.out.println(iq.getSql());
		System.out.println(iq.getParameters());
	}

}
