package easyjdbc.query.excute;

import easyjdbc.annotation.Table;

public class UpdateQuery extends ExecuteQuery {

	private String tableName;
	private Object instance;


	public UpdateQuery(Object instance) {
		this.instance = instance;
		Class<?> type = instance.getClass();
		fieldsDeclare(type);
		Table table = type.getAnnotation(Table.class);
		this.tableName = table.value();
		sql = "update " + tableName + " set " + joinedString(columns, "=?, ", 2) + WHERE + joinedString(keys, "=? and ", 5);
		for (String key : columns.keySet()) {
			parameters.add(columns.get(key).getInvokedObject(this.instance));
		}
		for (String key : keys.keySet()) {
			parameters.add(keys.get(key).getInvokedObject(this.instance));
		}
	}


}
