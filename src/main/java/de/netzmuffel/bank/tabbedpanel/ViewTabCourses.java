package de.netzmuffel.bank.tabbedpanel;

import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.structure.AbstractModelTabbedPanel;
import de.netzmuffel.bank.tablemodel.TableModelShowCourses;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.awt.GridLayout;

/**
 * The Class ViewTabCourses create a panel and can used in a tabPanel.
 */
public class ViewTabCourses extends AbstractModelTabbedPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The table. */
	public JTable table;

	/** The panel. */
	public JPanel panel;

	/** The btn new class. */
	public JButton btnNewClass;

	/** The btn show class. */
	public JButton btnShowClass;

	/** The scroll pane. */
	public JScrollPane scrollPane;

	/** The table model. */
	public TableModelShowCourses tableModel;
	
	/** The btn delete class. */
	public JButton btnDeleteClass;
	
	/** The btn edit class. */
	public JButton btnEditClass;

	/**
	 * Instantiates a new view tab courses.
	 *
	 * @param db the db
	 */
	public ViewTabCourses(Database db) {
		setLayout(new BorderLayout(0, 0));

		this.db = db;

		panel = new JPanel();
		add(panel, BorderLayout.WEST);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		btnShowClass = new JButton(I18n.get().showClass());
		panel.add(btnShowClass);

		btnNewClass = new JButton(I18n.get().createClass());
		panel.add(btnNewClass);

		btnEditClass = new JButton(I18n.get().editClass());
		panel.add(btnEditClass);

		btnDeleteClass = new JButton(I18n.get().deleteClass());
		panel.add(btnDeleteClass);

		tableModel = new TableModelShowCourses(this.db);
		table = new JTable(tableModel);

		scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.netzmuffel.bank.model.structure.AbstractModelTabbedPanel#setEnabled
	 * (boolean)
	 */
	@Override
	public void setEnabled(boolean b) {
		btnNewClass.setEnabled(b);
		btnShowClass.setEnabled(b);
		btnDeleteClass.setEnabled(b);
		btnEditClass.setEnabled(b);
	}
}
