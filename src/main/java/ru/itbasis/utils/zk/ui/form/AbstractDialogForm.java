package ru.itbasis.utils.zk.ui.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import ru.itbasis.utils.core.model.IId;
import ru.itbasis.utils.zk.LogMsg;
import ru.itbasis.utils.zk.ui.AbstractDialog;
import ru.itbasis.utils.zk.ui.toolbar.ToolbarButton;

public abstract class AbstractDialogForm<Item extends IId> extends AbstractDialog {
	public static final String LABEL_CORE_BUTTON_SAVE     = "core.form.action.save";
	public static final String LABEL_CORE_FORM_TITLE_ADD  = "core.form.title.add";
	public static final String LABEL_CORE_FORM_TITLE_EDIT = "core.form.title.edit";

	private static final transient Logger LOG = LoggerFactory.getLogger(AbstractDialogForm.class.getName());

	protected Item          _item;
	protected ToolbarButton actionSave;

	public AbstractDialogForm(final Page page) {
		this(page, null);
	}

	public AbstractDialogForm(final Page page, final Item item) {
		super(page);
		initItem(item);
	}

	protected abstract void initFormFields();

	protected abstract void initItem();

	protected abstract void loadFieldData();

	protected abstract void saveFieldData();

	protected ToolbarButton appendActionSave(final EventListener<Event> listener) {
		return appendActionSave(LABEL_CORE_BUTTON_SAVE, listener);
	}

	protected ToolbarButton appendActionSave(final String label, final EventListener<Event> listener) {
		actionSave = new ToolbarButton(_toolbar).setLabelResource(label).addClickListener(listener);
		return actionSave;
	}

	@SuppressWarnings("unchecked")
	protected void initItem(final Item item) {
		LOG.trace(LogMsg.VALUE, item);
		_item = item;
		initItem();
		LOG.trace(LogMsg.VALUE, _item);
		initTitle();
		loadFieldData();
	}

	@Override
	protected void initTitle() {
		initTitle(LABEL_CORE_FORM_TITLE_ADD, LABEL_CORE_FORM_TITLE_EDIT);
	}

	protected void initTitle(final String titleAdd, final String titleEdit) {
		if (_item.getId() == null) {
			setTitle(Labels.getRequiredLabel(LABEL_CORE_FORM_TITLE_ADD));
		} else {
			setTitle(Labels.getRequiredLabel(LABEL_CORE_FORM_TITLE_EDIT));
		}
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

}
