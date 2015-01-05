package easyjdbc.query.excute;

import java.sql.Connection;
import java.util.Date;

import mytoday.object.Type;
import mytoday.object.User;

import org.junit.Before;
import org.junit.Test;

import easyjdbc.query.QueryExecuter;
import easyjdbc.query.execute.DeleteQuery;
import easyjdbc.query.execute.DeleteWhereQuery;
import easyjdbc.query.execute.InsertQuery;
import easyjdbc.query.execute.UpdateQuery;
import easyjdbc.query.select.ListQuery;
import easyjdbc.query.select.SelectQuery;
import easyjdbc.query.select.SelectWhereQuery;

public class ExecuteableQueryTest {
	Connection con = QueryExecuter.getConnection();
	User user = new User();

	@Before
	public void setup() {
		user.setId("zerohoduse");
		user.setPassword("pasword");
		user.setEmail("mail");
		user.setTimestamp(new Date());
	}


	@Test
	public void selectTest() {
		Connection con = QueryExecuter.getConnection();
		SelectQuery<User> query = new SelectQuery<User>(User.class, "mytoday");
		System.out.println(query.execute(con));
	}
	
	@Test
	public void ListTest() {
		Connection con = QueryExecuter.getConnection();
		ListQuery<User> query = new ListQuery<User>(User.class);
		query.setPage(3,1);
		System.out.println(query.execute(con));
	}
	
	@Test
	public void updateTest() {
		Connection con = QueryExecuter.getConnection();
		UpdateQuery query = new UpdateQuery(user);
		System.out.println(query.execute(con));
	}
	
	@Test
	public void deleteTest() {
		Connection con = QueryExecuter.getConnection();
		DeleteQuery query = new DeleteQuery(user);
		System.out.println(query.execute(con));
	}
	
	@Test
	public void deletePrimaryTest() {
		Connection con = QueryExecuter.getConnection();
		DeleteWhereQuery query = new DeleteWhereQuery(Type.class, "userId=?", "mytoday");
		System.out.println(query.execute(con));
	}
	
	@Test
	public void selectwhereTest() {
		Connection con = QueryExecuter.getConnection();
		SelectWhereQuery<Type> query = new SelectWhereQuery<Type>(Type.class, "userId=?", "mytoday");
		System.out.println(query.execute(con));
	}
	
	@Test
	public void insertTest() {
		Connection con = QueryExecuter.getConnection();
		InsertQuery query = new InsertQuery(user);
		System.out.println(query.execute(con));
	}

}
