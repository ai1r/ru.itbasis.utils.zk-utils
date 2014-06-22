package ru.itbasis.utils.zk.ui.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import ru.itbasis.utils.zk.LogMsg;
import ru.itbasis.utils.zk.ui.AbstractDialog;
import ru.itbasis.utils.zk.ui.toolbar.ToolbarButton;

public abstract class AbstractDialogForm<T> extends AbstractDialog {
	protected static final String DEFAULT_BUTTON_SAVE_LABEL = "form.action.save";

	private static final transient Logger LOG = LoggerFactory.getLogger(AbstractDialogForm.class.getName());

	protected T             _item;
	protected ToolbarButton actionSave;

	public AbstractDialogForm(final Page page) {
		this(page, null);
	}

	public AbstractDialogForm(final Page page, final T item) {
		super(page);
		initItem(item);
	}

	protected abstract void initFormFields();

	protected abstract void initItem();

	protected abstract void loadFieldData();

	protected abstract void saveFieldData();

	protected ToolbarButton appendActionSave(final EventListener<Event> listener) {
		return appendActionSave(DEFAULT_BUTTON_SAVE_LABEL, listener);
	}

	protected ToolbarButton appendActionSave(final String label, final EventListener<Event> listener) {
		actionSave = new ToolbarButton(_toolbar).setLabelResource(label).addClickListener(listener);
		return actionSave;
	}

	@Override
	protected void initGridColumns() {
		_grid.appendChild(new GridTwoColumn());
	}

	@Override
	protected void initLayoutCenter() {
		super.initLayoutCenter();
		initFormFields();
	}

	@SuppressWarnings("unchecked")
	protected void initItem(final T item) {
		LOG.trace(LogMsg.VALUE, item);
		_item = item;
		initItem();
		LOG.trace(LogMsg.VALUE, _item);
		initTitle();
		loadFieldData();
	}

}
