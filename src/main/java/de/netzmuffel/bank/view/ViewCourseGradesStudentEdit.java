package de.netzmuffel.bank.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.structure.AbstractModelView;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ViewCourseGradesStudentEdit extends AbstractModelView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The jpnl_layout. */
	private JPanel jpnl_layout;

	/** The jbtn_close. */
	public JButton jbtn_cancel;

	/** The jbtn_ok. */
	public JButton jbtn_ok;

	/** The jbtn_apply. */
	public JButton jbtn_apply;

	/** The panel. */
	public JPanel panel;

	/** The lbl text. */
	private JLabel lblText;

	/** The txt fld name. */
	public JTextField txtFldName;
	private JScrollPane scrollPane;
	public JTable table;

	public ViewCourseGradesStudentEdit() {
		super();
		this.setTitle(I18n.get().student());
		this.setSize(600, 400);
		this.getContentPane().setLayout(new BorderLayout());

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		jpnl_layout = new JPanel();
		jpnl_layout.setLayout(new BorderLayout(0, 0));

		JPanel jpnl_infos = new JPanel();
		jpnl_layout.add(jpnl_infos);
		jpnl_infos.setLayout(new GridLayout(0, 2, 0, 0));

		lblText = new JLabel(I18n.get().student());
		lblText.setHorizontalAlignment(SwingConstants.CENTER);
		jpnl_infos.add(lblText);

		txtFldName = new JTextField();
		txtFldName.setEnabled(false);
		jpnl_infos.add(txtFldName);
		txtFldName.setColumns(10);
		panel.add(jpnl_layout);

		JPanel jpnl_btnbar = new JPanel();
		getContentPane().add(jpnl_btnbar, BorderLayout.SOUTH);

		FlowLayout flowLayout = (FlowLayout) jpnl_btnbar.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);

		jbtn_ok = new JButton(I18n.get().componentButtonOk());
		jpnl_btnbar.add(jbtn_ok);

		jbtn_cancel = new JButton(I18n.get().componentButtonCancel());
		jpnl_btnbar.add(jbtn_cancel);

		jbtn_apply = new JButton(I18n.get().componentButtonApply());
		jpnl_btnbar.add(jbtn_apply);

		jpnl_layout.setPreferredSize(new Dimension(300, jpnl_infos.getComponentCount() * 10));

		scrollPane = new JScrollPane();
		panel.add(scrollPane);

		table = new JTable();
		scrollPane.add(table);
	}
}
