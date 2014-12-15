package mytoday;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.stream.JsonReader;

public class Setting {

	private static Setting setting = new Setting();

	private Map<String, String> db = new HashMap<String, String>();
	private Map<String, String> url = new HashMap<String, String>();

	private Setting() {
		String path = Setting.class.getResource("/").getPath();

		try {
			JsonReader reader = new JsonReader(new FileReader(path
					+ "../setting.mytoday"));
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if (name.equals("database")) {
					readDBSettings(reader);
					continue;
				}
				if (name.equals("url")) {
					readUrlSettings(reader);
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

	public static Map<String, String> get(String type) {
		switch (type) {
		case "db":
			return setting.db;
		case "url":
			return setting.url;
		default:
			return null;
		}

	}

	private void readDBSettings(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String dbn = reader.nextName();
			if (dbn.equals("url")) {
				db.put("url", reader.nextString());
			} else if (dbn.equals("id")) {
				db.put("id", reader.nextString());
			} else if (dbn.equals("password")) {
				db.put("password", reader.nextString());
			}
		}
		reader.endObject();
	}

	private void readUrlSettings(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String dbn = reader.nextName();
			if (dbn.equals("default")) {
				url.put("default", reader.nextString());
			}
		}
		reader.endObject();
	}

}
