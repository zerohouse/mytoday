package easymapping.mapping;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import easymapping.annotation.DateFormat;

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

	public <T> T getJsonObject(Class<T> cLass, String name) {
		Gson gson = getGsonBuilder(cLass);
		return gson.fromJson(req.getParameter(name), cLass);

	}

	public <T> T getJsonObject(Class<T> cLass) {
		Gson gson = getGsonBuilder(cLass);
		return gson.fromJson(gson.toJson(req.getParameterMap()), cLass);
	}

	private static <T> Gson getGsonBuilder(Class<T> cLass) {
		Field[] fields = cLass.getDeclaredFields();
		DateParser parser = new DateParser();
		
		for (int i = 0; i < fields.length; i++) {
			if(fields[i].isAnnotationPresent(DateFormat.class)){
				parser.addFormat(fields[i].getAnnotation(DateFormat.class).value());
			}
		}
		if(cLass.isAnnotationPresent(DateFormat.class)){
			parser.addFormat(cLass.getAnnotation(DateFormat.class).value());
		}
		GsonBuilder gb = new GsonBuilder().registerTypeAdapter(Date.class, parser);
		Gson gson = gb.create();
		return gson;
	}

	public String getParameter(String name) {
		return req.getParameter(name);
	}

	public Map<String, String[]> getParameterMap() {
		return req.getParameterMap();
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

	public String getUriVariable(int number) {
		return params.get(number);
	}

	public void setCharacterEncoding(String encording) throws UnsupportedEncodingException {
		req.setCharacterEncoding(encording);
		resp.setCharacterEncoding(encording);
	}

	public void sendNotFound() {
		try {
			resp.sendError(404, req.getRequestURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setSessionAttribute(String name, Object value) {
		req.getSession().setAttribute(name, value);
	}

	public void removeSessionAttribute(String name) {
		req.getSession().removeAttribute(name);
	}

	@SuppressWarnings("unchecked")
	public <T> T getSessionAttribute(Class<T> cLass, String name) {
		return (T) req.getSession().getAttribute(name);
	}

	public Object getSessionAttribute(String name) {
		return req.getSession().getAttribute(name);
	}

	public void sendRedirect(String location) {
		try {
			resp.sendRedirect(location);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendError(int errorNo) {
		try {
			resp.sendError(errorNo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendError(int errorNo, String errorMesage) {
		try {
			resp.sendError(errorNo, errorMesage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
