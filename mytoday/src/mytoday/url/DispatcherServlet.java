package mytoday.url;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mytoday.Setting;

@WebServlet("*.my")
public class DispatcherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String uri = req.getRequestURI();
		dispatch(Url.getGetMapper().get(uri), req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String uri = req.getRequestURI();
		dispatch(Url.getPostMapper().get(uri), req, resp);
	}

	private void dispatch(MethodHolder methodHolder, HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		if (methodHolder == null) {
			resp.sendError(404, "not found");
			return;
		}
		Http http = new Http(req, resp);
		Object url = executeMethod(methodHolder, http);
		if (url == null)
			return;
		String jspPath = Setting.get("general").get("jspPath");
		RequestDispatcher dispatcher = req.getRequestDispatcher(jspPath + url);
		dispatcher.forward(req, resp);
	}

	private Object executeMethod(MethodHolder methodHolder, Http http) {
		Object instance;
		try {
			instance = methodHolder.getClasstype().getConstructor()
					.newInstance();
			Object url = (String) methodHolder.getMethod().invoke(instance,
					http);
			return url;
		} catch (Exception e) {
			return null;
		}
	}
}
