package ru.itbasis.utils.zk.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Menuitem;
import ru.itbasis.utils.zk.ui.TabboxUtils;

import java.util.List;

abstract public class AbstractTabMenuController extends SelectorComposer<Component> {
	private transient static final Logger LOG = LoggerFactory.getLogger(AbstractTabMenuController.class.getName());

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		List<Component> list = Selectors.find(comp, Menuitem.class.getSimpleName());
		for (Component cmp : list) {
			Menuitem menuitem = (Menuitem) cmp;
			if (!menuitem.getValue().isEmpty()) {
				menuitem.addEventListener(Events.ON_CLICK, new OpenTabListener());
			}
		}
	}

	private class OpenTabListener implements EventListener<Event> {
		@Override
		public void onEvent(Event event) throws Exception {
			Menuitem mi = (Menuitem) event.getTarget();
			TabboxUtils.goTab(mi.getRoot(), mi);
		}
	}
}