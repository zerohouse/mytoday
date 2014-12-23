package mytoday.mapping;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("*.my")
public class DispatcherServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory
			.getLogger(DispatcherServlet.class);
	
	private static final String METHOD_NAME = "Method Name = ";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Method method = Url.getGetMapper().findMethod(req.getRequestURI());
		dispatch(method, new Http(req, resp));
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Method method = Url.getPostMapper().findMethod(req.getRequestURI());
		dispatch(method, new Http(req, resp));
	}

	private void dispatch(Method method, Http http) {
		logger.debug(METHOD_NAME + method.getName());
		Object instance;
		Response render;
		try {
			instance = method.getDeclaringClass().getConstructor().newInstance();
			render = (Response) method.invoke(instance, http);
			render.render(http);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
	}

}
