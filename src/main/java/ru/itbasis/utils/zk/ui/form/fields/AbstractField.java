package ru.itbasis.utils.zk.ui.form.fields;

import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.ConventionWires;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;

abstract public class AbstractField<T> extends AbstractComponent {
	public static final String ICON_NEW     = "z-icon-plus";
	public static final String ICON_EDIT    = "z-icon-pencil";
	public static final String ICON_REFRESH = "z-icon-refresh";

	public static final String DEFAULT_WIDTH = "98%";
	public static final String DEFAULT_HFLEX = "1";

	public static final String CONSTRAINT_NOEMPTY         = "no empty,end_after";
	public static final String CONSTRAINT_NUMBER_POSITIVE = "no negative,no zero,end_after";

	protected AbstractComponent _this;

	protected HtmlBasedComponent box;

	protected AbstractField() {
		_this = this;
		ConventionWires.wireVariables(this, this);
		initBox();
	}

	protected void initBox() {
		box = new Hbox();
		box.setHflex(DEFAULT_WIDTH);
	}

	public HtmlBasedComponent getBox() {
		return box;
	}

	@SuppressWarnings("unused")
	protected Button appendActionAdd(EventListener<Event> listener) {
		Button action = new Button();
		action.setIconSclass(ICON_NEW);
		if (listener != null) {
			action.addEventListener(Events.ON_CLICK, listener);
		}
		action.setParent(box);
		return action;
	}

	@SuppressWarnings("unused")
	protected Button appendActionEdit(EventListener<Event> listener) {
		Button action = new Button();
		action.setIconSclass(ICON_EDIT);
		if (listener != null) {
			action.addEventListener(Events.ON_CLICK, listener);
		}
		action.setParent(box);
		return action;
	}

	@SuppressWarnings("unused")
	protected Button appendActionRefresh(EventListener<Event> listener) {
		Button action = new Button();
		action.setIconSclass(ICON_REFRESH);
		if (listener != null) {
			action.addEventListener(Events.ON_CLICK, listener);
		}
		action.setParent(box);
		return action;
	}

	@SuppressWarnings("unused")
	public void setVisibleRow(boolean flag) {
		box.getParent().getParent().setVisible(flag);
	}

	@SuppressWarnings("unused")
	public boolean isVisibleRow() {
		return box.getParent().getParent().isVisible();
	}

	@SuppressWarnings("unused")
	abstract public T getValue();

	@SuppressWarnings("unused")
	abstract public void setValue(T value);

	@SuppressWarnings("unused")
	abstract public void clear();
}