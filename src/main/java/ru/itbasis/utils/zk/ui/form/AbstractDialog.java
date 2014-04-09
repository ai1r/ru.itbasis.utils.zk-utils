package ru.itbasis.utils.zk.ui.form;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.ConventionWires;
import org.zkoss.zul.*;
import org.zkoss.zul.impl.XulElement;
import ru.itbasis.utils.zk.ui.form.fields.AbstractField;
import ru.itbasis.utils.zk.ui.toolbar.ToolbarButton;

abstract public class AbstractDialog extends Window {
	private static final int MIN_FORM_WIDTH    = 500;
	private static final int MIN_FORM_HEIGHT   = 400;
	private static final int MIN_PREVIEW_WIDTH = 400;

	protected AbstractDialog _this;

	protected Borderlayout   _layout;
	protected Toolbar        _toolbar;
	protected Grid           _form;

	protected ToolbarButton actionSave;

	public AbstractDialog(Page page) {
		_this = this;

		setPage(page);
		setClosable(true);
		setBorder(true);
		setTitle(" ");
		setMode(Mode.MODAL);

		setMinwidth(MIN_FORM_WIDTH);
		setMinheight(MIN_FORM_HEIGHT);

		setHeight(Integer.toString(MIN_FORM_HEIGHT) + "px");
		setWidth(Integer.toString(MIN_FORM_WIDTH) + "px");

		setMaximizable(true);
		setSizable(true);

		ConventionWires.wireVariables(this, this);
		ConventionWires.addForwards(this, this);

		initLayout();
	}

	private void initLayout() {
		_layout = new Borderlayout();
		_layout.setVflex("1");
		_layout.setParent(this);

		initLayoutNorth();
		initLayoutCenter();
	}

	protected void initLayoutNorth() {
		North north = new North();
		north.setBorder("none");
		north.setParent(_layout);

		_toolbar = new Toolbar();
		_toolbar.setHflex("1");
		_toolbar.setParent(north);
	}

	protected void initLayoutCenter() {
		Center center = new Center();
		center.setBorder("true");
		center.setParent(_layout);

		_form = new Grid();
		_form.setVflex("1");
		_form.setParent(center);

		initFormColumns();

		new Rows().setParent(_form);
		initFormFields();
	}

	private void initFormColumns() {
		Columns head = new Columns();
		head.setVisible(false);
		head.setParent(_form);

		Column c0 = new Column();
		c0.setWidth("35%");
		c0.setParent(head);

		Column c1 = new Column();
//		c1.setWidth("70%");
		c1.setParent(head);
	}

	@SuppressWarnings("unused")
	public void enablePreview(int formWidth) {
		West west = _layout.getWest();
		if (west == null) {
			west = new West();
			west.setBorder("mone");
			west.setParent(_layout);
		}
		west.setWidth(formWidth + "px");
		setWidth((MIN_PREVIEW_WIDTH + formWidth) + "px");
		_form.setParent(_layout.getWest());
	}

	@SuppressWarnings("unused")
	protected XulElement appendFormRow(String fieldLabel, AbstractField fieldComp, EventListener<Event> listener) {
		return appendFormRow(fieldLabel, fieldComp.getBox(), listener);
	}

	@SuppressWarnings("unused")
	protected XulElement appendFormRow(String fieldLabel, HtmlBasedComponent fieldComp, EventListener<Event> listener) {
		XulElement row = appendFormRow(fieldLabel, fieldComp);
		row.addEventListener(Events.ON_CLICK, listener);
		return row;
	}

	@SuppressWarnings("unused")
	protected XulElement appendFormRow(String fieldLabel, AbstractField fieldComp) {
		return appendFormRow(fieldLabel, fieldComp.getBox());
	}

	@SuppressWarnings("unused")
	protected XulElement appendFormRow(String fieldLabel, HtmlBasedComponent fieldComp) {
		Row row = appendRow();

		new Label(Labels.getRequiredLabel(fieldLabel)).setParent(row);
		fieldComp.setParent(row);

		return row;
	}

	@SuppressWarnings("unused")
	protected XulElement appendFormRow(AbstractField fieldComp) {
		return appendFormRow(fieldComp.getBox());
	}

	protected XulElement appendFormRow(HtmlBasedComponent fieldComp) {
		final Row row = appendRow();

		Cell cell = new Cell();
		cell.setColspan(2);
		cell.setParent(row);

		fieldComp.setParent(cell);

		return row;
	}


	private Row appendRow() {
		Row row = new Row();
		row.setParent(_form.getRows());
		return row;
	}

	protected ToolbarButton appendActionSave(EventListener<Event> listener) {
		return appendActionSave("form.action.save", listener);
	}

	protected ToolbarButton appendActionSave(String label, EventListener<Event> listener) {
		actionSave = new ToolbarButton(_toolbar, label, listener);
		return actionSave;
	}

	@SuppressWarnings("unused")
	public void preview(Component comp) {
		if (_layout.getWest() == null) {
			return;
		}
		Center infoBox = _layout.getCenter();
		Components.removeAllChildren(infoBox);
		if (comp != null) {
			comp.setParent(infoBox);
			return;
		}
		Vbox vbox = new Vbox();
		vbox.setPack("center");
		vbox.setAlign("center");
		vbox.appendChild(new Label(Labels.getLabel("empty.exInfo")));
		vbox.setParent(infoBox);
	}

	@SuppressWarnings("unused")
	abstract protected void initToolbar();

	@SuppressWarnings("unused")
	abstract protected void initTitle();

	abstract protected void initFormFields();

	@SuppressWarnings("unused")
	abstract protected void loadFieldData();

	@SuppressWarnings("unused")
	abstract protected void saveFieldData();

}