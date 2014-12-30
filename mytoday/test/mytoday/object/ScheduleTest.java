package mytoday.object;

import static org.junit.Assert.*;

import org.junit.Test;

import easyjdbc.dao.DBMethods;

public class ScheduleTest {

	@Test
	public void test() {
		assertTrue(true);
		System.out.println(DBMethods.getList(Schedule.class, "userId=? and date=?", "zerohouse", "2014-12-30"));
	}
}
