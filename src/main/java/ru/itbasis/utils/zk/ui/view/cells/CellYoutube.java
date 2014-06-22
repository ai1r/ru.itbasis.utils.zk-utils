package ru.itbasis.utils.zk.ui.view.cells;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zhtml.I;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import ru.itbasis.utils.zk.ui.dialog.preview.YoutubePreview;

public class CellYoutube extends Listcell {
	private static final transient Logger LOG = LoggerFactory.getLogger(CellYoutube.class.getName());

	private I tagI;

	public CellYoutube(final String code) {
		LOG.debug("code: {}", code);

		final Label label = new Label(code);
		label.setParent(this);

		tagI = new I();
		tagI.setSclass("z-icon-youtube");
		tagI.setParent(this);
		tagI.addEventListener(Events.ON_CLICK, new Event$Preview(code));
	}

	private class Event$Preview implements EventListener<Event> {
		private String code;

		public Event$Preview(final String value) {
			this.code = value;
		}

		@Override
		public void onEvent(final Event event) throws Exception {
			new YoutubePreview(event.getPage(), code).doModal();
		}
	}
}
