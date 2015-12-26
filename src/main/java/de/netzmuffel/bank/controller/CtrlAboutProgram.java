package de.netzmuffel.bank.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.netzmuffel.bank.Config;
import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.Update;
import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.structure.AbstractModelCtrl;
import de.netzmuffel.bank.view.ViewAboutProgram;

public class CtrlAboutProgram extends AbstractModelCtrl {

	private ViewAboutProgram form;

	public CtrlAboutProgram(AbstractModelCtrl par, Database db) {
		super(par, new ViewAboutProgram(), db);
		this.form = (ViewAboutProgram) this.formular;
		this.addListenersToComponents();

		this.form.lblWebVersion.setText(I18n.get().webVersion() + ": " + Config.getLatestVersion());
		this.form.lblOffVersion.setText(I18n.get().version() + ": " + Config.getLocalVersion());

		if (Config.getLatestVersion() > Config.getLocalVersion()) {
			this.form.lblUpdate
					.setText("<html><body style=\"padding:0 10;\">" + I18n.get().pleaseUpdate() + "</body></html>");
			this.form.btnUpdate.setEnabled(true);
		} else {
			this.form.lblUpdate.setText(
					"<html><body style=\"padding:0 10;\">" + I18n.get().updateNotAvailable() + "</body></html>");
			this.form.btnUpdate.setEnabled(false);
		}
	}

	@Override
	public void setComponentsEnable(Boolean b) {
	}

	@Override
	public void addListenersToComponents() {
		this.form.jbtn_close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlAboutProgram.this.hide();
			}
		});

		this.form.btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Update.execute();
			}
		});
	}
}
