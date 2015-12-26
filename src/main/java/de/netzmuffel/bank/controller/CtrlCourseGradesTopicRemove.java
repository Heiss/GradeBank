package de.netzmuffel.bank.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.Grade;
import de.netzmuffel.bank.model.Topic;
import de.netzmuffel.bank.model.enumerator.Error;
import de.netzmuffel.bank.model.structure.AbstractModelCtrl;
import de.netzmuffel.bank.view.ViewCourseGradesRemove;

public class CtrlCourseGradesTopicRemove extends AbstractModelCtrl {

	private ViewCourseGradesRemove form;

	public CtrlCourseGradesTopicRemove(AbstractModelCtrl par, Database db) {
		super(par, new ViewCourseGradesRemove(), db);

		this.form = (ViewCourseGradesRemove) this.formular;
		this.addListenersToComponents();

		this.fillChkBox();
	}

	private void fillChkBox() {
		for (Topic t : ((CtrlCourseGradesShow) this.parent).tableModel.topics) {
			this.form.combobxTopic.addItem(t);
		}
	}

	@Override
	public void setComponentsEnable(Boolean b) {

	}

	@Override
	public void addListenersToComponents() {
		this.form.jbtn_ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlCourseGradesTopicRemove.this.removeTopic();
			}
		});

		this.form.jbtn_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlCourseGradesTopicRemove.this.hide();
			}
		});
	}

	protected void removeTopic() {
		Topic t = (Topic) CtrlCourseGradesTopicRemove.this.form.combobxTopic.getSelectedItem();
		if (JOptionPane.showConfirmDialog(null, I18n.get().confirmRemoveTopic(t.getName()), I18n.get().topicRemove(),
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			try {
				CtrlCourseGradesTopicRemove.this.db.getTopicDAO().delete(t);

				for (Grade g : t.getGrades()) {
					CtrlCourseGradesTopicRemove.this.db.getGradeDAO().delete(g);
				}
			} catch (SQLException e) {
				Error.COURSEGRADESREMOVE_TOPIC_REMOVE.showDialog(e);
				return;
			}
		}
		((CtrlCourseGradesShow) CtrlCourseGradesTopicRemove.this.parent).tableModel.refresh();
		CtrlCourseGradesTopicRemove.this.hide();
	}

}
