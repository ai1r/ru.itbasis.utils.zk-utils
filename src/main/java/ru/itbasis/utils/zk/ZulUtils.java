package ru.itbasis.utils.zk;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ZulUtils {
	public static final String PATH_SEPARATOR = "/";

	private static final transient Logger LOG = LoggerFactory.getLogger(ZulUtils.class.getName());

	private ZulUtils() {
	}

	public static String makeFullZulPath(final String path) {
		if (StringUtils.isBlank(path)) {
			return StringUtils.EMPTY;
		}

		if (StringUtils.startsWith(path, PATH_SEPARATOR) && !StringUtils.equals(path, PATH_SEPARATOR)) {
			return makeFullZulPath(path, StringUtils.EMPTY);
		}

		final String pathRoot = "/WEB-INF/zul";

		if (StringUtils.equals(path, PATH_SEPARATOR)) {
			return makeFullZulPath(path, pathRoot);
		}

		return makeFullZulPath(PATH_SEPARATOR + path, pathRoot);
	}

	public static String makeFullZulPath(final String path, final String pathRoot) {
		if (StringUtils.isBlank(path)) {
			return StringUtils.EMPTY;
		}

		if (StringUtils.containsWhitespace(path)) {
			LOG.warn("path contains whitespace: '{}'", path);
		}

		if (!StringUtils.startsWith(path, PATH_SEPARATOR)) {
			return makeFullZulPath(path);
		}

		if (StringUtils.endsWith(path, PATH_SEPARATOR)) {
			return pathRoot + path + "index.zul";
		}

		if (StringUtils.endsWith(path, ".zul")) {
			return pathRoot + path;
		}

		return pathRoot + path + ".zul";
	}
}
