package mytoday;

import static org.junit.Assert.*;
import mytoday.util.Setting;

import org.junit.Test;

public class SettingTest {

	@Test
	public void test() {
		assertEquals(Setting.get("general", "controllerPath"),"mytoday.controller");
		assertEquals(Setting.get("general", "urlPattern"),"*.my");
	}

}
