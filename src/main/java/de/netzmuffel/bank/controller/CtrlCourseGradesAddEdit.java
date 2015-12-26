/*
 * 
 */
package de.netzmuffel.bank.controller;

import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.model.Grade;
import de.netzmuffel.bank.model.structure.AbstractModelCtrl;
import de.netzmuffel.bank.view.ViewCourseGradesAddEdit;

/**
 * The Class CtrlGradesAddEdit.
 */
public class CtrlCourseGradesAddEdit extends AbstractModelCtrl {

	/**
	 * Instantiates a new ctrl grades add edit.
	 *
	 * @param par
	 *            the par
	 * @param db
	 *            the db
	 */
	public CtrlCourseGradesAddEdit(AbstractModelCtrl par, Database db) {
		super(par, new ViewCourseGradesAddEdit(), db);

	}

	/**
	 * Instantiates a new ctrl grades add edit.
	 *
	 * @param par
	 *            the par
	 * @param db
	 *            the db
	 * @param grade
	 *            the grade
	 */
	public CtrlCourseGradesAddEdit(AbstractModelCtrl par, Database db, Grade grade) {
		this(par, db);
		addListenersToComponents();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.netzmuffel.bank.model.structure.AbstractModelCtrl#setComponentsEnable(
	 * java.lang.Boolean)
	 */
	@Override
	public void setComponentsEnable(Boolean b) {

	}

	@Override
	public void addListenersToComponents() {

	}

}
