package mytoday.url;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Http {
	private HttpServletRequest req;
	private HttpServletResponse resp;
	
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
}
