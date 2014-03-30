package ru.itbasis.utils.zk.ui.form.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import java.lang.reflect.Constructor;

public class OpenDialogListener implements EventListener<Event> {
	private transient static final Logger LOG = LoggerFactory.getLogger(OpenDialogListener.class.getName());

	private Class   clazz;
	private boolean flagUnderConstruction;

	public static OpenDialogListener underConstruction(Class clazz) {
		LOG.warn("init underConstruction for class: {}", clazz);
		OpenDialogListener listener = new OpenDialogListener(clazz);
		listener.flagUnderConstruction = true;
		return listener;
	}

	public OpenDialogListener(Class clazz) {
		this.clazz = clazz;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onEvent(Event event) throws Exception {
		LOG.trace("event: {}", event);
		LOG.debug("class: {}", clazz);
		if (flagUnderConstruction) {
			final String msg = Labels.getLabel("msg.underConstruction",
			                                   "under construction...\\n\\n{0}",
			                                   new Object[]{clazz.getName()});
			final String title = Labels.getLabel("msg.underConstruction.title", "Разработка");
			Messagebox.show(msg, title, Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		Window window;
		try {
			Constructor constructor = clazz.getConstructor(Page.class);
			window = (Window) constructor.newInstance(event.getPage());
		} catch (NoSuchMethodException e) {
			window = (Window) clazz.newInstance();
		}
		window.setPage(event.getPage());
		window.doModal();
	}
}
