package easyjdbc.query.support;

import java.lang.reflect.Field;

import easyjdbc.annotation.Column;

public class DBColumn {

	public static final int PHASE_INSERT = 0;
	public static final int PHASE_DELETE = 1;
	public static final int PHASE_UPDATE = 2;
	public static final int PHASE_SELECT = 3;

	private String columnName;
	private Field field;
	private Object object;
	private int phase;
	private String[] format;

	public DBColumn(Field field, int phase, Object object) {
		this(field, phase);
		this.object = object;
	}

	public DBColumn(Field field, int phase) {
		this.phase = phase;
		this.field = field;
		if (field.isAnnotationPresent(Column.class)) {
			Column column = field.getAnnotation(Column.class);
			this.columnName = column.value();
			format = new String[4];
			format[PHASE_INSERT] = column.insertFormat();
			format[PHASE_DELETE] = column.deleteFormart();
			format[PHASE_UPDATE] = column.updateFormart();
			format[PHASE_SELECT] = column.selectFormart();
			return;
		}
		this.columnName = field.getName();
	}

	public Object getObject() {
		return object;
	}

	public String getColumnName() {
		return columnName;
	}

	public Object setObjectField(Object instance, Object parameter) {
		try {
			return instance.getClass().getMethod(setterString(field.getName()), field.getType()).invoke(instance, parameter);
		} catch (Exception e) {
			return null;
		}
	}

	public Object getInvokedObject(Object instance) {
		try {
			return instance.getClass().getMethod(getterString(field.getName()), (Class<?>[]) null).invoke(instance);
		} catch (Exception e) {
			return null;
		}
	}

	public String getterString(String fieldName) {
		return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	public String setterString(String fieldName) {
		return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	public void setByInstance(Object instance) {
		this.object = getInvokedObject(instance);
	}

	public boolean hasObject() {
		if (object == null)
			return false;
		return true;
	}

}
