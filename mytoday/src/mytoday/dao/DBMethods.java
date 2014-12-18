package mytoday.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mytoday.annotation.DefaultCondition;
import mytoday.annotation.DBExclude;
import mytoday.annotation.PrimaryKey;
import mytoday.annotation.TableName;

public class DBMethods {

	private DBMethods() {
	}

	public static boolean insert(Object record) {
		TableName anotation = record.getClass().getAnnotation(TableName.class);
		String tableName = anotation.value();
		List<Field> fields = excludeNotThisDB(record.getClass().getDeclaredFields());

		DAO dao = new DAO();
		String fieldsString = "";
		String valueString = "";
		Object param;

		for (int i = 0; i < fields.size(); i++) {
			try {
				param = getFieldObject(fields.get(i).getName(), record);
				if (param != null) {
					fieldsString += fields.get(i).getName() + ",";
					valueString += "?,";
					dao.addParameter(param);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		fieldsString = fieldsString.substring(0, fieldsString.length() - 1);
		valueString = valueString.substring(0, valueString.length() - 1);
		dao.setSql("insert into " + tableName + " (" + fieldsString
				+ ") values(" + valueString + ")");

		return dao.doQuery();
	}



	public static List<? extends Object> getList(Class<?> cLass) {
		List<Object> result = new ArrayList<Object>();
		TableName tableName = cLass.getAnnotation(TableName.class);
		DefaultCondition condition = cLass
				.getAnnotation(DefaultCondition.class);
		DAO dao = new DAO();
		String sql = "select * from " + tableName.value();
		if (condition != null)
			sql += " " + condition.value();
		dao.setSql(sql);
		dao.setResultSize(excludeNotThisDB(cLass.getDeclaredFields()).size());
		ArrayList<ArrayList<Object>> sqlArray = dao.getRecords();
		Iterator<ArrayList<Object>> iterator = sqlArray.iterator();
		Object eachInstance;
		while (iterator.hasNext()) {
			try {
				eachInstance = cLass.getConstructor(List.class).newInstance(
						iterator.next());
				result.add(eachInstance);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static List<? extends Object> getList(Class<?> cLass,
			String condition) {
		List<Object> result = new ArrayList<Object>();
		TableName tableName = cLass.getAnnotation(TableName.class);
		DAO dao = new DAO();
		String sql = "select * from " + tableName.value();
		if (condition != null)
			sql += " " + condition;
		dao.setSql(sql);
		dao.setResultSize(excludeNotThisDB(cLass.getDeclaredFields()).size());
		ArrayList<ArrayList<Object>> sqlArray = dao.getRecords();
		Iterator<ArrayList<Object>> iterator = sqlArray.iterator();
		Object eachInstance;
		while (iterator.hasNext()) {
			try {
				eachInstance = cLass.getConstructor(List.class).newInstance(
						iterator.next());
				result.add(eachInstance);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static Object get(Class<?> cLass, Object primaryKey) {
		TableName anotation = cLass.getAnnotation(TableName.class);
		Field primaryField = getPrimaryField(cLass);
		DAO dao = new DAO();
		dao.setSql("select * from " + anotation.value() + " where "
				+ primaryField.getName() + "=?");
		dao.addParameter(primaryKey);
		dao.setResultSize(excludeNotThisDB(cLass.getDeclaredFields()).size());
		ArrayList<Object> record = dao.getRecord();
		try {
			return cLass.getConstructor(List.class).newInstance(record);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean update(Object record, String whereClause) {
		DAO dao = new DAO();
		String tableName = record.getClass().getAnnotation(TableName.class)
				.value();
		Field primaryField = getPrimaryField(record.getClass());
		Object primaryKey = getFieldObject(primaryField.getName(), record);
		String fieldsString = addParams(record, dao);

		String sql = "update " + tableName + " set " + fieldsString + " where "
				+ primaryField.getName() + "=?";
		dao.addParameter(primaryKey);

		if (whereClause != null) {
			sql += " and " + whereClause;
		}

		dao.setSql(sql);
		return dao.doQuery();
	}

	public static boolean update(Object record) {
		DAO dao = new DAO();
		String tableName = record.getClass().getAnnotation(TableName.class)
				.value();
		Field primaryField = getPrimaryField(record.getClass());
		Object primaryKey = getFieldObject(primaryField.getName(), record);
		String fieldsString = addParams(record, dao);

		String sql = "update " + tableName + " set " + fieldsString + " where "
				+ primaryField.getName() + "=?";
		dao.addParameter(primaryKey);

		dao.setSql(sql);
		return dao.doQuery();
	}

	public static boolean delete(Object record, String whereClause) {
		DAO dao = new DAO();
		Class<?> cLass = record.getClass();
		String tableName = cLass.getAnnotation(TableName.class).value();
		Field primaryField = getPrimaryField(cLass);
		Object primaryKey = getFieldObject(primaryField.getName(), record);

		String sql = "delete from " + tableName + " where "
				+ primaryField.getName() + "=?";

		if (whereClause != null) {
			sql += " and " + whereClause;
		}

		dao.setSql(sql);
		dao.addParameter(primaryKey);
		return dao.doQuery();
	}

	public static boolean delete(Object record) {
		DAO dao = new DAO();
		Class<?> cLass = record.getClass();
		String tableName = cLass.getAnnotation(TableName.class).value();
		Field primaryField = getPrimaryField(cLass);
		Object primaryKey = getFieldObject(primaryField.getName(), record);

		String sql = "delete from " + tableName + " where "
				+ primaryField.getName() + "=?";

		dao.setSql(sql);
		dao.addParameter(primaryKey);
		return dao.doQuery();
	}

	private static String addParams(Object record, DAO dao) {
		String fieldsString = "";
		List<Field> fields = excludeNotThisDB(record.getClass().getDeclaredFields());
		Object param;
		for (int i = 0; i < fields.size(); i++) {
			try {
				param = getFieldObject(fields.get(i).getName(), record);
				if (param != null) {
					fieldsString += fields.get(i).getName() + "=?, ";
					dao.addParameter(param);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		fieldsString = fieldsString.substring(0, fieldsString.length() - 2);
		return fieldsString;
	}

	private static Field getPrimaryField(Class<?> cLass) {
		List<Field> fields = excludeNotThisDB(cLass.getDeclaredFields());
		int columnSize = fields.size();
		for (int i = 0; i < columnSize; i++)
			if (fields.get(i).isAnnotationPresent(PrimaryKey.class))
				return fields.get(i);
		return null;
	}

	private static Object getFieldObject(String fieldName, Object record) {
		try {
			return record.getClass()
					.getMethod(getterString(fieldName), (Class<?>[]) null)
					.invoke(record);
		} catch (Exception e) {
			return null;
		}
	}

	private static String getterString(String fieldName) {
		return "get" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
	}
	
	private static List<Field> excludeNotThisDB(Field[] fields) {
		List<Field> result = new ArrayList<Field>();
		for (int i = 0; i < fields.length; i++) {
			if(!fields[i].isAnnotationPresent(DBExclude.class))
				result.add(fields[i]);
		}
		return result;
	}

}
