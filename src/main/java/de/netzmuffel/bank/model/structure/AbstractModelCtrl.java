package de.netzmuffel.bank.model.structure;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import de.netzmuffel.bank.Config;
import de.netzmuffel.bank.Database;

/**
 * The Class AbstractModelCtrl implements the common functions for every control
 * class.
 */
public abstract class AbstractModelCtrl {
	/** The formular. */
	public JFrame formular;

	/** The parent. */
	public AbstractModelCtrl current, parent;

	/** The b enabled. */
	public boolean bEnabled;

	/** The db. */
	public Database db;

	/**
	 * Instantiates a new abstract model ctrl.
	 *
	 * @param par
	 *            the par
	 * @param form
	 *            the form
	 * @param db
	 *            the db
	 */
	public AbstractModelCtrl(AbstractModelCtrl par, JFrame form, Database db) {
		parent = par;
		current = this;
		this.db = db;
		formular = form;
		formular.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		show();

		formular.addWindowListener(new WindowListener() {

			public void windowOpened(WindowEvent e) {
			}

			public void windowIconified(WindowEvent e) {
			}

			public void windowDeiconified(WindowEvent e) {
			}

			public void windowDeactivated(WindowEvent e) {
			}

			public void windowClosed(WindowEvent e) {
			}

			public void windowActivated(WindowEvent e) {
			}

			public void windowClosing(WindowEvent e) {
				hide();
			}
		});
	}

	/**
	 * Show.
	 */
	public void show() {
		Config.logger.debug("A new frame controller {} is starting", current.getClass().getSimpleName());
		bEnabled = true;
		formular.setVisible(true);
		if (parent != null)
			parent.setEnable(false);
		Config.logger.debug("A new frame controller {} was started", current.getClass().getSimpleName());
	}

	/**
	 * Hide.
	 */
	public void hide() {
		Config.logger.debug("The frame controller {} is closing", current.getClass().getSimpleName());
		bEnabled = false;
		formular.setVisible(false);
		if (parent != null)
			parent.setEnable(true);
		Config.logger.debug("The frame controller {} was closed", current.getClass().getSimpleName());
		formular.dispose();
	}

	/**
	 * Sets the enable.
	 *
	 * @param b
	 *            the new enable
	 */
	public void setEnable(Boolean b) {
		bEnabled = b;
		setComponentsEnable(b);
	}

	/**
	 * Sets the components enable.
	 *
	 * @param b
	 *            the new components enable
	 */
	public abstract void setComponentsEnable(Boolean b);

	public abstract void addListenersToComponents();
}