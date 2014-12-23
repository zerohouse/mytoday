package mytoday;

import static org.junit.Assert.*;
import mytoday.mapping.Setting;

import org.junit.Test;

public class SettingTest {

	@Test
	public void test() {
		assertEquals(Setting.get(Setting.GENERAL, Setting.CONTROLLER),"mytoday.controller");
		System.out.println(Setting.get(Setting.GENERAL, Setting.CONTROLLER));
	}

}
