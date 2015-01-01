package mytoday.object;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import easyjdbc.dao.DBMethods;

public class ScheduleTest {

	@Test
	public void test() {
		
		DBMethods.getList(Schedule.class, "userId=? and date=?", "zerohouse", "2014-12-30");
		DBMethods.getList(Schedule.class, "userId=? and `date` BETWEEN ? AND ?", "zerohouse", "2014-12-30", "2015-01-01");
		assertTrue(true);
	}
}
