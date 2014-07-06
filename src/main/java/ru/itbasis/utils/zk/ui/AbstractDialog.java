package ru.itbasis.utils.zk.ui;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.ConventionWires;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Center;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.LayoutRegion;
import org.zkoss.zul.North;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;
import ru.itbasis.utils.core.ISelf;
import ru.itbasis.utils.zk.LogMsg;
import ru.itbasis.utils.zk.ui.form.fields.AbstractField;

public abstract class AbstractDialog<Self extends AbstractDialog> extends Window implements ISelf<Self> {
	public static final String LABEL_CORE_DIALOG_TAB_GENERAL_TITLE = "core.dialog.tab.general.title";

	public static final String TAB_ID_GENERAL = "tabGeneral";

	protected static final String DEFAULT_COLUMN_LABEL_WIDTH = "35%";
	protected static final String DEFAULT_HFLEX              = "1";
	protected static final String DEFAULT_VFLEX              = "1";

	protected static final int MIN_FORM_HEIGHT   = 400;
	protected static final int MIN_FORM_WIDTH    = 500;
	protected static final int MIN_PREVIEW_WIDTH = 400;

	private static final transient Logger LOG = LoggerFactory.getLogger(AbstractDialog.class.getName());

	private Borderlayout _layout;
	private Grid         _gridGeneral;
	private Toolbar      _toolbar;
	private boolean      _enablePreview;
	private Tabbox       _tabBox;

	public AbstractDialog(final Page page) {
		LOG.trace(LogMsg.PAGE, page);

		setClosable(true);
		setBorder(true);
		setTitle(StringUtils.SPACE);

		setMinwidth(MIN_FORM_WIDTH);
		setMinheight(MIN_FORM_HEIGHT);

		setHeight(Integer.toString(MIN_FORM_HEIGHT) + "px");
		setWidth(Integer.toString(MIN_FORM_WIDTH) + "px");

		setMaximizable(true);
		setSizable(true);

		ConventionWires.wireVariables(this, this);
		ConventionWires.addForwards(this, this);

		initLayout();

		setPage(page);
	}

	protected abstract void initTitle();

	protected Row appendRow(final AbstractField field) {
		LOG.trace("field: {}", field);
		return appendRow(field.getBox());
	}

	protected Row appendRow(final HtmlBasedComponent comp) {
		LOG.trace("comp: {}", comp);
		final Row row = appendRow();

		final Cell cell = new Cell();
		cell.setColspan(_gridGeneral.getColumns().getChildren().size());
		cell.setParent(row);

		comp.setParent(cell);

		return row;
	}

	protected Row appendRow() {
		final Row row = new Row();
		row.setParent(_gridGeneral.getRows());
		LOG.trace("row: {}", row);
		return row;
	}

	protected Row appendRowField(final String fieldLabel, final AbstractField fieldComp, final EventListener<Event> listener) {
		return appendRowField(fieldLabel, fieldComp.getBox(), listener);
	}

	protected Row appendRowField(final String fieldLabel, final HtmlBasedComponent fieldComp, final EventListener<Event> listener) {
		final Row row = appendRowField(fieldLabel, fieldComp);
		row.addEventListener(Events.ON_CLICK, listener);
		return row;
	}

	protected Row appendRowField(final String fieldLabel, final AbstractField fieldComp) {
		return appendRowField(fieldLabel, fieldComp.getBox());
	}

