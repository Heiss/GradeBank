package de.netzmuffel.bank.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.CourseStudent;
import de.netzmuffel.bank.model.Grade;
import de.netzmuffel.bank.model.enumerator.Error;
import de.netzmuffel.bank.model.structure.AbstractModelCtrl;
import de.netzmuffel.bank.view.ViewCourseGradesStudentEdit;

public class CtrlCourseGradesStudentEdit extends AbstractModelCtrl {

	private ViewCourseGradesStudentEdit form;
	private CourseStudent courseStudent;
	private Grade grades[];

	public CtrlCourseGradesStudentEdit(AbstractModelCtrl par, Database db, CourseStudent cs) {
		super(par, new ViewCourseGradesStudentEdit(), db);

		this.courseStudent = cs;
		this.form = (ViewCourseGradesStudentEdit) this.formular;

		this.addListenersToComponents();
		this.refreshGrades();

		this.form.txtFldName.setText(I18n.get().studentPrint(this.courseStudent.getStudent().getName(),
				this.courseStudent.getStudent().getForename()));
	}

	private void updateGrades() {
		// TODO
	}

	private void refreshGrades() {
		try {
			this.grades = this.db.getGradeDAO().queryForEq("coursestudent_id", this.courseStudent)
					.toArray(new Grade[0]);
		} catch (SQLException e) {
			Error.COURSEGRADESSTUDENTEDIT_GRADES_REFRESH.showDialog(e);

			this.grades = new Grade[0];
		}
	}

	@Override
	public void setComponentsEnable(Boolean b) {

	}

	@Override
	public void addListenersToComponents() {
		this.form.jbtn_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlCourseGradesStudentEdit.this.hide();
			}
		});

		this.form.jbtn_apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlCourseGradesStudentEdit.this.updateGrades();
			}
		});

		this.form.jbtn_ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlCourseGradesStudentEdit.this.updateGrades();
				CtrlCourseGradesStudentEdit.this.hide();
			}
		});
	}

}
