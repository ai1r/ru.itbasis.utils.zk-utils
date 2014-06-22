package ru.itbasis.utils.zk.ui.dialog.preview;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Page;
import org.zkoss.zul.Flash;
import org.zkoss.zul.Window;

public class YoutubePreview extends Window {

	public static final String LABEL_TITLE  = "dialog.preview.youtube";
	public static final String LABEL_WIDTH  = "dialog.preview.youtube.width";
	public static final String LABEL_HEIGHT = "dialog.preview.youtube.height";

	private static final String DEFAULT_WIDTH  = "640px";
	private static final String DEFAUTL_HEIGHT = "360px";

	public YoutubePreview(final Page page, final String code) {
		setPage(page);
		setTitle(Labels.getLabel(LABEL_TITLE, "Youtube preview"));
		setClosable(true);
		setBorder(true);
		setMode(Mode.MODAL);

		final Flash flash = new Flash("http://www.youtube.com/v/" + code);
		flash.setWidth(Labels.getLabel(LABEL_WIDTH, DEFAULT_WIDTH));
		flash.setHeight(Labels.getLabel(LABEL_HEIGHT, DEFAUTL_HEIGHT));
		flash.setParent(this);
	}
}
