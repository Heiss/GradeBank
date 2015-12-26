package de.netzmuffel.bank.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.structure.AbstractModelView;

import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;

/**
 * The Class ViewSettings create the formular for the settings.
 */
public class ViewSettings extends AbstractModelView {

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

	/** The combo box. */
	public JComboBox<String> comboBox;

	/** The panel. */
	public JPanel panel;

	/** The lbl text. */
	private JLabel lblText;

	/** The lbl new label. */
	private JLabel lblNewLabel;
	public JCheckBox chckbxEncryptPwd;

	/**
	 * Instantiates a new view settings.
	 */
	public ViewSettings() {
		super();
		this.setTitle(I18n.get().menuProperties());
		this.setSize(400, 400);
		this.getContentPane().setLayout(new BorderLayout());

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		jpnl_layout = new JPanel();
		jpnl_layout.setLayout(new BorderLayout(0, 0));

		JPanel jpnl_infos = new JPanel();
		jpnl_layout.add(jpnl_infos);
		jpnl_infos.setLayout(new GridLayout(0, 2, 0, 0));

		lblText = new JLabel(I18n.get().settingsLanguage());
		lblText.setHorizontalAlignment(SwingConstants.CENTER);
		jpnl_infos.add(lblText);

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { I18n.get().german(), I18n.get().english() }));
		jpnl_infos.add(comboBox);

		lblNewLabel = new JLabel(I18n.get().settingsEncryptEnable());
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jpnl_infos.add(lblNewLabel);

		chckbxEncryptPwd = new JCheckBox(I18n.get().settingsEncryptEnableCheckBox());
		jpnl_infos.add(chckbxEncryptPwd);
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
