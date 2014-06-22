package ru.itbasis.utils.zk.ui.view;

import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.ConventionWires;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.North;
import org.zkoss.zul.Toolbar;
import ru.itbasis.utils.zk.ui.toolbar.ToolbarButton;

public abstract class AbstractView extends Borderlayout {
	public static final String DEFAULT_VFLEX = "1";
	public static final String DEFAULT_HFLEX = "1";

	protected Toolbar _toolbar;

	protected ToolbarButton actionAdd;
	protected ToolbarButton actionEdit;

	public AbstractView() {
		ConventionWires.wireVariables(this, this);
		ConventionWires.addForwards(this, this);
	}

	protected abstract void initLayoutCenterChild();

	protected abstract void initToolbar();

	protected ToolbarButton appendActionAdd(final EventListener<Event> listener) {
		return appendActionAdd("view.action.add", listener);
	}

	protected ToolbarButton appendActionAdd(final String label, final EventListener<Event> listener) {
		actionAdd = new ToolbarButton(_toolbar).setLabelResource(label).addClickListener(listener);
		return actionAdd;
	}

	protected ToolbarButton appendActionEdit(final EventListener<Event> listener) {
		return appendActionEdit("view.action.edit", listener);
	}

	protected ToolbarButton appendActionEdit(final String label, final EventListener<Event> listener) {
		actionEdit = new ToolbarButton(_toolbar).setLabelResource(label).addClickListener(listener);
		actionEdit.setDisabled(true);
		return actionEdit;
	}

	protected void initLayoutCenter() {
		final Center center = new Center();
		center.setBorder("true");
		center.setParent(this);
		initLayoutCenterChild();
	}

	protected void initLayoutNorth() {
		final North north = new North();
		north.setBorder("none");
		north.setParent(this);

		_toolbar = new Toolbar();
		_toolbar.setHflex("true");
		_toolbar.setParent(north);
		initToolbar();
	}

	@Override
	public void onPageAttached(final Page newpage, final Page oldpage) {
		super.onPageAttached(newpage, oldpage);
		setVflex(DEFAULT_VFLEX);
		initLayoutNorth();
		initLayoutCenter();
	}

}
