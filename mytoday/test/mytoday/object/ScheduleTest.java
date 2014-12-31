package mytoday.object;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import easyjdbc.dao.DBMethods;

public class ScheduleTest {

	@Test
	public void test() {
		assertTrue(true);
		System.out.println(DBMethods.getList(Schedule.class, "userId=? and date=?", "zerohouse", "2014-12-30"));
		Gson gson = new GsonBuilder()
		   .setDateFormat("yyyy-MM-dd").create();
		System.out.println(gson.toJson(DBMethods.getList(Schedule.class, "userId=? and `date` BETWEEN ? AND ?", "zerohouse", "2014-12-30", "2015-01-01")));
	
	}
}
