package easyjdbc.sqlcreator;

import java.lang.reflect.Field;

import easyjdbc.annotation.Column;

public class DBColumn {

	private String name;
	private Field field;

	public DBColumn(Field field) {
		this.field = field;
		if (field.isAnnotationPresent(Column.class)) {
			this.name = field.getAnnotation(Column.class).value();
			return;
		}
		this.name = field.getName();
	}

	public String getName() {
		return name;
	}

}
