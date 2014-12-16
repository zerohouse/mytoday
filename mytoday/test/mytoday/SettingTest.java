package mytoday;

import static org.junit.Assert.*;

import org.junit.Test;

public class SettingTest {

	@Test
	public void test() {
		System.out.println(Setting.get("db"));
		System.out.println(Setting.get("general").get("controllerPath"));
		assertTrue(true);
	}

}
