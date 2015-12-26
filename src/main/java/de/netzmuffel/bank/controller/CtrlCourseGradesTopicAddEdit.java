package de.netzmuffel.bank.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.model.Topic;
import de.netzmuffel.bank.model.enumerator.Error;
import de.netzmuffel.bank.model.structure.AbstractModelCtrl;
import de.netzmuffel.bank.view.ViewCourseGradesTopicAddEdit;

public class CtrlCourseGradesTopicAddEdit extends AbstractModelCtrl {

	public ViewCourseGradesTopicAddEdit form;

	public CtrlCourseGradesTopicAddEdit(AbstractModelCtrl par, Database db) {
		super(par, new ViewCourseGradesTopicAddEdit(), db);
		this.form = (ViewCourseGradesTopicAddEdit) this.formular;
		this.addListenersToComponents();
	}

	public CtrlCourseGradesTopicAddEdit(AbstractModelCtrl current, Database db, boolean b) {
		this(current, db);
		this.form.combobxTopic.setVisible(true);
		this.form.label.setVisible(true);

		for (Topic t : ((CtrlCourseGradesShow) this.parent).tableModel.topics) {
			this.form.combobxTopic.addItem(t);
		}
	}

	@Override
	public void setComponentsEnable(Boolean b) {
	}

	@Override
	public void addListenersToComponents() {
		this.form.jbtn_apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlCourseGradesTopicAddEdit.this.saveTopic();
			}
		});

		this.form.jbtn_ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlCourseGradesTopicAddEdit.this.saveTopic();
				CtrlCourseGradesTopicAddEdit.this.hide();
			}
		});

		this.form.jbtn_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlCourseGradesTopicAddEdit.this.hide();
			}
		});

		this.form.combobxTopic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Topic topic = (Topic) CtrlCourseGradesTopicAddEdit.this.form.combobxTopic.getSelectedItem();
				CtrlCourseGradesTopicAddEdit.this.form.txtFldName.setText(topic.getName());
			}
		});
	}

	protected void saveTopic() {
		if (this.form.txtFldName.getText().isEmpty()) {
			Error.GENERAL_NOTEXTINFIELD.showDialog();
			return;
		}

		Topic topic = new Topic();
		if (this.form.combobxTopic.isVisible()) {
			topic = (Topic) this.form.combobxTopic.getSelectedItem();
		}
		topic.setName(this.form.txtFldName.getText());

		try {
			this.db.getTopicDAO().createOrUpdate(topic);
		} catch (SQLException e) {
			Error.COURSEGRADESTOPICADDEDIT_TOPIC_CREATE_OR_UPDATE.showDialog(e);
		}
		// TODO fix this bug with this shitty repaint fucker column name bug!
		// ((CtrlCourseGradesShow)parent).form.scrollPane.repaint();
	}

}
