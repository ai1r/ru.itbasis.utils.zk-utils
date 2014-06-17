package ru.itbasis.utils.zk.ui.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import ru.itbasis.utils.zk.LogMsg;
import ru.itbasis.utils.zk.ui.AbstractDialog;
import ru.itbasis.utils.zk.ui.toolbar.ToolbarButton;

abstract public class AbstractDialogForm<T> extends AbstractDialog {
	private transient static final Logger LOG                       = LoggerFactory.getLogger(AbstractDialogForm.class
		                                                                                          .getName());
	protected static final         String DEFAULT_BUTTON_SAVE_LABEL = "form.action.save";

	protected T             _item;
	protected ToolbarButton actionSave;

	public AbstractDialogForm(Page page) {
		this(page, null);
	}

	public AbstractDialogForm(Page page, T item) {
		super(page);
		initItem(item);
	}

	abstract protected void initItem();

	abstract protected void initFormFields();

	abstract protected void loadFieldData();

	abstract protected void saveFieldData();

	protected ToolbarButton appendActionSave(EventListener<Event> listener) {
		return appendActionSave(DEFAULT_BUTTON_SAVE_LABEL, listener);
	}

	protected ToolbarButton appendActionSave(String label, EventListener<Event> listener) {
		actionSave = new ToolbarButton(_toolbar).setLabelResource(label).addClickListener(listener);
		return actionSave;
	}

	@Override
	protected void initLayoutCenter() {
		super.initLayoutCenter();
		initFormFields();
	}

	@Override
	protected void initGridColumns() {
		_grid.appendChild(new GridTwoColumn());
	}

	@SuppressWarnings("unchecked")
	protected void initItem(T item) {
		LOG.trace(LogMsg.VALUE, item);
		_item = item;
		initItem();
		LOG.trace(LogMsg.VALUE, _item);
		initTitle();
		loadFieldData();
	}

}
