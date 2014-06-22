package ru.itbasis.utils.zk.ui.view;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;

// FIXME Избавиться от параметров в конструкторе
public abstract class AbstractListHeaders extends Listhead {
	public static final String WIDTH_DATE      = "6em";
	public static final String WIDTH_DATE_TIME = "12em";
	public static final String WIDTH_FLAG      = "2em";

	public AbstractListHeaders(final Listbox list) {
		setParent(list);
		setSizable(true);
	}

	@Deprecated
	public Listheader addHeader(final String label) {
		return addHeaderLabel(label);
	}

	public Listheader addHeaderFlag(final String label) {
		final Listheader header = new Listheader();
		header.setTooltiptext(Labels.getLabel(label));
		header.setWidth(WIDTH_FLAG);
		header.setParent(this);
		return header;
	}

	public Listheader addHeaderLabel(final String label) {
		final Listheader header = new Listheader();
		if (label != null && !label.trim().isEmpty()) {
			header.setLabel(Labels.getRequiredLabel(label));
		}
		header.setTooltiptext(Labels.getLabel(label));
		header.setParent(this);
		return header;
	}

	public Listheader addHeaderLabelRaw(final String label) {
		final Listheader header = new Listheader();
		if (label != null && !label.trim().isEmpty()) {
			header.setLabel(label);
		}
		header.setTooltiptext(label);
		header.setParent(this);
		return header;
	}
}
