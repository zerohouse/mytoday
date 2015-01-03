package easymapping.mapping;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import easymapping.annotation.Controller;
import easymapping.annotation.Get;
import easymapping.annotation.Post;
import easymapping.setting.Setting;

class Mapper {

	private static final Logger logger = LoggerFactory.getLogger(Mapper.class);

	public static final String GET = "get";
	public static final String POST = "post";
	private static final char DOT = '.';
	private static final char SLASH = '/';
	private static final String CLASS_SUFFIX = ".class";
	private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";
	private static final String PATH = Setting.get(Setting.CONTROLLER);
	private static Mapper instance = new Mapper();

	private Match getMap = new Match();
	private Match postMap = new Match();

	public static ParamHolder get(String type, String url) {
		ParamHolder holder = null;
		switch (type) {
		case GET:
			holder = instance.getMap.get(url);
			break;
		case POST:
			holder = instance.postMap.get(url);
			break;
		}
		if (holder != null) {
			logger.debug("\n requested URI = " + url + ", matched Method = " + holder.getName());
		} else {
			logger.debug("\n requested URI = " + url + ", There is no matched Method");
		}
		return holder;
	}

	Mapper() {
		setMap();
		logger.info("getMap\n" + getMap);
		logger.info("postMap\n" + postMap);
	}

	public List<Class<?>> find(String scannedPackage) {
		String scannedPath = scannedPackage.replace(DOT, SLASH);
		URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
		if (scannedUrl == null) {
			throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
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
		types.forEach(type -> {
			if (type.isAnnotationPresent(Controller.class)) {
				methodSetting(type);
			}
		});
	}

	private void methodSetting(Class<?> eachClass) {
		Method methods[] = eachClass.getDeclaredMethods();
		Get get;
		Post post;

		for (int i = 0; i < methods.length; i++) {
			if (methods[i].isAnnotationPresent(Get.class)) {
				get = methods[i].getAnnotation(Get.class);
				getMap.put(get.value(), methods[i]);
			}
			if (methods[i].isAnnotationPresent(Post.class)) {
				post = methods[i].getAnnotation(Post.class);
				postMap.put(post.value(), methods[i]);
			}
		}
	}

}
