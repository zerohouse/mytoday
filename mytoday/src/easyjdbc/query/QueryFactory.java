package easyjdbc.query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import easyjdbc.annotation.Exclude;
import easyjdbc.annotation.Key;
import easyjdbc.annotation.Table;
import easyjdbc.query.support.Methods;
import easyjdbc.query.support.PrimaryFields;

public class QueryFactory {

	private QueryFactory() {
	}

	public static ExecuteQuery getInsertQuery(Object record) {
		Table anotation = record.getClass().getAnnotation(Table.class);
		String tableName = anotation.value();
		List<Field> fields = excludeNotThisDB(record.getClass());
		List<Object> parameters = new ArrayList<Object>();
		String valueString = "";
		String fieldsString = "";
		Object param;
		for (int i = 0; i < fields.size(); i++) {
			try {
				param = Methods.getFieldObject(fields.get(i).getName(), record);
				if (param != null) {
					fieldsString += fields.get(i).getName() + ",";
					valueString += "?,";
					parameters.add(param);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		fieldsString = fieldsString.substring(0, fieldsString.length() - 1);
		valueString = valueString.substring(0, valueString.length() - 1);
		String sql = "insert into " + tableName + " (" + fieldsString + ") values(" + valueString + ")";
		return new ExecuteQuery(sql, parameters);
	}

	public static ExecuteQuery insertIfNotExistIgnore(Object record) {
		Table anotation = record.getClass().getAnnotation(Table.class);
		String tableName = anotation.value();
		List<Field> fields = excludeNotThisDB(record.getClass());
		List<Object> parameters = new ArrayList<Object>();
		String valueString = "";
		String fieldsString = "";
		Object param;
		for (int i = 0; i < fields.size(); i++) {
			try {
				param = Methods.getFieldObject(fields.get(i).getName(), record);
				if (param != null) {
					fieldsString += fields.get(i).getName() + ",";
					valueString += "?,";
					parameters.add(param);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		fieldsString = fieldsString.substring(0, fieldsString.length() - 1);
		valueString = valueString.substring(0, valueString.length() - 1);
		String sql = "insert ignore into " + tableName + " (" + fieldsString + ") values(" + valueString + ")";
		return new ExecuteQuery(sql, parameters);
	}

	public static ExecuteQuery insertIfExistUpdate(Object record) {
		Table anotation = record.getClass().getAnnotation(Table.class);
		String tableName = anotation.value();
		List<Field> fields = excludeNotThisDB(record.getClass());
		List<Object> parameters = new ArrayList<Object>();
		String valueString = "";
		String fieldsString = "";
		Object param;
		for (int i = 0; i < fields.size(); i++) {
			try {
				param = Methods.getFieldObject(fields.get(i).getName(), record);
				if (param != null) {
					fieldsString += fields.get(i).getName() + ",";
					valueString += "?,";
					parameters.add(param);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		fieldsString = fieldsString.substring(0, fieldsString.length() - 1);
		valueString = valueString.substring(0, valueString.length() - 1);
		String sql = "insert into " + tableName + " (" + fieldsString + ") values(" + valueString + ")";
		String updateString = addParams(record, fields, parameters);
		sql += " on duplicate key update " + updateString;
		return new ExecuteQuery(sql, parameters);
	}

	public static ExecuteQuery getUpdateQuery(Object record) {
		String tableName = record.getClass().getAnnotation(Table.class).value();
		List<Field> excludedFields = excludeNotThisDB(record.getClass());
		PrimaryFields primaryField = new PrimaryFields(excludedFields);
		Object[] primaryKey;
		List<Object> parameters = new ArrayList<Object>();
		primaryKey = primaryField.getParams(record);
		String fieldsString = addParams(record, excludedFields, parameters);
		String sql = "update " + tableName + " set " + fieldsString + " where " + primaryField.getCondition();
		addToArrayList(primaryKey, parameters);
		return new ExecuteQuery(sql, parameters);
	}

	public static ExecuteQuery getDeleteQuery(Object record) {
		String tableName = record.getClass().getAnnotation(Table.class).value();
		PrimaryFields primaryField = new PrimaryFields(record.getClass());
		Object[] primaryKey;
		List<Object> parameters = new ArrayList<Object>();
		primaryKey = primaryField.getParams(record);
		String sql = "delete from " + tableName + " where " + primaryField.getCondition();
		addToArrayList(primaryKey, parameters);
		return new ExecuteQuery(sql, parameters);
	}

	public static GetRecordQuery getRecordQuery(Class<?> cLass, Object... primaryKey) {
		Table anotation = cLass.getAnnotation(Table.class);
		List<Field> excludedFields = excludeNotThisDB(cLass);
		PrimaryFields primaryField = new PrimaryFields(excludedFields);
		String sql = "select * from " + anotation.value() + " where " + primaryField.getCondition();
		List<Object> parameters = new ArrayList<Object>();
		for (int i = 0; i < primaryKey.length; i++) {
			parameters.add(primaryKey[i]);
		}
		return new GetRecordQuery(excludedFields.size(), sql, parameters);
	}
	
	public static GetRecordQuery getRecordQuery(Class<?> cLass) {
		Table anotation = cLass.getAnnotation(Table.class);
		List<Field> excludedFields = excludeNotThisDB(cLass);
		String sql = "select * from " + anotation.value();
		List<Object> parameters = new ArrayList<Object>();
		return new GetRecordQuery(excludedFields.size(), sql, parameters);
	}

	

	public static GetRecordsQuery getRecordsQuery(Class<?> cLass, String condition, Object... objects) {
		Table anotation = cLass.getAnnotation(Table.class);
		List<Field> excludedFields = excludeNotThisDB(cLass);
		String sql;
		if (condition != null) {
			sql = "select * from " + anotation.value() + " where " + condition;
		} else {
			sql = "select * from " + anotation.value();
		}
		List<Object> parameters = new ArrayList<Object>();
		addToArrayList(objects, parameters);
		return new GetRecordsQuery(excludedFields.size(), sql, parameters);
	}

	private static void addToArrayList(Object[] array, List<Object> params) {
		for (int i = 0; i < array.length; i++) {
			params.add(array[i]);
		}
	}

	private static String addParams(Object record, List<Field> fields, List<Object> paramSaveThisList) {
		String fieldsString = "";
		Object param;
		for (int i = 0; i < fields.size(); i++) {
			if (fields.get(i).isAnnotationPresent(Key.class))
				continue;
			try {
				param = Methods.getFieldObject(fields.get(i).getName(), record);
				if (param != null) {
					fieldsString += fields.get(i).getName() + "=?, ";
					paramSaveThisList.add(param);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		fieldsString = fieldsString.substring(0, fieldsString.length() - 2);
		return fieldsString;
	}

	

	static List<Field> excludeNotThisDB(Class<?> cLass) {
		List<Field> result = new ArrayList<Field>();
		Field[] fields = cLass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (!fields[i].isAnnotationPresent(Exclude.class))
				result.add(fields[i]);
		}
		return result;
	}


}
