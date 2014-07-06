package ru.itbasis.utils.zk.ui.dialog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import ru.itbasis.utils.core.ISelf;
import ru.itbasis.utils.zk.ui.dialog.form.fields.AbstractField;

public abstract class AbstractDialogFrame<Self extends AbstractDialogFrame> extends Grid implements ISelf<Self> {

	public static final String DEFAULT_COLUMN_LABEL_WIDTH = "35%";

	private static final transient Logger LOG = LoggerFactory.getLogger(AbstractDialogFrame.class.getName());

	public AbstractDialogFrame() {
		setVflex(AbstractDialog.DEFAULT_VFLEX);
		initGridColumns().setParent(this);
		new Rows().setParent(this);
	}

	protected Row appendRow(final AbstractField field) {
		LOG.trace("field: {}", field);
		return appendRow(field.getBox());
	}

	protected Row appendRow(final HtmlBasedComponent comp) {
		LOG.trace("comp: {}", comp);
		final Row row = appendRow();

		final Cell cell = new Cell();
		cell.setColspan(getColumns().getChildren().size());
		cell.setParent(row);

		comp.setParent(cell);

		return row;
	}

	protected Row appendRow() {
		final Row row = new Row();
		row.setParent(getRows());
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

	protected Columns initGridColumns() {
		return new GridOneColumn();
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
