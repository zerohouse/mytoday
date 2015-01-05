package easyjdbc.query;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import easyjdbc.annotation.Exclude;
import easyjdbc.annotation.Key;

public abstract class EasyQuery extends Query {

	protected final static String WHERE = " where ";

	protected Map<String, DBColumn> columns = new LinkedHashMap<String, DBColumn>();
	protected Map<String, DBColumn> keys = new LinkedHashMap<String, DBColumn>();

	public EasyQuery() {

	}

	protected String joinedString(Map<String, DBColumn> columns, String delimiter, int subLength) {
		String result = new String();
		for (String key : columns.keySet()) {
			result += columns.get(key).getColumnName() + delimiter;
		}
		result = result.substring(0, result.length() - subLength);
		return result;
	}

	protected String getQuestionComma(int commaLength) {
		String result = new String();
		for (int i = 0; i < commaLength; i++) {
			result += "?, ";
		}
		result = result.substring(0, result.length() - 2);
		return result;
	}

	protected String joinedString(Map<String, DBColumn> columns, String delimiter) {
		String result = new String();
		for (String key : columns.keySet())
			result += columns.get(key).getColumnName() + delimiter;
		return result;
	}
	
	protected void fieldsDeclare(Class<?> cLass) {
		Field[] fields = cLass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(Exclude.class))
				continue;
			if (fields[i].isAnnotationPresent(Key.class)) {
				keys.put(fields[i].getName(), new DBColumn(fields[i]));
				continue;
			}
			columns.put(fields[i].getName(), new DBColumn(fields[i]));
		}
	}

	protected void fieldsDeclare(Class<?> cLass, Object... primaryKey) {
		Field[] fields = cLass.getDeclaredFields();
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(Exclude.class))
				continue;
			if (fields[i].isAnnotationPresent(Key.class)) {
				keys.put(fields[i].getName(), new DBColumn(fields[i], primaryKey[j]));
				j++;
				continue;
			}
			columns.put(fields[i].getName(), new DBColumn(fields[i]));
		}
	}

}
