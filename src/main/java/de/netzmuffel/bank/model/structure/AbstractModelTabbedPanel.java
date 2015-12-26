package de.netzmuffel.bank.model.structure;

import javax.swing.JPanel;

import de.netzmuffel.bank.Database;

/**
 * The Class AbstractModelTabbedPanel imnplements the common functions for every
 * panel which can used in a tabPanel.
 */
public abstract class AbstractModelTabbedPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The db. */
	protected Database db;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	public abstract void setEnabled(boolean b);
}
