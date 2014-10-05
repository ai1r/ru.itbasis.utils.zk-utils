package ru.itbasis.utils.zk.ui.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
import ru.itbasis.utils.zk.ui.toolbar.ToolbarButton;

public abstract class AbstractViewList extends AbstractView {
	private static final transient Logger LOG = LoggerFactory.getLogger(AbstractViewList.class.getName());

	protected Listbox _list;

	protected abstract void initHeaders();

	protected abstract void loadData();

	public Listbox getList() {
		return _list;
	}

	@Override
	protected void initLayoutCenterChild() {
		_list = initList(getCenter());
		initHeaders();
	}

	@Override
	public void onPageAttached(final Page newpage, final Page oldpage) {
		super.onPageAttached(newpage, oldpage);
		loadData();
	}

	protected Listbox initList(final Component parent) {
		final Listbox list = new Listbox();
		list.setParent(parent);
		list.setVflex(DEFAULT_VFLEX);
		list.setAutopaging(true);
		list.setPagingPosition("both");
		list.setMold("paging");
		return list;
	}

	public class Event$List$Refresh implements EventListener<Event> {
		@Override
		public void onEvent(final Event event) throws Exception {
			final ToolbarButton button = getActionEdit();
			if (button != null) {
				button.setDisabled(true);
			}
			loadData();
		}
	}

	public class Event$Listitem$OnClick implements EventListener<Event> {
		@Override
		public void onEvent(final Event event) throws Exception {
			final ToolbarButton button = getActionEdit();
			if (button != null) {
				button.setDisabled(false);
			}
		}
	}
}
