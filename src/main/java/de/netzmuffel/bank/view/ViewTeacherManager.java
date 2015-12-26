package de.netzmuffel.bank.view;

import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.structure.AbstractModelView;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JTable;
import java.awt.GridLayout;

/**
 * The Class ViewTeacherManager.
 */
public class ViewTeacherManager extends AbstractModelView {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The jbtn_close. */
	public JButton jbtn_close;

	/** The panel. */
	public JPanel panel;
	
	/** The pnl_btn. */
	private JPanel pnl_btn;
	
	/** The table. */
	public JTable table;
	
	/** The btn teacher edit. */
	public JButton btnTeacherEdit;
	
	/** The btn teacher create. */
	public JButton btnTeacherCreate;

	/** The scroll pane. */
	private JScrollPane scrollPane;

	/** The btn teacher remove. */
	public JButton btnTeacherRemove;

	/**
	 * Instantiates a new view teacher manager.
	 */
	public ViewTeacherManager() {
		super();
		this.setTitle(I18n.get().teacher());
		this.setSize(600, 400);
		this.getContentPane().setLayout(new BorderLayout());

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		pnl_btn = new JPanel();
		panel.add(pnl_btn, BorderLayout.WEST);
		pnl_btn.setLayout(new GridLayout(0, 1, 0, 0));

		btnTeacherCreate = new JButton(I18n.get().teacherCreate());
		pnl_btn.add(btnTeacherCreate);

		btnTeacherEdit = new JButton(I18n.get().teacherEdit());
		pnl_btn.add(btnTeacherEdit);

		btnTeacherRemove = new JButton(I18n.get().teacherRemove());
		pnl_btn.add(btnTeacherRemove);

		table = new JTable();
		scrollPane = new JScrollPane(table);
		panel.add(scrollPane, BorderLayout.CENTER);

		JPanel jpnl_btnbar = new JPanel();
		getContentPane().add(jpnl_btnbar, BorderLayout.SOUTH);
		jpnl_btnbar.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		jbtn_close = new JButton(I18n.get().componentButtonClose());
		jpnl_btnbar.add(jbtn_close);
	}
}
