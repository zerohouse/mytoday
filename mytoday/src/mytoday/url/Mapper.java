package mytoday.url;

import java.util.HashMap;
import java.util.Map;

public class Mapper {
	private Map<String, MethodHolder> map = new HashMap<String, MethodHolder>();
	
	public MethodHolder get(String key){
		return map.get(key);
	}

	public void put(String value, MethodHolder methodHolder) {
		map.put(value, methodHolder);
	}
}
