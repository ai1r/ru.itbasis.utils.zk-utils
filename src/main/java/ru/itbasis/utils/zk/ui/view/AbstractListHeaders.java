package ru.itbasis.utils.zk.ui.view;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;

abstract public class AbstractListHeaders extends Listhead {
	public static final String WIDTH_DATE      = "6em";
	public static final String WIDTH_DATE_TIME = "12em";
	public static final String WIDTH_FLAG      = "2em";

	public AbstractListHeaders(Listbox list) {
		setParent(list);
		setSizable(true);
	}

	public Listheader addHeader(String label) {
		Listheader header = new Listheader();
		if (label != null && !label.trim().isEmpty()) {
			header.setLabel(Labels.getRequiredLabel(label));
		}
		header.setTooltiptext(Labels.getLabel(label));
		header.setParent(this);
		return header;
	}

	public Listheader addHeaderFlag(String label) {
		Listheader header = new Listheader();
		header.setTooltiptext(Labels.getLabel(label));
		header.setWidth(WIDTH_FLAG);
		header.setParent(this);
		return header;
	}
}
