package de.netzmuffel.bank.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.j256.ormlite.stmt.DeleteBuilder;

import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.CourseStudent;
import de.netzmuffel.bank.model.Student;
import de.netzmuffel.bank.model.enumerator.Error;
import de.netzmuffel.bank.model.structure.AbstractModelCtrl;
import de.netzmuffel.bank.tablemodel.TableModelShowStudents;
import de.netzmuffel.bank.view.ViewStudentManager;

public class CtrlStudentManager extends AbstractModelCtrl {

	public ViewStudentManager form;

	public TableModelShowStudents tableModel;

	public CtrlStudentManager(AbstractModelCtrl par, Database db) {
		super(par, new ViewStudentManager(), db);
		this.form = (ViewStudentManager) this.formular;
		this.addListenersToComponents();

		this.tableModel = new TableModelShowStudents(db);
		this.form.table.setModel(this.tableModel);
	}

	@Override
	public void setComponentsEnable(Boolean b) {
		this.form.btnStudentCreate.setEnabled(b);
		this.form.btnStudentEdit.setEnabled(b);
		this.form.btnStudentRemove.setEnabled(b);
		this.form.jbtn_close.setEnabled(b);
	}

	@Override
	public void addListenersToComponents() {
		this.form.jbtn_close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlStudentManager.this.hide();
			}
		});
		this.form.btnStudentCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CtrlCourseStudentAddEdit(CtrlStudentManager.this.current, CtrlStudentManager.this.db,
						CtrlStudentManager.this.tableModel);
			}
		});
		this.form.btnStudentEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlStudentManager.this.openStudentEdit();
			}
		});
		this.form.btnStudentRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlStudentManager.this.removeSelectedStudent();
			}
		});
	}

	public void openStudentEdit() {
		if (this.form.table.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, I18n.get().errorNoSelectionOnTable(I18n.get().student()));
			return;
		}
		new CtrlCourseStudentAddEdit(this.current, this.db,
				this.tableModel.students.get(this.form.table.getSelectedRow()), this.tableModel);
	}

	private void removeSelectedStudent() {
		if (this.form.table.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, I18n.get().errorNoSelectionOnTable(I18n.get().student()));
			return;
		}
		Student student = this.tableModel.students.get(this.form.table.getSelectedRow());

		int iRemove = JOptionPane.showConfirmDialog(null,
				I18n.get().confirmRemoveStudentFromCourse(student.getName(), student.getForename()),
				I18n.get().confirmation(), JOptionPane.YES_NO_OPTION);

		if (iRemove == JOptionPane.OK_OPTION) {
			try {
				DeleteBuilder<CourseStudent, Integer> rb = this.db.getCourseStudentDAO().deleteBuilder();
				rb.where().eq("student_id", student);
				rb.delete();

				DeleteBuilder<Student, Integer> rb2 = this.db.getStudentDAO().deleteBuilder();
				rb2.where().equals(this.tableModel.students.get(this.form.table.getSelectedRow()));
				rb2.delete();
			} catch (SQLException e) {
				Error.STUDENTMANAGER_DELETE_STUDENT.showDialog(e);
			}
			this.tableModel.refresh();
		}
	}
}
