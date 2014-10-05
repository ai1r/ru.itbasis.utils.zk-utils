package ru.itbasis.utils.zk.ui.dialog.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import ru.itbasis.utils.core.LogMsg;
import ru.itbasis.utils.core.model.IId;
import ru.itbasis.utils.zk.ui.dialog.AbstractDialog;
import ru.itbasis.utils.zk.ui.toolbar.ToolbarButton;

public abstract class AbstractDialogForm<Item extends IId, Self extends AbstractDialogForm> extends AbstractDialog<Self> {
	public static final String LABEL_CORE_BUTTON_SAVE     = "core.form.action.save";
	public static final String LABEL_CORE_FORM_TITLE_ADD  = "core.form.title.add";
	public static final String LABEL_CORE_FORM_TITLE_EDIT = "core.form.title.edit";

	private static final transient Logger LOG = LoggerFactory.getLogger(AbstractDialogForm.class.getName());

	private ToolbarButton _actionSave;
	private Item          _itemFix;
	private Item          _itemOrigin;

	public AbstractDialogForm(final Page page) {
		this(page, null);
	}

	public AbstractDialogForm(final Page page, final Item item) {
		super(page);
		_itemOrigin = item;
		_itemFix = fixItem(_itemOrigin);
	}

	protected abstract Item fixItem(final Item item);

	protected abstract void initFormFields();

	protected abstract void loadFieldData();

	protected abstract void saveFieldData();

	protected ToolbarButton appendActionSave(final EventListener<Event> listener) {
		return appendActionSave(LABEL_CORE_BUTTON_SAVE, listener);
	}

	protected ToolbarButton appendActionSave(final String label, final EventListener<Event> listener) {
		_actionSave = new ToolbarButton().setLabelResource(label).addClickListener(listener);
		_actionSave.setParent(getToolbar());
		return _actionSave;
	}

	protected Item getItem() {
		assert _itemOrigin != null;
		if (_itemFix == null) {
			_itemFix = fixItem(_itemOrigin);
		}
		LOG.trace(LogMsg.ITEM, _itemFix);
		return _itemFix;
	}

	protected void setItem(final Item value) {
		LOG.trace(LogMsg.VALUE, value);
		this._itemFix = value;
	}

	@Override
	protected void initTitle() {
		initTitle(LABEL_CORE_FORM_TITLE_ADD, LABEL_CORE_FORM_TITLE_EDIT);
	}

	@Override
	protected void initLayoutCenterContent() {
		initFormFields();
	}

	protected void initTitle(final String titleAdd, final String titleEdit) {
		if (getItem().getId() == null) {
			setTitle(Labels.getRequiredLabel(titleAdd));
		} else {
			setTitle(Labels.getRequiredLabel(titleEdit));
		}
	}

	@Override
	public void doModal() {
		initTitle();
		loadFieldData();
		super.doModal();
	}

}
