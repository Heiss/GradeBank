/*
 *
 */
package de.netzmuffel.bank.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.Course;
import de.netzmuffel.bank.model.CourseStudent;
import de.netzmuffel.bank.model.Student;
import de.netzmuffel.bank.model.enumerator.Error;
import de.netzmuffel.bank.model.structure.AbstractModelCtrl;
import de.netzmuffel.bank.tablemodel.TableModelShowStudents;
import de.netzmuffel.bank.view.ViewCourseStudentAddExists;

/**
 * The Class CtrlStudentAddExists.
 */
public class CtrlCourseStudentAddExists extends AbstractModelCtrl {

	/** The form. */
	ViewCourseStudentAddExists form;
	private TableModelShowStudents tableModel;
	private Course course;

	/**
	 * Instantiates a new ctrl student add exists.
	 *
	 * @param par
	 *            the par
	 * @param db
	 *            the db
	 * @param course
	 *            the course
	 * @param tableModel
	 *            the table model
	 */
	public CtrlCourseStudentAddExists(final AbstractModelCtrl par, Database db, Course course,
			TableModelShowStudents tableModel) {
		super(par, new ViewCourseStudentAddExists(), db);

		this.form = (ViewCourseStudentAddExists) this.formular;
		this.tableModel = tableModel;
		this.course = course;
		this.addListenersToComponents();

		ArrayList<Student> list = null;
		try {
			list = (ArrayList<Student>) db.getStudentDAO().queryForAll();
			List<CourseStudent> csList = db.getCourseStudentDAO().queryForEq("course_id", course);
			List<Student> remList = new ArrayList<Student>();
			for (CourseStudent courseStudent : csList) {
				remList.add(courseStudent.getStudent());
			}

			list.removeAll(remList);
			for (Student student : list) {
				this.form.comboBox.addItem(student);
			}

			if (list.size() == 0) {
				JOptionPane.showMessageDialog(null, I18n.get().errorNoStudentAvailable());
				this.hide();
			}
		} catch (SQLException e) {
			Error.COURSESTUDENT_ADDEXISTS_CANNOT_QUERY.showDialog(e);
			list = new ArrayList<Student>();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * de.netzmuffel.bank.model.structure.AbstractModelCtrl#setComponentsEnable(
	 * java.lang.Boolean)
	 */
	@Override
	public void setComponentsEnable(Boolean b) {

	}

	@Override
	public void addListenersToComponents() {
		this.form.jbtn_ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlCourseStudentAddExists.this.addSelectedStudentToCourse();
				CtrlCourseStudentAddExists.this.hide();
			}
		});
		this.form.jbtn_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (CtrlCourseStudentAddExists.this.bEnabled) {
					CtrlCourseStudentAddExists.this.hide();
				}
			}
		});
	}

	private void addSelectedStudentToCourse() {
		try {
			CourseStudent cs = new CourseStudent(this.course, (Student) this.form.comboBox.getSelectedItem());
			this.db.getCourseStudentDAO().create(cs);
			this.tableModel.refresh();
		} catch (SQLException e) {
			Error.COURSESTUDENT_ADDEXISTS_CREATE.showDialog(e);
		}
	}

}
