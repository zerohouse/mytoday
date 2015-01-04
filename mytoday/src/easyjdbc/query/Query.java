package easyjdbc.query;

import java.util.List;

public abstract class Query {
	
	protected int resultSize;
	protected String sql;
	protected List<Object> parameters;

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void addSql(String sql) {
		this.sql += " ";
		this.sql += sql;
	}

	public void addParameters(Object... parameters) {
		for(int i=0;i<parameters.length; i++){
			this.parameters.add(parameters[i]);
		}
	}

	public void setParameters(List<Object> parameters) {
		this.parameters = parameters;
	}

}
