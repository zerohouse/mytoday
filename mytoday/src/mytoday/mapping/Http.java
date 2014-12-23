package mytoday.mapping;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Http {
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private ArrayList<String> params;

	public HttpServletRequest getReq() {
		return req;
	}

	public HttpServletResponse getResp() {
		return resp;
	}

	public Http(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void forword(String path) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}

	public void setContentType(String type) {
		resp.setContentType(type);
	}

	public void write(String string) throws IOException {
		resp.getWriter().write(string);
	}

	public void setParams(ArrayList<String> params) {
		this.params = params;
	}
	
	public String getUriVariable(int number){
		return params.get(number);
	}

	public void setCharacterEncoding(String encording) throws UnsupportedEncodingException {
		req.setCharacterEncoding(encording);
		resp.setCharacterEncoding(encording);
	}

}
