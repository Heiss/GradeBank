package de.netzmuffel.bank.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.Topic;
import de.netzmuffel.bank.model.structure.AbstractModelView;

public class ViewCourseGradesRemove extends AbstractModelView {
	private static final long serialVersionUID = 1L;
	
	/** The jpnl_layout. */
	private JPanel jpnl_layout;

	/** The jbtn_close. */
	public JButton jbtn_cancel;

	/** The jbtn_ok. */
	public 	JButton jbtn_ok;

	/** The panel. */
	public JPanel panel;
	public JLabel label;
	public JComboBox<Topic> combobxTopic;

	public ViewCourseGradesRemove() {
		super();

		this.setTitle(I18n.get().topicRemove());
		this.setSize(400, 100);
		this.getContentPane().setLayout(new BorderLayout());

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		jpnl_layout = new JPanel();
		jpnl_layout.setLayout(new BorderLayout(0, 0));

		JPanel jpnl_infos = new JPanel();
		jpnl_layout.add(jpnl_infos);
		jpnl_infos.setLayout(new GridLayout(0, 2, 0, 0));

		label = new JLabel(I18n.get().topicSelect());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		jpnl_infos.add(label);

		combobxTopic = new JComboBox<Topic>();
		jpnl_infos.add(combobxTopic);
		panel.add(jpnl_layout);

		JPanel jpnl_btnbar = new JPanel();
		getContentPane().add(jpnl_btnbar, BorderLayout.SOUTH);

		FlowLayout flowLayout = (FlowLayout) jpnl_btnbar.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);

		jbtn_ok = new JButton(I18n.get().componentButtonOk());
		jpnl_btnbar.add(jbtn_ok);

		jbtn_cancel = new JButton(I18n.get().componentButtonCancel());
		jpnl_btnbar.add(jbtn_cancel);

		jpnl_layout.setPreferredSize(new Dimension(300, jpnl_infos.getComponentCount() * 10));
	}
}