	protected Row appendRowField(final String fieldLabel, final HtmlBasedComponent fieldComp) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("fieldLabel: {}", fieldLabel);
			LOG.trace("fieldComp: {}", fieldComp);
		}
		final Row row = appendRow();

		new Label(Labels.getRequiredLabel(fieldLabel)).setParent(row);
		fieldComp.setParent(row);

		return row;
	}

	private void disablePreview() {
		final Center center = _layout.getCenter();
		Components.removeAllChildren(center);
		_gridGeneral.setParent(center);
		setWidth(MIN_FORM_WIDTH + "px");
		if (_layout.getWest() != null) {
			_layout.removeChild(_layout.getWest());
		}
		_enablePreview = false;
	}

	public Self disableTabs() {
		_gridGeneral.setParent(getMainBox());
		Components.removeAllChildren(_tabBox);
		_tabBox = null;
		return getSelf();
	}

	public void enablePreview(final boolean flag) {
		if (flag) {
			enablePreview(MIN_FORM_WIDTH);
		} else {
			disablePreview();
		}
	}

	public void enablePreview(final int formWidth) {
		West west = _layout.getWest();
		if (west == null) {
			west = new West();
			west.setBorder("none");
			west.setSplittable(true);
			west.setParent(_layout);
		}
		west.setWidth(formWidth + "px");
		setWidth((MIN_PREVIEW_WIDTH + formWidth) + "px");
		_gridGeneral.setParent(_layout.getWest());

		_enablePreview = true;
	}

	public Self enableTabs() {
		initTabbox();

		return getSelf();
	}

	protected LayoutRegion getMainBox() {
		if (isEnablePreview()) {
			return _layout.getWest();
		}
		return _layout.getCenter();
	}

	protected LayoutRegion getPreviewBox() {
		if (isEnablePreview()) {
			return _layout.getCenter();
		}
		return null;
	}

	protected String getTabId(final String suffix) {
		return getUuid().hashCode() + "_" + suffix;
	}

	public Toolbar getToolbar() {
		assert _toolbar != null;
		return _toolbar;
	}

	protected void initGridColumns(final Grid grid) {
		grid.appendChild(new GridOneColumn());
	}

	private void initGridGeneral(final Component parent) {
		_gridGeneral = new Grid();
		_gridGeneral.setVflex(DEFAULT_VFLEX);
		_gridGeneral.setParent(parent);

		initGridColumns(_gridGeneral);

		new Rows().setParent(_gridGeneral);
	}

	private void initLayout() {
		_layout = new Borderlayout();
		_layout.setVflex(DEFAULT_VFLEX);
		_layout.setParent(this);

		initLayoutNorth();
		initLayoutCenter();
	}

	private void initLayoutCenter() {
		final Center center = new Center();
		center.setBorder("true");
		center.setParent(_layout);

		initGridGeneral(center);
		initLayoutCenterContent();
	}

	protected void initLayoutCenterContent() {
	}

	private void initLayoutNorth() {
		_toolbar = new Toolbar();
		_toolbar.setHflex(DEFAULT_HFLEX);
		if (!initToolbarContent(_toolbar)) {
			_toolbar = null;
			return;
		}

		final North north = new North();
		north.setBorder("none");
		north.setParent(_layout);
		north.appendChild(_toolbar);
	}

	protected void initTabBoxExtTabs(final Tabbox tb) {
	}

	protected Tab initTabGeneral(final Tabbox tb) {
		final Tab tab = TabboxUtils.appendTab(tb, getTabId(TAB_ID_GENERAL), Labels.getRequiredLabel(LABEL_CORE_DIALOG_TAB_GENERAL_TITLE), _gridGeneral);
		tab.setClosable(false);
		return tab;
	}

	private void initTabbox() {
		_tabBox = new Tabbox();
		_tabBox.setVflex(DEFAULT_VFLEX);

		initTabGeneral(_tabBox);
		initTabBoxExtTabs(_tabBox);

		getMainBox().appendChild(_tabBox);
	}

	protected boolean initToolbarContent(final Toolbar tb) {
		return false;
	}

	public boolean isEnablePreview() {
		return _enablePreview;
	}

	public boolean isTabEnabled() {
		return _tabBox != null;
	}

	public void preview(final Component comp) {
		if (_layout.getWest() == null) {
			return;
		}
		final Center infoBox = _layout.getCenter();
		Components.removeAllChildren(infoBox);
		if (comp != null) {
			comp.setParent(infoBox);
			return;
		}
		final Vbox vbox = new Vbox();
		vbox.setPack("center");
		vbox.setAlign("center");
		vbox.appendChild(new Label(Labels.getLabel("empty.exInfo")));
		vbox.setParent(infoBox);
	}

	public Self toogleTabs() {
		if (isTabEnabled()) {
			return disableTabs();
		}
		return enableTabs();
	}

	protected class GridOneColumn extends Columns {
		public GridOneColumn() {
			setVisible(false);

			final Column c0 = new Column();
			c0.setParent(this);
		}
	}

	protected class GridTwoColumn extends Columns {
		public GridTwoColumn() {
			setVisible(false);

			final Column c0 = new Column();
			c0.setAlign("right");
			c0.setWidth(DEFAULT_COLUMN_LABEL_WIDTH);
			c0.setParent(this);

			final Column c1 = new Column();
			c1.setParent(this);
		}
	}
}
