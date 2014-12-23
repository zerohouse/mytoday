package mytoday.mapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Match {

	private Map<String, Method> match = new HashMap<String, Method>();
	private Map<String, String> regexMap = new HashMap<String, String>();

	public void put(String key, Method method) {
		if (key.contains("{}")) {
			String regex = key.replace("{}", "(.*)");
			regexMap.put(regex, key);
		}
		match.put(key, method);
	}

	public ParamHolder get(String key) {
		Method method = match.get(key);
		if (method != null)
			return new ParamHolder(match.get(key));;

		Iterator<String> regexiter = regexMap.keySet().iterator();

		String regex;
		Pattern pattern;
		Matcher matcher;
		while (regexiter.hasNext()) {
			regex = regexiter.next();
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(key);
			if (matcher.matches()) {
				method = match.get(regexMap.get(regex));
				ParamHolder holder = new ParamHolder(method);
				for (int i = 1; i < matcher.groupCount()+1; i++) {
					holder.addParameter(matcher.group(i));
				}
				return holder;
			}
		}
		return null;
	}
}
