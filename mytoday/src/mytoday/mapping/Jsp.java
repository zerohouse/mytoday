package mytoday.mapping;

import java.io.IOException;

import javax.servlet.ServletException;

import mytoday.setting.Setting;

public class Jsp implements Response {

	private String jspFile;

	public Jsp(String jspFile) {
		this.jspFile = jspFile;
	}

	@Override
	public void render(Http http) throws ServletException, IOException {
		String jspPath = Setting.get(Setting.JSP);
		http.forword(jspPath + jspFile);
	}

}
