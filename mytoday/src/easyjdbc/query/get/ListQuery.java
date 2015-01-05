package easyjdbc.query.get;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import easyjdbc.annotation.Table;
import easyjdbc.query.DBColumn;
import easyjdbc.query.EasyQuery;

public class ListQuery<T> extends EasyQuery {

	Class<T> type;
	String tableName;

	public ListQuery(Class<T> cLass, String whereClause, Object... keys) {
		type = cLass;
		System.out.println(type);
		fieldsDeclare(type);
		Table table = type.getAnnotation(Table.class);
		this.tableName = table.value();
		sql = "select * from " + tableName;
		sql += WHERE + whereClause;
		for (int i = 0; i < keys.length; i++)
			parameters.add(keys[i]);

	}

	public ListQuery(Class<T> cLass) {
		type = cLass;
		System.out.println(type);
		fieldsDeclare(type);
		Table table = type.getAnnotation(Table.class);
		this.tableName = table.value();
		sql = "select * from " + tableName;
		if (!table.defaultCondition().equals(""))
			sql += WHERE + table.defaultCondition();
		System.out.println(sql);
	}

	@SuppressWarnings("unchecked")
	public List<T> execute(Connection conn) {
		List<T> result = new ArrayList<T>();
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
				while (rs.next()) {
					instance = type.getConstructor().newInstance();
					for (String key : keys.keySet()) {
						DBColumn column = keys.get(key);
						column.setObjectField(instance, rs.getObject(column.getColumnName()));
					}
					for (String key : columns.keySet()) {
						DBColumn column = columns.get(key);
						column.setObjectField(instance, rs.getObject(column.getColumnName()));
					}
					result.add((T) instance);
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
			return result;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

}
