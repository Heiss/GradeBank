package de.netzmuffel.bank.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.structure.AbstractModelView;

public class ViewAboutProgram extends AbstractModelView {
	private static final long serialVersionUID = 1L;

	/** The jbtn_close. */
	public JButton jbtn_close;

	/** The jpnl_layout. */
	private JPanel jpnl_layout;

	public JLabel lblOffVersion;
	public JLabel lblWebVersion;
	public JLabel lblUpdate;
	public JButton btnUpdate;

	public ViewAboutProgram() {
		super();
		this.setTitle(I18n.get().menuAboutProgram());
		this.setSize(250, 200);
		this.getContentPane().setLayout(new BorderLayout());

		jbtn_close = new JButton("Close");
		jpnl_layout = new JPanel();
		jpnl_layout.setLayout(new BorderLayout(0, 0));

		JPanel jpnl_infos = new JPanel();
		jpnl_layout.add(jpnl_infos);
		jpnl_infos.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblWebVersion = new JLabel("webversion");
		lblWebVersion.setVerticalAlignment(SwingConstants.TOP);
		lblWebVersion.setHorizontalAlignment(SwingConstants.CENTER);
		jpnl_infos.add(lblWebVersion);
		
		lblOffVersion = new JLabel("localversion");
		lblOffVersion.setVerticalAlignment(SwingConstants.TOP);
		lblOffVersion.setHorizontalAlignment(SwingConstants.CENTER);
		jpnl_infos.add(lblOffVersion);
		
		lblUpdate = new JLabel("please update");
		lblUpdate.setVerticalAlignment(SwingConstants.TOP);
		lblUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		jpnl_infos.add(lblUpdate);
		
		btnUpdate = new JButton(I18n.get().btnUpdate());
		jpnl_infos.add(btnUpdate);

		JPanel jpnl_btnbar = new JPanel();
		jpnl_layout.add(jpnl_btnbar, BorderLayout.SOUTH);

		jbtn_close = new JButton("Close");
		jpnl_btnbar.add(jbtn_close);

		this.getContentPane().add(jpnl_layout);
	}
}
