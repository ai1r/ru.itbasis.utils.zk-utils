package ru.itbasis.utils.zk.ui.form.fields;

public class FieldPassword extends FieldText {

	public FieldPassword() {
		super();
		_text.setConstraint("");
		_text.setType("password");
	}

	@Override
	public String getRawValue() {
		final String value = super.getRawValue();
		if (value == null) {
			return "";
		}
		return value.trim();
	}
}
