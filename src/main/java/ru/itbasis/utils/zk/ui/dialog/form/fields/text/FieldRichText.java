package ru.itbasis.utils.zk.ui.dialog.form.fields.text;

import org.zkforge.ckez.CKeditor;
import org.zkoss.zk.ui.event.Events;
import ru.itbasis.utils.zk.ui.dialog.form.fields.AbstractField;

import java.util.Map;

public class FieldRichText extends AbstractField<String> {
	private CKeditor _editor;

	public FieldRichText() {
		super();
		_editor = new CKeditor();
		_editor.setHflex(DEFAULT_HFLEX);
		_editor.setCustomConfigurationsPath("/static/js/ckeditor.js");
		_editor.setParent(getBox());
		_editor.addEventListener(Events.ON_CHANGE, new Event$Default$OnChange());
	}

	public Map<String, Object> getConfig() {
		return _editor.getConfig();
	}

	public void setConfig(final Map<String, Object> config) {
		_editor.setConfig(config);
	}

	@Override
	public String getRawValue() {
		return _editor.getValue();
	}

	@Override
	public void setRawValue(final String value) {
		if (value == null) {
			_editor.setValue("");
			return;
		}
		_editor.setValue(value);
	}

	public void setConfigPath(final String value) {
		_editor.setCustomConfigurationsPath(value);
	}

}
