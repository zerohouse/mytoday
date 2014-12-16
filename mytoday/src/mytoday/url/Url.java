package mytoday.url;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mytoday.Setting;
import mytoday.annotation.Controller;
import mytoday.annotation.Get;
import mytoday.annotation.Post;

public class Url {

	private static Url instance = new Url();

	private Mapper getMap = new Mapper();
	private Mapper postMap = new Mapper();

	public static Mapper getGetMapper() {
		return instance.getMap;
	}

	public static Mapper getPostMapper() {
		return instance.postMap;
	}

	private Url() {
		setMap();
	}

	private final char DOT = '.';

	private final char SLASH = '/';

	private final String CLASS_SUFFIX = ".class";

	private final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

	private final String PATH = Setting.get("general").get("controllerPath");

	public List<Class<?>> find(String scannedPackage) {
		String scannedPath = scannedPackage.replace(DOT, SLASH);
		URL scannedUrl = Thread.currentThread().getContextClassLoader()
				.getResource(scannedPath);
		if (scannedUrl == null) {
			throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR,
					scannedPath, scannedPackage));
		}
		File scannedDir = new File(scannedUrl.getFile());
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (File file : scannedDir.listFiles()) {
			classes.addAll(find(file, scannedPackage));
		}
		return classes;
	}

	private List<Class<?>> find(File file, String scannedPackage) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		String resource = scannedPackage + DOT + file.getName();
		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				classes.addAll(find(child, resource));
			}
		} else if (resource.endsWith(CLASS_SUFFIX)) {
			int endIndex = resource.length() - CLASS_SUFFIX.length();
			String className = resource.substring(0, endIndex);
			try {
				classes.add(Class.forName(className));
			} catch (ClassNotFoundException ignore) {
			}
		}
		return classes;
	}

	private void setMap() {
		List<Class<?>> types = find(PATH);
		Iterator<Class<?>> iterator = types.iterator();
		while (iterator.hasNext()) {
			Class<?> eachClass = iterator.next();
			if (eachClass.isAnnotationPresent(Controller.class)) {
				Method methods[] = eachClass.getDeclaredMethods();
				Get get;
				Post post;
				for (int i = 0; i < methods.length; i++) {
					if (methods[i].isAnnotationPresent(Get.class)) {
						get = methods[i].getAnnotation(Get.class);
						getMap.put(get.value(), new MethodHolder(methods[i],
								eachClass));
					}
					if (methods[i].isAnnotationPresent(Post.class)) {
						post = methods[i].getAnnotation(Post.class);
						postMap.put(post.value(), new MethodHolder(methods[i],
								eachClass));
					}
				}
			}
		}
	}

}
