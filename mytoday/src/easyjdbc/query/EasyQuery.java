package easyjdbc.query;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import easyjdbc.annotation.Exclude;
import easyjdbc.annotation.Key;
import easyjdbc.query.support.DBColumn;

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

	protected String getNotNullFieldString(Map<String, DBColumn> columns, String delimiter, int subLength) {
		String result = new String();
		for (String key : columns.keySet()) {
			DBColumn column = columns.get(key);
			if (column.hasObject())
				result += column.getColumnName() + delimiter;
		}
		result = result.substring(0, result.length() - subLength);
		return result;
	}


	protected String joinedString(Map<String, DBColumn> columns, String delimiter) {
		String result = new String();
		for (String key : columns.keySet())
			result += columns.get(key).getColumnName() + delimiter;
		return result;
	}

	protected void setByInstance(Object instance, int phase) {
		Field[] fields = instance.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(Exclude.class))
				continue;
			DBColumn dbCol = new DBColumn(fields[i], phase);
			dbCol.setByInstance(instance);
			if (fields[i].isAnnotationPresent(Key.class)) {
				keys.put(fields[i].getName(), dbCol);
				continue;
			}
			columns.put(fields[i].getName(), dbCol);
		}
	}

	protected void setByType(Class<?> cLass, int phase) {
		Field[] fields = cLass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(Exclude.class))
				continue;
			if (fields[i].isAnnotationPresent(Key.class)) {
				keys.put(fields[i].getName(), new DBColumn(fields[i], phase));
				continue;
			}
			columns.put(fields[i].getName(), new DBColumn(fields[i], phase));
		}
	}

	protected void setByTypeAndPrimaryKey(Class<?> cLass, int phase, Object... primaryKey) {
		Field[] fields = cLass.getDeclaredFields();
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(Exclude.class))
				continue;
			if (fields[i].isAnnotationPresent(Key.class)) {
				keys.put(fields[i].getName(), new DBColumn(fields[i], phase, primaryKey[j]));
				j++;
				continue;
			}
			columns.put(fields[i].getName(), new DBColumn(fields[i], phase));
		}
	}

}
