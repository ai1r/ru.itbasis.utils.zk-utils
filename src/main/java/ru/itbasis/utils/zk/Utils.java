package ru.itbasis.utils.zk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Utils {
	private static final transient Logger LOG = LoggerFactory.getLogger(Utils.class.getName());

	private Utils() {
	}

	public static String getZulPage(final String path) {
		final String PATH_SEPARATOR = "/";

		String fullPath = "";
		if (!path.startsWith(PATH_SEPARATOR)) {
			fullPath = "/WEB-INF/zul" + PATH_SEPARATOR + path;
		}

		if (path.endsWith(PATH_SEPARATOR)) {
			fullPath = fullPath + "index";
		}

		fullPath = fullPath + ".zul";

		LOG.debug("fullPath: {}", fullPath);
		return fullPath;
	}
}
