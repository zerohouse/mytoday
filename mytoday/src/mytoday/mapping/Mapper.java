package mytoday.mapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Mapper {
	private Map<String, Method> map = new HashMap<String, Method>();
	
	public Method findMethod(String key){
		return map.get(key);
	}

	public void put(String value, Method method) {
		map.put(value, method);
	}
}
