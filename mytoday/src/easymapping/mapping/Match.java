package easymapping.mapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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

	@Override
	public String toString() {
		StringAdd result = new StringAdd();
		match.forEach((key, value) -> {
			result.add(key + " -> " + value.getDeclaringClass().getName() + "." + value.getName() + "\n");
		});
		return result.toString() + "---- Map End ----\n";
	}

	private class StringAdd {
		private String string;

		private StringAdd() {
			this.string = "";
		}

		private void add(String string) {
			this.string += string;
		}
		
		@Override
		public String toString(){
			return string;
		}
	}

	public ParamHolder get(String key) {
		Method method = match.get(key);
		if (method != null)
			return new ParamHolder(method);
		ParamHolder holder = null;
		Pattern pattern;
		Matcher matcher;
		String regex;
		String regexValue;
		for (Entry<String, String> entry : regexMap.entrySet()) {
			regex = entry.getKey();
			regexValue = entry.getValue();
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(key);
			if (matcher.matches()) {
				method = match.get(regexValue);
				holder = new ParamHolder(method);
				for (int i = 1; i < matcher.groupCount() + 1; i++) {
					holder.addParameter(matcher.group(i));
				}
			}

		}
		return holder;
	}
}
