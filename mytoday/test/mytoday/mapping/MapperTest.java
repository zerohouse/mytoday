package mytoday.mapping;

import static org.junit.Assert.*;

import org.junit.Test;

public class MapperTest {

	@Test
	public void test() {
		// assertEquals(Mapper.get(Mapper.GET, new Match("/home.my")).getName(),
		// "hossme");
		assertEquals(Mapper.get(Mapper.GET, "/home.my").getMethod().getName(), "home");
	}

}
