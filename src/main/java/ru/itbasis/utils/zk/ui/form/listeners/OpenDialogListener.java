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
	public static final String LABEL_MSG_UNDER_CONSTRUCTION       = "msg.underConstruction";
	public static final String LABEL_MSG_UNDER_CONSTRUCTION_TITLE = "msg.underConstruction.title";

	private static final transient Logger LOG = LoggerFactory.getLogger(OpenDialogListener.class.getName());

	private Class   clazz;
	private boolean flagUnderConstruction;

	public OpenDialogListener(final Class value) {
		this.clazz = value;
	}

	public static OpenDialogListener underConstruction(final Object o) {
		return underConstruction(o.getClass());
	}

	public static OpenDialogListener underConstruction(final Class clazz) {
		LOG.warn("init underConstruction for class: {}", clazz);
		final OpenDialogListener listener = new OpenDialogListener(clazz);
		listener.flagUnderConstruction = true;
		return listener;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onEvent(final Event event) throws Exception {
		LOG.debug("clazz: {}", clazz);
		if (flagUnderConstruction) {
			final String msg = Labels.getLabel(LABEL_MSG_UNDER_CONSTRUCTION,
			                                   "under construction...\\n\\n{0}",
			                                   new Object[]{clazz.getName()});
			final String title = Labels.getLabel(LABEL_MSG_UNDER_CONSTRUCTION_TITLE, "In developing...");
			Messagebox.show(msg, title, Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		Window window;
		try {
			final Constructor constructor = clazz.getConstructor(Page.class);
			window = (Window) constructor.newInstance(event.getPage());
		} catch (NoSuchMethodException e) {
			window = (Window) clazz.newInstance();
		}
		window.setPage(event.getPage());
		window.doModal();
	}
}
