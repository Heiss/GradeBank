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
import de.netzmuffel.bank.model.Teacher;
import de.netzmuffel.bank.model.enumerator.Subject;
import de.netzmuffel.bank.model.structure.AbstractModelView;

import javax.swing.JComboBox;

/**
 * The Class ViewCourse create the formular, where you can add a new course.
 */
public class ViewCourse extends AbstractModelView {

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
	
	/** The label. */
	private JLabel label;

	/** The txt fld name. */
	public JTextField txtFldName;
	
	/** The combo box_type. */
	public JComboBox<String> comboBox_type;
	
	/** The combo box_subject. */
	public JComboBox<String> comboBox_subject;
	
	/** The label_1. */
	private JLabel label_1;
	
	/** The combo box_teacher. */
	public JComboBox<Teacher> comboBox_teacher;

	/**
	 * Instantiates a new view course.
	 */
	public ViewCourse() {
		super();
		this.setTitle(I18n.get().course());
		this.setSize(320, 250);
		this.getContentPane().setLayout(new BorderLayout());

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		jpnl_layout = new JPanel();
		jpnl_layout.setLayout(new BorderLayout(0, 0));

		JPanel jpnl_infos = new JPanel();
		jpnl_layout.add(jpnl_infos);
		jpnl_infos.setLayout(new GridLayout(0, 2, 0, 0));

		lblText = new JLabel(I18n.get().coursename());
		lblText.setHorizontalAlignment(SwingConstants.CENTER);
		jpnl_infos.add(lblText);

		txtFldName = new JTextField();
		jpnl_infos.add(txtFldName);
		txtFldName.setColumns(10);

		lblNewLabel = new JLabel(I18n.get().subject());
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jpnl_infos.add(lblNewLabel);

		comboBox_subject = new JComboBox<String>();
		for (Subject subj : Subject.values()) {
			comboBox_subject.addItem(I18n.getEnumText(subj));
		}
		jpnl_infos.add(comboBox_subject);

		label = new JLabel(I18n.get().courseType());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		jpnl_infos.add(label);

		comboBox_type = new JComboBox<String>();
		comboBox_type.addItem(I18n.get().middleSchool());
		comboBox_type.addItem(I18n.get().highSchool());
		jpnl_infos.add(comboBox_type);

		label_1 = new JLabel(I18n.get().teacher());
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		jpnl_infos.add(label_1);

		comboBox_teacher = new JComboBox<Teacher>();
		jpnl_infos.add(comboBox_teacher);
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
