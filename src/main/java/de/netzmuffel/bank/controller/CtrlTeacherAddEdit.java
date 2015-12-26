/*
 *
 */
package de.netzmuffel.bank.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import de.netzmuffel.bank.Config;
import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.model.Teacher;
import de.netzmuffel.bank.model.enumerator.Error;
import de.netzmuffel.bank.model.structure.AbstractModelCtrl;
import de.netzmuffel.bank.view.ViewTeacherAddEdit;

/**
 * The Class CtrlTeacherAddEdit.
 */
public class CtrlTeacherAddEdit extends AbstractModelCtrl {

	/** The form. */
	private ViewTeacherAddEdit form;

	/** The teacher. */
	private Teacher teacher = new Teacher();

	/**
	 * Instantiates a new ctrl teacher add edit.
	 *
	 * @param par
	 *            the par
	 * @param db
	 *            the db
	 */
	public CtrlTeacherAddEdit(AbstractModelCtrl par, Database db) {
		super(par, new ViewTeacherAddEdit(), db);

		this.form = (ViewTeacherAddEdit) this.formular;
		this.addListenersToComponents();
	}

	/**
	 * Instantiates a new ctrl teacher add edit.
	 *
	 * @param par
	 *            the par
	 * @param db
	 *            the db
	 * @param teacher
	 *            the teacher
	 */
	public CtrlTeacherAddEdit(AbstractModelCtrl par, Database db, Teacher teacher) {
		this(par, db);

		this.teacher = teacher;
		this.form.txtFldName.setText(this.teacher.getName());
		this.form.txtFldForeName.setText(this.teacher.getForename());
		this.form.txtFldMail.setText(this.teacher.getMail());
		this.form.txtFldTele.setText(this.teacher.getTelephone());
	}

	/**
	 * Update teacher.
	 */
	protected void updateTeacher() {
		this.teacher.setForename(this.form.txtFldForeName.getText());
		this.teacher.setName(this.form.txtFldName.getText());
		this.teacher.setMail(this.form.txtFldMail.getText());
		this.teacher.setTelephone(this.form.txtFldTele.getText());

		try {
			this.db.getTeacherDAO().createOrUpdate(this.teacher);
		} catch (SQLException e) {
			Config.logger.error("There was an error in the CreateOrUpdate for the teacher {}", this.teacher);
			Error.TEACHERADDEDIT_CREATEORUPDATE.showDialog(e);
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
		this.form.jbtn_apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlTeacherAddEdit.this.updateTeacher();
			}
		});

		this.form.jbtn_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((CtrlTeacherManager) CtrlTeacherAddEdit.this.parent).tableModel.refresh();
				CtrlTeacherAddEdit.this.hide();
			}
		});

		this.form.jbtn_ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlTeacherAddEdit.this.updateTeacher();
				((CtrlTeacherManager) CtrlTeacherAddEdit.this.parent).tableModel.refresh();
				CtrlTeacherAddEdit.this.hide();
			}
		});
	}

}
