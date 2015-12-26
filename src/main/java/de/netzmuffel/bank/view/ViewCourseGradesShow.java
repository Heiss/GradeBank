package de.netzmuffel.bank.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.structure.AbstractModelView;

/**
 * The Class ViewShowGrades.
 */
public class ViewCourseGradesShow extends AbstractModelView {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The jbtn_close. */
	public JButton btnClose;

	/** The panel. */
	public JPanel panel;
	
	/** The pnl_btn. */
	private JPanel pnl_btn;
	
	/** The table. */
	public JTable table;

	/** The scroll pane. */
	public JScrollPane scrollPane;

	/** The btn grades remove. */
	public JButton btnGradesRemove;
	
	/** The btn topic add. */
	public JButton btnTopicAdd;
	public JButton btnTopicEdit;

	/**
	 * Instantiates a new view show grades.
	 */
	public ViewCourseGradesShow() {
		super();
		this.setTitle(I18n.get().showGrades());
		this.setSize(600, 400);
		this.getContentPane().setLayout(new BorderLayout());

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		pnl_btn = new JPanel();
		panel.add(pnl_btn, BorderLayout.WEST);
		pnl_btn.setLayout(new GridLayout(0, 1, 0, 0));

		btnTopicAdd = new JButton(I18n.get().topicCreate());
		pnl_btn.add(btnTopicAdd);
		
		btnTopicEdit = new JButton(I18n.get().topicEdit());
		pnl_btn.add(btnTopicEdit);

		btnGradesRemove = new JButton(I18n.get().topicRemove());
		pnl_btn.add(btnGradesRemove);

		table = new JTable();
		scrollPane = new JScrollPane(table);
		panel.add(scrollPane, BorderLayout.CENTER);

		JPanel jpnl_btnbar = new JPanel();
		getContentPane().add(jpnl_btnbar, BorderLayout.SOUTH);
		jpnl_btnbar.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		btnClose = new JButton(I18n.get().componentButtonClose());
		jpnl_btnbar.add(btnClose);
	}
}
