package mytoday;

import static org.junit.Assert.*;
import mytoday.setting.Setting;

import org.junit.Test;

public class SettingTest {

	@Test
	public void test() {
		assertEquals(Setting.get(Setting.CONTROLLER),"mytoday.controller");
		System.out.println(Setting.get(Setting.CONTROLLER));
		System.out.println(Setting.get(Setting.JSP));
		System.out.println(Setting.get(Setting.URL));
	}

}
