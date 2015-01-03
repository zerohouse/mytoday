package easyjdbc.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetRecordQuery extends QueryProto {

	@Override
	public Object execute(PreparedStatement pstmt, Connection conn, ResultSet rs) throws SQLException {
		List<Object> record = new ArrayList<Object>();
		pstmt = conn.prepareStatement(sql);
		if (parameters != null)
			for (int j = 0; j < parameters.size(); j++) {
				pstmt.setObject(j + 1, parameters.get(j));
			}
		rs = pstmt.executeQuery();
		while (rs.next()) {
			for (int i = 0; i < resultSize; i++) {
				record.add(rs.getObject(i + 1));
			}
		}

		return record;
	}

	public GetRecordQuery(int resultSize, String sql, List<Object> parameters) {
		this.resultSize = resultSize;
		this.sql = sql;
		this.parameters = parameters;
		if (parameters == null) {
			this.parameters = new ArrayList<Object>();
		}
	}

	public GetRecordQuery(int resultSize, String sql, Object... parameters) {
		this.resultSize = resultSize;
		this.sql = sql;
		this.parameters = new ArrayList<Object>();
		for (int i = 0; i < parameters.length; i++) {
			this.parameters.add(parameters[i]);
		}
	}
}