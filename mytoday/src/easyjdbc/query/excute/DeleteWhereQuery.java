package easyjdbc.query.excute;

import easyjdbc.annotation.Table;

public class DeleteWhereQuery extends ExecuteQuery {

	private String tableName;
	Class<?> type;

	public DeleteWhereQuery(Class<?> type, String whereClause, Object... keys) {
		this.type = type;
		fieldsDeclare(type);
		Table table = type.getAnnotation(Table.class);
		this.tableName = table.value();
		sql = "delete from " + tableName + WHERE + whereClause;
		for (int i = 0; i < keys.length; i++)
			parameters.add(keys[i]);
	}
}
