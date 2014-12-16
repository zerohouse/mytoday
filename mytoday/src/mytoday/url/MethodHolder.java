package mytoday.url;

import java.lang.reflect.Method;


public class MethodHolder {
	
	private Class<?> classtype;
	private Method method;
	
	public MethodHolder(Method method, Class<?> classtype) {
		this.method = method;
		this.classtype = classtype;
	}
	
	public Method getMethod() {
		return method;
	}
	

	public Class<?> getClasstype() {
		return classtype;
	}

}
