package mytoday;

import static org.junit.Assert.*;

import org.junit.Test;

public class SettingTest {

	@Test
	public void test() {
		assertEquals(Setting.get("general").get("controllerPath"),"mytoday.controller");
	}

}
