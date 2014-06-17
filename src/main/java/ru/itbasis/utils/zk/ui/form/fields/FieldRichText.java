package ru.itbasis.utils.zk.ui.form.fields;

import org.zkforge.ckez.CKeditor;
import org.zkoss.zk.ui.event.Events;

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

	@Override
	public String getRawValue() {
		return _editor.getValue();
	}

	@Override
	public void setRawValue(String value) {
		if (value == null) {
			_editor.setValue("");
			return;
		}
		_editor.setValue(value);
	}

	public Map<String, Object> getConfig() {
		return _editor.getConfig();
	}

	public void setConfig(Map<String, Object> config) {
		_editor.setConfig(config);
	}

	public void setConfigPath(String value) {
		_editor.setCustomConfigurationsPath(value);
	}

}
