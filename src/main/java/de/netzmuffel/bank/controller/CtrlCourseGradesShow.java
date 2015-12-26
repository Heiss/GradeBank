package de.netzmuffel.bank.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.model.Course;
import de.netzmuffel.bank.model.structure.AbstractModelCtrl;
import de.netzmuffel.bank.tablemodel.TableModelShowGrades;
import de.netzmuffel.bank.view.ViewCourseGradesShow;

/**
 * The Class CtrlGradesShow.
 */
public class CtrlCourseGradesShow extends AbstractModelCtrl {

	/** The form. */
	public ViewCourseGradesShow form;

	/** The table model. */
	public TableModelShowGrades tableModel;

	/**
	 * Instantiates a new ctrl grades show.
	 *
	 * @param par
	 *            the par
	 * @param db
	 *            the db
	 * @param course
	 *            the course
	 */
	public CtrlCourseGradesShow(AbstractModelCtrl par, final Database db, Course course) {
		super(par, new ViewCourseGradesShow(), db);

		this.db = db;
		this.form = (ViewCourseGradesShow) this.formular;
		this.addListenersToComponents();

		this.tableModel = new TableModelShowGrades(course, db);
		this.form.table.setModel(this.tableModel);
	}

	@Override
	public void addListenersToComponents() {
		this.form.btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlCourseGradesShow.this.hide();
			}
		});

		this.form.btnTopicAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CtrlCourseGradesTopicAddEdit(CtrlCourseGradesShow.this.current, CtrlCourseGradesShow.this.db);
			}
		});

		this.form.btnTopicEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CtrlCourseGradesTopicAddEdit(CtrlCourseGradesShow.this.current, CtrlCourseGradesShow.this.db, true);
			}
		});

		this.form.btnGradesRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CtrlCourseGradesTopicRemove(CtrlCourseGradesShow.this.current, CtrlCourseGradesShow.this.db);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * de.netzmuffel.bank.model.structure.AbstractModelCtrl#setComponentsEnable(
	 * java.lang.Boolean)
	 */
	@Override
	public void setComponentsEnable(Boolean b) {
		this.tableModel.setEnabled(b);
		this.form.btnGradesRemove.setEnabled(b);
		this.form.btnTopicAdd.setEnabled(b);
		this.form.btnTopicEdit.setEnabled(b);
	}

}
