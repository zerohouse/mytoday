package easyjdbc.sqlcreator;

import java.util.LinkedHashMap;
import java.util.Map;

public class ColumnHolder {

	Map<String, DBColumn> columns = new LinkedHashMap<String, DBColumn>();

	public void put(String name, DBColumn dbColumn) {
		columns.put(name, dbColumn);
	}

	public String joinedString(String delimiter, int subLength) {
		String result = new String();
		for (String key : columns.keySet()) {
			result += columns.get(key).getName() + delimiter;
		}
		result = result.substring(0, result.length() - subLength);
		return result;
	}

	public String joinedString(String delimiter) {
		String result = new String();
		for (String key : columns.keySet())
			result += columns.get(key).getName() + delimiter;
		return result;
	}
}
