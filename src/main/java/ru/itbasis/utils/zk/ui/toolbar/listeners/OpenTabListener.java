package ru.itbasis.utils.zk.ui.toolbar.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Tabbox;
import ru.itbasis.utils.zk.ui.TabboxUtils;

// FIXME Избавиться от параметров в конструкторе
public class OpenTabListener implements EventListener<Event> {
	private static final transient Logger LOG = LoggerFactory.getLogger(OpenTabListener.class.getName());

	private Tabbox tabbox;
	private String tabName;
	private Class  clazz;

	public OpenTabListener(final Tabbox tb, final String name, final Class aClass) {
		this.tabbox = tb;
		this.tabName = name;
		this.clazz = aClass;
	}

	@Override
	public void onEvent(final Event event) throws Exception {
		LOG.trace("clazz: {}", clazz);
		TabboxUtils.goTab(tabbox, Labels.getRequiredLabel(tabName), clazz);
	}
}
