package ru.itbasis.utils.zk.ui.toolbar.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Tabbox;
import ru.itbasis.utils.zk.ui.TabboxUtils;

public class OpenTabListener implements EventListener<Event> {
	private transient static final Logger LOG = LoggerFactory.getLogger(OpenTabListener.class.getName());

	private Tabbox tabbox;
	private String tabName;
	private Class  clazz;

	public OpenTabListener(Tabbox tabbox, String tabName, Class clazz) {
		this.tabbox = tabbox;
		this.tabName = tabName;
		this.clazz = clazz;
	}

	@Override
	public void onEvent(Event event) throws Exception {
		LOG.trace("event: {}", event);
		LOG.trace("clazz: {}", clazz);
		TabboxUtils.goTab(tabbox, Labels.getRequiredLabel(tabName), clazz);
	}
}
