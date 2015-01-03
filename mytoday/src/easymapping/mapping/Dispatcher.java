package easymapping.mapping;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import easymapping.response.Response;
import easymapping.setting.Setting;

public class Dispatcher extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ParamHolder holder = Mapper.get(Mapper.GET, req.getRequestURI());
		dispatch(holder, new Http(req, resp));
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ParamHolder holder = Mapper.get(Mapper.POST, req.getRequestURI());
		dispatch(holder, new Http(req, resp));
	}

	private void dispatch(ParamHolder holder, Http http) throws UnsupportedEncodingException {
		if (holder == null) {
			http.sendNotFound();
			return;
		}

		String encording = Setting.get(Setting.ENCORDING);
		if (encording != null)
			http.setCharacterEncoding(encording);
		if (holder.isParamExist()) {
			http.setParams(holder.getParams());
		}
		Method method = holder.getMethod();
		Object instance;
		Response render;
		try {
			instance = method.getDeclaringClass().getConstructor().newInstance();
			if (method.getParameterCount() == 0)
				render = (Response) method.invoke(instance);
			else
				render = (Response) method.invoke(instance, http);
			if(render==null)
				return;
			render.render(http);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
