package mytoday.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import mytoday.annotation.Primarykey;
import mytoday.annotation.TableInfo;

public class Access {

	private Access() {
	}

	public static boolean insert(Object record) {
		TableInfo anotation = record.getClass().getAnnotation(TableInfo.class);
		String tableName = anotation.tableName();
		Field[] fields = record.getClass().getDeclaredFields();

		DAO dao = new DAO();
		String fieldsString = "";
		String valueString = "";
		Object param;

		for (int i = 0; i < fields.length; i++) {
			try {
				param = getFieldObject(fields[i].getName(), record);
				if (param != null) {
					fieldsString += fields[i].getName() + ",";
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

	public static Object get(Class<?> cLass, Object primaryKey) {
		TableInfo anotation = cLass.getAnnotation(TableInfo.class);
		Field primaryField = getPrimaryField(cLass);
		DAO dao = new DAO();
		dao.setSql("select * from " + anotation.tableName() + " where "
				+ primaryField.getName() + "=?");
		System.out.println("select * from " + anotation.tableName() + " where "
				+ primaryField.getName() + "=?");
		dao.addParameter(primaryKey);

		dao.setResultSize(cLass.getDeclaredFields().length);
		System.out.println(cLass.getDeclaredFields().length);
		ArrayList<Object> user = dao.getRecord();
		System.out.println(user);
		try {
			return cLass.getConstructor(List.class).newInstance(user);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean update(Object record, String whereClause) {
		DAO dao = new DAO();
		String tableName = record.getClass().getAnnotation(TableInfo.class)
				.tableName();
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
		String tableName = record.getClass().getAnnotation(TableInfo.class)
				.tableName();
		Field primaryField = getPrimaryField(record.getClass());
		Object primaryKey = getFieldObject(primaryField.getName(), record);
		String fieldsString = addParams(record, dao);

		String sql = "update " + tableName + " set " + fieldsString
				+ " where " + primaryField.getName() + "=?";
		dao.addParameter(primaryKey);

		dao.setSql(sql);
		return dao.doQuery();
	}

	public static boolean delete(Object record, String whereClause) {
		DAO dao = new DAO();
		Class<?> cLass = record.getClass();
		String tableName = cLass.getAnnotation(TableInfo.class).tableName();
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
		String tableName = cLass.getAnnotation(TableInfo.class).tableName();
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
		Field fields[] = record.getClass().getDeclaredFields();
		Object param;
		for (int i = 0; i < fields.length; i++) {
			try {
				param = getFieldObject(fields[i].getName(), record);
				if (param != null) {
					fieldsString += fields[i].getName() + "=?, ";
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
		Field[] fields = cLass.getDeclaredFields();
		int columnSize = fields.length;
		for (int i = 0; i < columnSize; i++)
			if (fields[i].isAnnotationPresent(Primarykey.class))
				return fields[i];
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

}
