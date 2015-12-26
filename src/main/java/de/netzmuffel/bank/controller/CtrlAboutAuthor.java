/*
 * 
 */
package de.netzmuffel.bank.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.model.structure.AbstractModelCtrl;
import de.netzmuffel.bank.view.ViewAboutAuthor;

/**
 * The Class CtrlHelp controls the formular ViewHelp.
 */
public class CtrlAboutAuthor extends AbstractModelCtrl {

	/** The form. */
	private ViewAboutAuthor form;

	/**
	 * Instantiates a new ctrl help.
	 *
	 * @param par
	 *            the par
	 * @param db
	 *            the db
	 */
	public CtrlAboutAuthor(AbstractModelCtrl par, Database db) {
		super(par, new ViewAboutAuthor(), db);
		form = (ViewAboutAuthor) formular;
		addListenersToComponents();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.netzmuffel.bank.model.structure.AbstractModelCtrl#setComponentsEnable
	 * (java.lang.Boolean)
	 */
	@Override
	public void setComponentsEnable(Boolean b) {
	}

	@Override
	public void addListenersToComponents() {
		form.jbtn_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hide();
			}
		});
	}

}
