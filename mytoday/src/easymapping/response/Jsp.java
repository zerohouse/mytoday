package easymapping.response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import easymapping.mapping.Http;
import easymapping.setting.Setting;

public class Jsp implements Response {

	private static final String jspPath = Setting.get(Setting.JSP);
	private String jspFileName;

	private List<String> keys = new ArrayList<String>();
	private List<Object> objs = new ArrayList<Object>();

	public Jsp() {
	}

	public void setJspFileName(String jspFileName) {
		this.jspFileName = jspFileName;
	}

	public Jsp(String jspFileName) {
		this.jspFileName = jspFileName;
	}

	public void put(String key, Object obj) {
		keys.add(key);
		objs.add(obj);
	}

	@Override
	public void render(Http http) throws ServletException, IOException {
		for (int i = 0; i < keys.size(); i++) {
			http.getReq().setAttribute(keys.get(i), objs.get(i));
		}
		http.forword(jspPath + jspFileName);
	}

	public String getJspFileName() {
		return jspFileName;
	}

	public List<String> getKeys() {
		return keys;
	}

	public List<Object> getObjs() {
		return objs;
	}
}
