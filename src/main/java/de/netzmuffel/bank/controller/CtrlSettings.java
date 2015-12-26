/*
 *
 */
package de.netzmuffel.bank.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Locale;

import javax.swing.JOptionPane;

import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.PropertiesHelper;
import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.enumerator.Error;
import de.netzmuffel.bank.model.enumerator.Property;
import de.netzmuffel.bank.model.enumerator.encryptStatus;
import de.netzmuffel.bank.model.structure.AbstractModelCtrl;
import de.netzmuffel.bank.view.ViewSettings;

/**
 * The Class CtrlSettings controls the formular ViewSettings.
 */
public class CtrlSettings extends AbstractModelCtrl {

	/** The form. */
	private ViewSettings form;

	/** The index. */
	private int index = 0;

	protected String pwd;

	/**
	 * Instantiates a new ctrl settings.
	 *
	 * @param par
	 *            the par
	 * @param db
	 *            the db
	 */
	public CtrlSettings(AbstractModelCtrl par, Database db) {
		super(par, new ViewSettings(), db);

		this.form = (ViewSettings) this.formular;
		this.addListenersToComponents();

		if (Locale.getDefault().getLanguage() == "de") {
			this.index = 0;
		} else {
			this.index = 1;
		}

		this.form.comboBox.setSelectedIndex(this.index);
		this.form.chckbxEncryptPwd.setSelected(PropertiesHelper.isEncryptionEnabled());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * de.netzmuffel.bank.model.structure.AbstractModelCtrl#setComponentsEnable
	 * (java.lang.Boolean)
	 */
	@Override
	public void setComponentsEnable(Boolean b) {
	}

	@Override
	public void addListenersToComponents() {
		this.form.jbtn_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (CtrlSettings.this.bEnabled) {
					CtrlSettings.this.hide();
				}
			}
		});

		this.form.jbtn_ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlSettings.this.saveSettings();
			}
		});
	}

	protected void saveSettings() {
		String lang = "de";

		switch (CtrlSettings.this.form.comboBox.getSelectedIndex()) {
			case 1:
				lang = "en";
				break;
		}

		encryptStatus eEncrypt = encryptStatus.waiting;

		if (CtrlSettings.this.form.chckbxEncryptPwd.isSelected() && !PropertiesHelper.isEncryptionEnabled()) {
			eEncrypt = encryptStatus.encrypt;
		} else if (!CtrlSettings.this.form.chckbxEncryptPwd.isSelected() && PropertiesHelper.isEncryptionEnabled()) {
			eEncrypt = encryptStatus.decrypt;
		}

		if (eEncrypt != encryptStatus.waiting) {
			this.showPwdInputDialog(eEncrypt);
		}

		PropertiesHelper.setProperty(Property.LANGUAGE, lang);
		PropertiesHelper.setProperty(Property.ENCRYPTENABLED, CtrlSettings.this.form.chckbxEncryptPwd.isSelected());

		Locale locale = new Locale(lang);
		Locale.setDefault(locale);

		CtrlSettings.this.hide();
		CtrlSettings.this.parent.hide();

		new CtrlStart(null, CtrlSettings.this.db);
	}

	private void showPwdInputDialog(encryptStatus eEncrypt) {
		boolean bExit = true;
		do {
			bExit = true;
			CtrlSettings.this.pwd = JOptionPane.showInputDialog(null, I18n.get().inputYourNewPassword());

			if (CtrlSettings.this.pwd == null) {
				Error.SETTINGS_CANCEL_APPLY.showDialog();
				return;
			} else if (CtrlSettings.this.pwd.isEmpty()) {
				bExit = false;
				continue;
			}

			try {
				CtrlSettings.this.db.shutdown();
			} catch (SQLException e) {
				Error.SETTINGS_DATABASE_SHUTDOWN.showDialog(e);
			}

			switch (eEncrypt) {
				case encrypt:
					try {
						CtrlSettings.this.db.EncryptDatabase(CtrlSettings.this.pwd);
						CtrlSettings.this.db = new Database(CtrlSettings.this.pwd);
					} catch (SQLException e) {
						Error.SETTINGS_DATABASE_ENCRYPT.showDialog(e);
						bExit = false;
					}
					break;
				case decrypt:
					try {
						CtrlSettings.this.db.DecryptDatabase(CtrlSettings.this.pwd);
						CtrlSettings.this.db = new Database();
					} catch (SQLException e) {
						Error.SETTINGS_DATABASE_DECRYPT.showDialog(e);
						bExit = false;
					}
					break;
				case waiting:
					break;
			}
		} while (!bExit);
	}

}
