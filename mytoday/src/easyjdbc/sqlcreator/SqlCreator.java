package easyjdbc.sqlcreator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import easyjdbc.annotation.Exclude;
import easyjdbc.annotation.Key;
import easyjdbc.annotation.Table;

public class SqlCreator {
	private Object dbObject;
	private Class<?> type;

	private String tableName;
	private String defaultCondition;

	private ColumnHolder columns = new ColumnHolder();
	private ColumnHolder keys = new ColumnHolder();

	public SqlCreator(Object dbObject) {
		this.dbObject = dbObject;
		type = dbObject.getClass();
		fieldsDeclare(type);
		Table table = type.getAnnotation(Table.class);
		this.tableName = table.value();
		this.defaultCondition = table.defaultCondition();
	}

	public SqlCreator(Class<?> type) {
		this.type = type;
		fieldsDeclare(type);
		Table table = type.getAnnotation(Table.class);
		this.tableName = table.value();
		this.defaultCondition = table.defaultCondition();
	}

	public String getSelectSql() {
		String select = keys.joinedString(",") + columns.joinedString(",", 1);
		return "select " + select + " from " + tableName;
	}

	public String getUpdateSql() {
		String fieldsString = columns.joinedString("=?, ", 2);
		String condition = keys.joinedString("=?, ", 2);
		return "update " + tableName + " set " + fieldsString + " where " + condition;
	}

	public String getInsertSql() {
		String condition = keys.joinedString(",") + columns.joinedString(",",1); 
		String insert = keys.joinedString(",") + columns.joinedString(",", 1);
		return "insert into " + tableName + " (" + insert + ") values (" + condition + ")";
	}

	private void fieldsDeclare(Class<?> cLass) {
		Field[] fields = cLass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(Exclude.class))
				continue;
			if (fields[i].isAnnotationPresent(Key.class)) {
				keys.put(fields[i].getName(), new DBColumn(fields[i]));
				continue;
			}
			columns.put(fields[i].getName(), new DBColumn(fields[i]));
			continue;
		}
	}

}
