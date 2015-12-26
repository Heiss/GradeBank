package de.netzmuffel.bank.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.structure.AbstractModelView;

import javax.swing.JTextField;

/**
 * The Class ViewStudent create the formular. Here you can create a student.
 */
public class ViewCourseGradesStudentAddEdit extends AbstractModelView {

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
	
	/** The lbl new label. */
	private JLabel lblNewLabel;
	
	/** The label_1. */
	private JLabel label_1;
	
	/** The label. */
	private JLabel label;

	/** The txt fld name. */
	public JTextField txtFldName;
	
	/** The txt fld fore name. */
	public JTextField txtFldForeName;
	
	/** The txt fld mail. */
	public JTextField txtFldMail;
	
	/** The txt fld tele. */
	public JTextField txtFldTele;

	/**
	 * Instantiates a new view student add edit.
	 */
	public ViewCourseGradesStudentAddEdit() {
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

		lblText = new JLabel(I18n.get().name());
		lblText.setHorizontalAlignment(SwingConstants.CENTER);
		jpnl_infos.add(lblText);

		txtFldName = new JTextField();
		jpnl_infos.add(txtFldName);
		txtFldName.setColumns(10);

		lblNewLabel = new JLabel(I18n.get().forename());
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jpnl_infos.add(lblNewLabel);

		txtFldForeName = new JTextField();
		jpnl_infos.add(txtFldForeName);
		txtFldForeName.setColumns(10);

		label = new JLabel(I18n.get().mail());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		jpnl_infos.add(label);

		txtFldMail = new JTextField();
		txtFldMail.setColumns(10);
		jpnl_infos.add(txtFldMail);

		label_1 = new JLabel(I18n.get().telephone());
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		jpnl_infos.add(label_1);

		txtFldTele = new JTextField();
		txtFldTele.setColumns(10);
		jpnl_infos.add(txtFldTele);
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
	}
}
