/*
 *
 */
package de.netzmuffel.bank.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.Course;
import de.netzmuffel.bank.model.Teacher;
import de.netzmuffel.bank.model.enumerator.Error;
import de.netzmuffel.bank.model.enumerator.Subject;
import de.netzmuffel.bank.model.structure.AbstractModelCtrl;
import de.netzmuffel.bank.view.ViewCourse;

/**
 * The Class CtrlCourse controls the formular ViewCourse.
 */
public class CtrlCourseAddEdit extends AbstractModelCtrl {

	/** The form. */
	public ViewCourse form;

	/** The co. */
	public Course co = new Course();

	/**
	 * Instantiates a new ctrl course.
	 *
	 * @param par
	 *            the par
	 * @param db
	 *            the db
	 */
	public CtrlCourseAddEdit(AbstractModelCtrl par, Database db) {
		super(par, new ViewCourse(), db);
		this.form = (ViewCourse) this.formular;
		this.addListenersToComponents();

		this.fillComboBox();
	}

	/**
	 * Instantiates a new ctrl course.
	 *
	 * @param par
	 *            the par
	 * @param db
	 *            the db
	 * @param course
	 *            the course
	 */
	public CtrlCourseAddEdit(AbstractModelCtrl par, Database db, Course course) {
		super(par, new ViewCourse(), db);
		this.form = (ViewCourse) this.formular;
		this.addListenersToComponents();

		this.fillComboBox();

		this.co = course;

		if (this.co != null) {
			this.form.txtFldName.setText(this.co.getCourseName());
			this.form.comboBox_type.setSelectedIndex(this.co.isbHighschoolCourse() ? 1 : 0);
			this.form.comboBox_subject.setSelectedItem(I18n.getEnumText(this.co.getSubject()));
			this.form.comboBox_teacher.setSelectedItem(this.co.getTeacher());
		}
	}

	private void fillComboBox() {
		try {
			for (Teacher t : this.db.getTeacherDAO().queryForAll()) {
				this.form.comboBox_teacher.addItem(t);
			}
		} catch (SQLException e) {
			Error.COURSEADDEDIT_FILL_COMBOBOX.showDialog(e);
		}
	}

	/**
	 * Apply changes.
	 *
	 * @param db
	 *            the db
	 * @return true, if successful
	 */
	private boolean updateCourse(Database db) {
		if (this.form.txtFldName.getText().equals("")) {
			Error.GENERAL_NOTEXTINFIELD.showDialog();
			return false;
		} else {
			try {
				if ((this.co.getID() == 0
						&& db.getCourseDAO().queryForEq("courseName", this.form.txtFldName.getText()).size() > 0)
						|| (this.co.getID() != 0 && !this.form.txtFldName.getText().equals(this.co.getCourseName()))) {
					Error.COURSEADDEDIT_COURSE_ALREADY_EXIST.showDialog();
					return false;
				}
			} catch (SQLException e) {
				Error.COURSEADDEDIT_UPDATE_GET_COURSE.showDialog(e);
			}
		}

		this.co.setCourseName(this.form.txtFldName.getText());
		this.co.setSubject(Subject.values()[this.form.comboBox_subject.getSelectedIndex()]);
		this.co.setbHighschoolCourse((this.form.comboBox_type.getSelectedIndex() == 0) ? false : true);
		this.co.setTeacher((Teacher) this.form.comboBox_teacher.getSelectedItem());

		try {
			db.getCourseDAO().createOrUpdate(this.co);
		} catch (SQLException e) {
			Error.COURSEADDEDIT_CREATE_OR_UPDATE_COURSE.showDialog(e);
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * de.netzmuffel.bank.model.structure.AbstractModelCtrl#setComponentsEnable
	 * (java.lang.Boolean)
	 */
	@Override
	public void setComponentsEnable(Boolean b) {
	}

	@Override
	public void addListenersToComponents() {
		this.form.jbtn_cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((CtrlStart) CtrlCourseAddEdit.this.parent).form.jpnltab_classes.tableModel.refresh();
				CtrlCourseAddEdit.this.hide();
			}
		});

		this.form.jbtn_apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (CtrlCourseAddEdit.this.updateCourse(CtrlCourseAddEdit.this.db)) {
				}
			}
		});

		this.form.jbtn_ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (CtrlCourseAddEdit.this.updateCourse(CtrlCourseAddEdit.this.db)) {
					((CtrlStart) CtrlCourseAddEdit.this.parent).form.jpnltab_classes.tableModel.refresh();
					CtrlCourseAddEdit.this.hide();
				}
			}
		});
	}

}
