package easymapping.response;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import easymapping.mapping.Http;

public class Json implements Response {

	private Object jsonObj;
	private String dateformat;

	public void setDateformat(String dateformat) {
		this.dateformat = dateformat;
	}

	public Json() {
	}

	public void setJsonObj(Object jsonObj) {
		this.jsonObj = jsonObj;
	}

	public Json(Object obj) {
		this.jsonObj = obj;
	}

	@Override
	public void render(Http http) throws IOException {
		Gson gson;
		if(dateformat!=null)
			gson = new GsonBuilder().setDateFormat(dateformat).create();
		else
			gson = new Gson();
		http.setContentType("application/json");
		http.write(gson.toJson(jsonObj));
	}

	public Object getJsonObj() {
		return jsonObj;
	}

}
