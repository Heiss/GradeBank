/*
 *
 */
package de.netzmuffel.bank.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.Teacher;
import de.netzmuffel.bank.model.enumerator.Error;
import de.netzmuffel.bank.model.structure.AbstractModelCtrl;
import de.netzmuffel.bank.tablemodel.TableModelShowTeacher;
import de.netzmuffel.bank.view.ViewTeacherManager;

/**
 * The Class CtrlTeacherManager.
 */
public class CtrlTeacherManager extends AbstractModelCtrl {

	/** The form. */
	public ViewTeacherManager form;

	/** The table model. */
	public TableModelShowTeacher tableModel;

	/**
	 * Instantiates a new ctrl teacher manager.
	 *
	 * @param par
	 *            the par
	 * @param db
	 *            the db
	 */
	public CtrlTeacherManager(AbstractModelCtrl par, final Database db) {
		super(par, new ViewTeacherManager(), db);

		this.db = db;

		this.form = (ViewTeacherManager) this.formular;
		this.tableModel = new TableModelShowTeacher(db);
		this.form.table.setModel(this.tableModel);

		this.addListenersToComponents();
	}

	@Override
	public void addListenersToComponents() {
		this.form.jbtn_close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlTeacherManager.this.hide();
			}
		});

		this.form.btnTeacherCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CtrlTeacherAddEdit(CtrlTeacherManager.this.current, CtrlTeacherManager.this.db);
			}
		});

		this.form.btnTeacherEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlTeacherManager.this.openTeacherEdit();
			}
		});

		this.form.btnTeacherRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlTeacherManager.this.removeTeacher();
			}
		});
	}

	private void openTeacherEdit() {
		if (this.form.table.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, I18n.get().errorNoSelectionOnTable(I18n.get().teacher()));
			return;
		}
		new CtrlTeacherAddEdit(this.current, this.db,
				this.tableModel.getTeacher().get(this.form.table.getSelectedRow()));
	}

	private void removeTeacher() {
		if (this.form.table.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, I18n.get().errorNoSelectionOnTable(I18n.get().teacher()));
			return;
		}
		Teacher teacher = this.tableModel.getTeacher().get(this.form.table.getSelectedRow());
		int select = JOptionPane.showConfirmDialog(null,
				I18n.get().confirmRemoveTeacher(teacher.getName(), teacher.getForename()), I18n.get().confirmation(),
				JOptionPane.YES_NO_OPTION);
		if (select == JOptionPane.YES_OPTION) {
			try {
				this.db.getTeacherDAO().delete(teacher);
				this.tableModel.refresh();
			} catch (SQLException e) {
				Error.TEACHERMANAGER_TEACHER_REMOVE.showDialog(e);
			}
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
		this.form.btnTeacherCreate.setEnabled(b);
		this.form.btnTeacherEdit.setEnabled(b);
		this.form.jbtn_close.setEnabled(b);
		this.form.btnTeacherRemove.setEnabled(b);
	}

	public void openTeacherAdd() {
		new CtrlTeacherAddEdit(this.current, this.db);
	}

}
