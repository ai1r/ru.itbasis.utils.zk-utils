package ru.itbasis.utils.zk.ui.view;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Auxhead;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Listbox;

public class AbstractListAuxHeaders extends Auxhead {
	public AbstractListAuxHeaders(Listbox list) {
		setParent(list);
	}

	public Auxheader addHeader(String label, int colspan) {
		Auxheader header = new Auxheader();
		if (label != null && !label.trim().isEmpty()) {
			header.setLabel(Labels.getRequiredLabel(label));
		}
		header.setTooltiptext(Labels.getLabel(label));
		header.setColspan(colspan);
		header.setParent(this);
		return header;
	}
}
