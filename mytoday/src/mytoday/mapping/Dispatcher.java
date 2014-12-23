package mytoday.mapping;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mytoday.setting.Setting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dispatcher extends HttpServlet {

	private static final Logger logger = LoggerFactory
			.getLogger(Dispatcher.class);

	private static final String METHOD_NAME = "Method Name = ";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ParamHolder holder = Mapper.get(Mapper.GET, req.getRequestURI());
		dispatch(holder, new Http(req, resp));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ParamHolder holder = Mapper.get(Mapper.POST, req.getRequestURI());
		dispatch(holder, new Http(req, resp));
	}

	private void dispatch(ParamHolder holder, Http http) throws UnsupportedEncodingException {
		String encording = Setting.get(Setting.ENCORDING);
		if (Setting.get(Setting.ENCORDING) != null)
			http.setCharacterEncoding(encording);
		if (holder.isParamExist()) {
			http.setParams(holder.getParams());
		}
		Method method = holder.getMethod();
		logger.debug(METHOD_NAME + method.getName());
		Object instance;
		Response render;
		try {
			instance = method.getDeclaringClass().getConstructor()
					.newInstance();
			render = (Response) method.invoke(instance, http);
			render.render(http);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
	}

}
