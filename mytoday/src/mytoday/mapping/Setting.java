package mytoday.mapping;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.stream.JsonReader;

public class Setting {
	
	public static final String GENERAL = "general";
	public static final String URL = "url";
	public static final String CONTROLLER = "controllerPath";
	public static final String JSP = "jspPath";
	
	private static Setting setting = new Setting();

	private Map<String, String> general = new HashMap<String, String>();

	private Setting() {
		
		String path = Setting.class.getResource("/").getPath();

		try {
			JsonReader reader = new JsonReader(new FileReader(path
					+ "../controller.setting"));
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if (name.equals(GENERAL)) {
					readGeneralSettings(reader);
					continue;
				}
				reader.skipValue();
			}
			reader.endObject();
			reader.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println("Current Path: "
					+ System.getProperty("user.dir"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static final String get(String type, String value) {
		switch (type) {
		case GENERAL:
			return setting.general.get(value);
		default:
			return null;
		}

	}
	
	private void readGeneralSettings(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String dbn = reader.nextName();
			if (dbn.equals(URL)) {
				general.put(URL, reader.nextString());
			} else if (dbn.equals(CONTROLLER)) {
				general.put(CONTROLLER, reader.nextString());
			} else if (dbn.equals(JSP)) {
				general.put(JSP, reader.nextString());
			}
		}
		reader.endObject();
	}
}
