package easyjdbc.query.select;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import easyjdbc.annotation.Table;
import easyjdbc.query.EasyQuery;
import easyjdbc.query.support.DBColumn;

public class SelectQuery<T> extends EasyQuery {

	String tableName;
	Class<T> type;

	public SelectQuery(Class<T> cLass, Object... primaryKey) {
		this.type = cLass;
		setByTypeAndPrimaryKey(cLass, DBColumn.PHASE_SELECT, primaryKey);
		Table table = type.getAnnotation(Table.class);
		this.tableName = table.value();
		sql = "select * from " + tableName + WHERE + joinedString(keys, AND, true);

		for (int i = 0; i < primaryKey.length; i++) {
			parameters.add(primaryKey[i]);
		}
	}

	@SuppressWarnings("unchecked")
	public T execute(Connection conn) {
		System.out.println(sql);
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			if (parameters != null)
				for (int j = 0; j < parameters.size(); j++) {
					pstmt.setObject(j + 1, parameters.get(j));
				}
			ResultSet rs = pstmt.executeQuery();
			Object instance = null;
			try {
				if (rs.next()) {
					instance = type.getConstructor().newInstance();
					for (int i = 0; i < columns.size(); i++) {
						DBColumn column = columns.get(i);
						column.setObjectField(instance, rs.getObject(column.getColumnName()));
					}
					for (int i = 0; i < keys.size(); i++) {
						DBColumn column = keys.get(i);
						column.setObjectField(instance, column.getObject());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (pstmt != null)
					try {
						pstmt.close();
					} catch (SQLException sqle) {
					}
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException sqle) {
					}
			}
			return (T) instance;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

}
