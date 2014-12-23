package mytoday.setting;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class Setting {

	public static final String URL = "url";
	public static final String CONTROLLER = "controllerPath";
	public static final String JSP = "jspPath";

	private static Setting setting = new Setting();
	private String controller;
	private String url;
	private String jsp;

	private Setting() {

		String path = Setting.class.getResource("/").getPath();

		try {
			JsonReader reader = new JsonReader(new FileReader(path
					+ "../controller.setting"));
			reader.beginObject();
			while (reader.hasNext()) {
				readGeneralSettings(reader);
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

	public static final String get(String value) {
		switch (value) {
		case URL:
			return setting.url;
		case CONTROLLER:
			return setting.controller;
		case JSP:
			return setting.jsp;
		default:
			return null;
		}
	}

	private void readGeneralSettings(JsonReader reader) throws IOException {
		while (reader.hasNext()) {
			String dbn = reader.nextName();
			if (dbn.equals(URL)) {
				url = reader.nextString();
			} else if (dbn.equals(CONTROLLER)) {
				controller = reader.nextString();
			} else if (dbn.equals(JSP)) {
				jsp = reader.nextString();
			}
		}
	}
}
