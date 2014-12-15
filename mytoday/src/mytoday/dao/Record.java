package mytoday.dao;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import mytoday.annotation.Primarykey;
import mytoday.annotation.TableInfo;

public abstract class Record {

	protected abstract boolean setByList(ArrayList<Object> user);

	private Field primaryField;
	private Field[] fields;
	private final int columnSize;
	private final String tableName;
	private final String dateformat;

	protected Record() {
		TableInfo anotation = getClass().getAnnotation(TableInfo.class);
		tableName = anotation.tableName();
		dateformat = anotation.dateFormat();
		fields = getClass().getDeclaredFields();
		columnSize = fields.length;
		for (int i = 0; i < fields.length; i++)
			if (fields[i].isAnnotationPresent(Primarykey.class))
				primaryField = fields[i];
	}

	public boolean insert() {
		DAO dao = new DAO();
		String fieldsString = "";
		String valueString = "";
		Object param;

		for (int i = 0; i < fields.length; i++) {
			try {
				param = getFieldObject(fields[i].getName());
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

	public boolean update(String whereClause, List<Object> expectedObject) {
		DAO dao = new DAO();
		String fieldsString = "";
		fieldsString = addParams(dao, fieldsString);

		String sql = "update contents set " + fieldsString + " where "
				+ primaryField.getName() + "=?";

		if (whereClause != null) {
			sql += " and " + whereClause;
			Iterator<Object> iterator = expectedObject.iterator();
			while (iterator.hasNext()) {
				dao.addParameter(iterator.next());
			}
		}
		addPrimaryKey(dao);
		dao.setSql(sql);

		return dao.doQuery();
	}

	public boolean update(String whereClause) {
		DAO dao = new DAO();
		String fieldsString = "";
		fieldsString = addParams(dao, fieldsString);

		String sql = "update contents set " + fieldsString + " where "
				+ primaryField.getName() + "=?";

		if (whereClause != null) {
			sql += " and " + whereClause;
		}

		addPrimaryKey(dao);
		dao.setSql(sql);
		return dao.doQuery();
	}

	public boolean update() {
		DAO dao = new DAO();
		String fieldsString = "";
		fieldsString = addParams(dao, fieldsString);

		String sql = "update contents set " + fieldsString + " where "
				+ primaryField.getName() + "=?";

		addPrimaryKey(dao);
		dao.setSql(sql);
		return dao.doQuery();
	}

	public boolean delete(String whereClause) {
		DAO dao = new DAO();
		String sql = "delete from " + tableName + " where "
				+ primaryField.getName() + "=?";
		if (whereClause != null) {
			sql += " and " + whereClause;
		}
		dao.setSql(sql);
		addPrimaryKey(dao);
		return dao.doQuery();
	}

	public boolean delete() {
		DAO dao = new DAO();
		String sql = "delete from " + tableName + " where "
				+ primaryField.getName() + "=?";
		dao.setSql(sql);
		addPrimaryKey(dao);
		return dao.doQuery();
	}

	private void addPrimaryKey(DAO dao) {
		try {
			dao.addParameter(getFieldObject(primaryField.getName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String addParams(DAO dao, String fieldsString) {
		Object param;
		for (int i = 0; i < fields.length; i++) {
			try {
				param = getFieldObject(fields[i].getName());
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

	public boolean set() {
		DAO dao = new DAO();
		dao.setSql("select * from " + tableName + " where "
				+ primaryField.getName() + "=?");
		addPrimaryKey(dao);
		dao.setResultSize(columnSize);
		ArrayList<Object> user = dao.getRecord();
		if (user.size() == 0)
			return false;
		return setByList(user);
	}

	private Object getFieldObject(String fieldName) throws Exception {
		return getClass().getMethod(getterString(fieldName), (Class<?>[]) null)
				.invoke(this);
	}

	private String getterString(String fieldName) {
		return "get" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
	}

	protected Date parseDate(Object object) {
		SimpleDateFormat datetime = new SimpleDateFormat(dateformat);
		Date date = null;
		try {
			date = datetime.parse(object.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
