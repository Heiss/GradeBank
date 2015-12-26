package de.netzmuffel.bank.model.structure;

import javax.swing.JFrame;

/**
 * The Class AbstractModelView implements the common functions for every View.
 */
public abstract class AbstractModelView extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	public AbstractModelView() {
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.requestFocus();
	}
	
	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		setLocationRelativeTo(null);
	}
}
