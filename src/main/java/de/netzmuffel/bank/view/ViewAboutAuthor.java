package de.netzmuffel.bank.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.structure.AbstractModelView;

/**
 * The Class ViewHelp create the formular for the help links and informations.
 */
public class ViewAboutAuthor extends AbstractModelView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The jbtn_close. */
	public JButton jbtn_close;

	/** The jpnl_layout. */
	private JPanel jpnl_layout;

	/**
	 * Instantiates a new view help.
	 *
	 * @wbp.parser.entryPoint
	 */
	public ViewAboutAuthor() {
		super();
		this.setTitle(I18n.get().menuAboutAuthor());
		this.setSize(250, 400);
		this.getContentPane().setLayout(new BorderLayout());

		jbtn_close = new JButton("Close");
		jpnl_layout = new JPanel();
		jpnl_layout.setLayout(new BorderLayout(0, 0));

		JPanel jpnl_infos = new JPanel();
		jpnl_layout.add(jpnl_infos);
		jpnl_infos.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel lblText = new JLabel("<html><body style=\"width:250\">\r\n<p "
				+ "style=\"padding: 10px\">\r\nProgrammer : Peter \"PhysicX\" H.<br>\r\n"
				+ "Mail: physicx.project@gmail.com<br>\r\nWebsite: www.netzmuffel.de\r\n"
				+ "</p>\r\n<hr>\r\n\r\nGood guy greg program!\r\n</body></html>");
		lblText.setVerticalAlignment(SwingConstants.TOP);
		lblText.setHorizontalAlignment(SwingConstants.CENTER);
		jpnl_infos.add(lblText);

		JPanel jpnl_btnbar = new JPanel();
		jpnl_layout.add(jpnl_btnbar, BorderLayout.SOUTH);

		jbtn_close = new JButton("Close");
		jpnl_btnbar.add(jbtn_close);

		this.getContentPane().add(jpnl_layout);
	}
}
