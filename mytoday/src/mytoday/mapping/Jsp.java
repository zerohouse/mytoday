package mytoday.mapping;

import java.io.IOException;

import javax.servlet.ServletException;

public class Jsp implements Response {

	private String jspFile;

	public Jsp(String jspFile) {
		this.jspFile = jspFile;
	}

	@Override
	public void render(Http http) throws ServletException, IOException {
		String jspPath = Setting.get("general", "jspPath");
		http.forword(jspPath + jspFile);
	}

}
