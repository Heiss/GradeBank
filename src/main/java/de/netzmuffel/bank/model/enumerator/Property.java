package de.netzmuffel.bank.model.enumerator;

public enum Property {
	ENCRYPTENABLED, LANGUAGE;

	@Override
	public String toString() {
		return this.name();
	};
}
