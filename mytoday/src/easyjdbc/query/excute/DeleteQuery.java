package easyjdbc.query.excute;

import easyjdbc.annotation.Table;
import easyjdbc.query.DBColumn;

public class DeleteQuery extends ExecuteQuery {

	private String tableName;
	Class<?> type;

	public DeleteQuery(Class<?> type, Object... primaryKey) {
		this.type = type;
		fieldsDeclare(type);
		Table table = type.getAnnotation(Table.class);
		this.tableName = table.value();
		sql = "delete from " + tableName + WHERE + joinedString(keys, "=? and ", 5);
		for (int i = 0; i < primaryKey.length; i++)
			parameters.add(primaryKey[i]);
	}

	public DeleteQuery(Object record) {
		this.type = record.getClass();
		fieldsDeclare(type);
		Table table = type.getAnnotation(Table.class);
		this.tableName = table.value();
		sql = "delete from " + tableName + WHERE + joinedString(keys, "=? and ", 5);
		for (String key : keys.keySet()) {
			DBColumn column = keys.get(key);
			parameters.add(column.getInvokedObject(record));
		}
	}
}
