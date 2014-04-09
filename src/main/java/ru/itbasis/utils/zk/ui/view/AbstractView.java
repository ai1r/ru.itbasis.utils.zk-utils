package ru.itbasis.utils.zk.ui.view;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.ConventionWires;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.North;
import org.zkoss.zul.Toolbar;
import ru.itbasis.utils.zk.ui.toolbar.ToolbarButton;

abstract public class AbstractView extends Borderlayout {
	public static final String DEFAULT_VFLEX = "1";
	public static final String DEFAULT_HFLEX = "1";

	protected Toolbar _toolbar;

	protected ToolbarButton actionAdd;
	protected ToolbarButton actionEdit;

	public AbstractView() {
		setVflex("1");

		ConventionWires.wireVariables(this, this);
		ConventionWires.addForwards(this, this);

		initLayoutNorth();
		initLayoutCenter();
	}

	protected void initLayoutNorth() {
		North north = new North();
		north.setBorder("none");
		north.setParent(this);

		_toolbar = new Toolbar();
		_toolbar.setHflex("true");
		_toolbar.setParent(north);
		initToolbar();
	}

	protected void initLayoutCenter() {
		Center center = new Center();
		center.setBorder("true");
		center.setParent(this);
		initLayoutCenterChild();
	}

	abstract protected void initLayoutCenterChild();

	abstract protected void initToolbar();

	protected ToolbarButton appendActionAdd(EventListener<Event> listener) {
		return appendActionAdd("view.action.add", listener);
	}

	protected ToolbarButton appendActionAdd(String label, EventListener<Event> listener) {
		actionAdd = new ToolbarButton(_toolbar, label, listener);
		return actionAdd;
	}

	protected ToolbarButton appendActionEdit(EventListener<Event> listener) {
		return appendActionEdit("view.action.edit", listener);
	}

	protected ToolbarButton appendActionEdit(String label, EventListener<Event> listener) {
		actionEdit = new ToolbarButton(_toolbar, label, listener);
		actionEdit.setDisabled(true);
		return actionEdit;
	}

}
