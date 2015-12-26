/*
 *
 */
package de.netzmuffel.bank.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.model.Student;
import de.netzmuffel.bank.model.enumerator.Error;
import de.netzmuffel.bank.model.structure.AbstractModelCtrl;
import de.netzmuffel.bank.tablemodel.TableModelShowStudents;
import de.netzmuffel.bank.view.ViewCourseGradesStudentAddEdit;

/**
 * The Class CtrlStudent controls the formular ViewStudent.s
 */
public class CtrlCourseStudentAddEdit extends AbstractModelCtrl {

	/** The form. */
	private ViewCourseGradesStudentAddEdit form;
	private Student student;
	private TableModelShowStudents tableModel;

	/**
	 * Show a formular, where you can create a new student.
	 *
	 * @param par
	 *            the par
	 * @param db
	 *            the db
	 */
	public CtrlCourseStudentAddEdit(AbstractModelCtrl par, Database db, TableModelShowStudents tableModel) {
		super(par, new ViewCourseGradesStudentAddEdit(), db);
		this.form = (ViewCourseGradesStudentAddEdit) this.formular;
		this.tableModel = tableModel;
		this.addListenersToComponents();
	}

	/**
	 * Instantiates a new ctrl student add edit.
	 *
	 * @param par
	 *            the par
	 * @param db
	 *            the db
	 * @param student
	 *            the student
	 * @param tableModel
	 */
	public CtrlCourseStudentAddEdit(AbstractModelCtrl par, Database db, Student student,
			TableModelShowStudents tableModel) {
		this(par, db, tableModel);

		this.student = student;

		this.form.txtFldName.setText(student.getName());
		this.form.txtFldForeName.setText(student.getForename());
		this.form.txtFldMail.setText(student.getMail());
		this.form.txtFldTele.setText(student.getTelephone());
	}

	private void updateStudent() {
		this.student.setForename(this.form.txtFldForeName.getText());
		this.student.setName(this.form.txtFldName.getText());
		this.student.setMail(this.form.txtFldMail.getText());
		this.student.setTelephone(this.form.txtFldTele.getText());

		try {
			this.db.getStudentDAO().createOrUpdate(this.student);
		} catch (SQLException e) {
			Error.COURSESTUDENT_EDIT_CREATE_OR_UPDATE.showDialog(e);
		}

		this.tableModel.refresh();
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
				if (CtrlCourseStudentAddEdit.this.bEnabled) {
					CtrlCourseStudentAddEdit.this.hide();
				}
			}
		});

		this.form.jbtn_ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlCourseStudentAddEdit.this.updateStudent();
				CtrlCourseStudentAddEdit.this.hide();
			}
		});

		this.form.jbtn_apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlCourseStudentAddEdit.this.updateStudent();
			}
		});
	}

}
