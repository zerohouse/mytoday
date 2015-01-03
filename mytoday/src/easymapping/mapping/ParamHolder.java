package easymapping.mapping;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ParamHolder {

	private ArrayList<String> params = new ArrayList<String>();
	private Method method;

	public ParamHolder(Method method) {
		this.method = method;
	}

	public Method getMethod() {
		return method;
	}

	public void addParameter(String element) {
		params.add(element);
	}

	public ArrayList<String> getParams() {
		return params;
	}

	public boolean isParamExist() {
		return params.size() > 0;
	}

	public String getName() {
		return method.getDeclaringClass().getName() + "." + method.getName();
	}

}
