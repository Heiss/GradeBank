/*
 *
 */
package de.netzmuffel.bank.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.netzmuffel.bank.Config;
import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.Course;
import de.netzmuffel.bank.model.enumerator.Error;
import de.netzmuffel.bank.model.enumerator.Shutdown;
import de.netzmuffel.bank.model.structure.AbstractModelCtrl;
import de.netzmuffel.bank.tabbedpanel.ViewTabStudentsFromCourse;
import de.netzmuffel.bank.view.ViewStart;

/**
 * The Class CtrlStart controls the formular ViewStart.
 */
public class CtrlStart extends AbstractModelCtrl {

	/** The form. */
	public ViewStart form;

	/**
	 * Instantiates a new ctrl start.
	 *
	 * @param par
	 *            the par
	 * @param db
	 *            the db
	 */
	public CtrlStart(final AbstractModelCtrl par, final Database db) {
		super(par, new ViewStart(db), db);
		this.form = (ViewStart) this.formular;
		this.addListenersToComponents();

		this.formular.removeWindowListener(this.formular.getWindowListeners()[0]);
		this.formular.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.checkTeacherExists();
	}

	private void checkTeacherExists() {
		try {
			if (this.db.getTeacherDAO().queryForAll().size() == 0) {
				if (JOptionPane.showConfirmDialog(null, I18n.get().noTeacherFound(), I18n.get().titleTeacherNotFound(),
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					(new CtrlTeacherManager(this.current, this.db)).openTeacherAdd();
				}
			}
		} catch (SQLException e) {
			Config.logger.error("There was an error in the query for the teacher.");
			Error.START_CHECK_TEACHER_EXISTS.showDialog(e);
		}
	}

	@Override
	public void addListenersToComponents() {
		this.formular.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				CtrlStart.this.hide();
				Config.shutdown(Shutdown.Okay, CtrlStart.this.db);
			}
		});

		this.form.mntmClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlStart.this.hide();
				Config.shutdown(Shutdown.Okay, CtrlStart.this.db);
			}
		});

		this.form.mntmAboutAuthor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (CtrlStart.this.bEnabled) {
					new CtrlAboutAuthor(CtrlStart.this.current, CtrlStart.this.db);
				}
			}
		});

		this.form.mntmAboutProgram.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (CtrlStart.this.bEnabled) {
					new CtrlAboutProgram(CtrlStart.this.current, CtrlStart.this.db);
				}
			}
		});

		this.form.mntmGeneralSettings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (CtrlStart.this.bEnabled) {
					new CtrlSettings(CtrlStart.this.current, CtrlStart.this.db);
				}
			}
		});

		this.form.mntmStudentManage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CtrlStudentManager(CtrlStart.this.current, CtrlStart.this.db);
			}
		});

		this.form.mntmTeacherManage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CtrlTeacherManager(CtrlStart.this.current, CtrlStart.this.db);
			}
		});

		this.form.jpnltab_classes.btnShowClass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlStart.this.openTabForSelectedCourse();
			}
		});

		this.form.jpnltab_classes.btnNewClass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CtrlCourseAddEdit(CtrlStart.this.current, CtrlStart.this.db);
			}
		});

		this.form.jpnltab_classes.btnEditClass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlStart.this.openCtrlCourse();
			}
		});

		this.form.jpnltab_classes.btnDeleteClass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlStart.this.deleteSelectedCourse();
			}
		});

		this.form.mntmDeleteDatas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CtrlStart.this.deleteAllDatas();
			}
		});

	}

	private void deleteAllDatas() {
		int ans = JOptionPane.showConfirmDialog(null, I18n.get().confirmDataDeletion(), I18n.get().confirmation(),
				JOptionPane.YES_NO_OPTION);

		if (ans == JOptionPane.YES_OPTION) {
			if (this.deleteAllDatabases()) {
				JOptionPane.showMessageDialog(null, I18n.get().ConfirmAllDeleted());
				new CtrlStart(null, this.db);
				this.hide();
			}
		}
	}

	private boolean deleteAllDatabases() {
		if (this.db.deleteDatabaseFile()) {
			try {
				this.db = new Database();
				return true;
			} catch (SQLException e) {
				Error.APPLICATION_DATABASE_CONNECT_NEW_DATABASE.showDialog(e);
			}
		}
		return false;
	}

	private void openCtrlCourse() {
		if (this.form.jpnltab_classes.table.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, I18n.get().errorNoSelectionOnTable(I18n.get().course()));
			return;
		}

		Course course = this.form.jpnltab_classes.tableModel.getCourses()
				.get(this.form.jpnltab_classes.table.getSelectedRow());
		new CtrlCourseAddEdit(this.current, this.db, course);
	}

	private void deleteSelectedCourse() {
		if (this.form.jpnltab_classes.table.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, I18n.get().errorNoSelectionOnTable(I18n.get().course()));
			return;
		}

		Course course = this.form.jpnltab_classes.tableModel.getCourses()
				.get(this.form.jpnltab_classes.table.getSelectedRow());
		int select = JOptionPane
				.showConfirmDialog(null,
						I18n.get().confirmRemoveCourse(course.getCourseName(), course.getTeacher().getName(),
								I18n.getEnumText(course.getSubject())),
						I18n.get().confirmation(), JOptionPane.YES_NO_OPTION);
		if (select == JOptionPane.YES_OPTION) {
			try {
				this.db.getCourseDAO().delete(course);
			} catch (SQLException e) {
				Error.START_DELETE_SELECTED_COURSE.showDialog(e);
			}
		}
		this.form.jpnltab_classes.tableModel.refresh();
	}

	private void openTabForSelectedCourse() {
		ViewTabStudentsFromCourse jpnltab_class;

		int selectedItem = this.form.jpnltab_classes.table.getSelectedRow();
		if (selectedItem == -1) {
			JOptionPane.showMessageDialog(null, I18n.get().errorNoSelectionOnTable(I18n.get().course()));
			return;
		}
		try {
			Course course = null;
			course = this.db.getCourseDAO().queryForAll().get(selectedItem);
			String tabName = I18n.get().course() + " " + course.getCourseName();

			// check the tab-names, maybe the class is aldready opened
			boolean isTabNotAlreadyOpened = true;
			for (int i = 0; i < this.form.tabbedPane.getTabCount(); i++) {
				if (this.form.tabbedPane.getTitleAt(i).equals(tabName)) {
					isTabNotAlreadyOpened = false;
				}
			}

			if (isTabNotAlreadyOpened) {
				jpnltab_class = new ViewTabStudentsFromCourse(this.db, course, this.current);
				this.form.tabbedPane.addTab(tabName, null, jpnltab_class, null);
				this.form.tabbedPane.setSelectedIndex(this.form.tabbedPane.getTabCount() - 1);
			} else {
				JOptionPane.showMessageDialog(null, I18n.get().errorCourseAlreadyOpened());
			}
		} catch (SQLException e) {
			Error.START_OPEN_TAB_FOR_SELECTED_COURSE.showDialog(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * de.netzmuffel.bank.model.structure.AbstractModelCtrl#setComponentsEnable
	 * (java.lang.Boolean)
	 */
	@Override
	public void setComponentsEnable(Boolean b) {
		this.form.mnHelp.setEnabled(b);
		this.form.mnFile.setEnabled(b);
		this.form.mnManage.setEnabled(b);
		this.form.mnSettings.setEnabled(b);

		this.form.mntmAboutAuthor.setEnabled(b);
		this.form.mntmAboutProgram.setEnabled(b);
		this.form.mntmClose.setEnabled(b);
		this.form.mntmGeneralSettings.setEnabled(b);
		this.form.mntmCloudSettings.setEnabled(b);
		this.form.mntmDeleteDatas.setEnabled(b);
		this.form.mntmExportDatas.setEnabled(b);
		this.form.mntmImportDatas.setEnabled(b);

		this.form.jpnltab_classes.setEnabled(b);
		this.form.jpnltab_classes.btnDeleteClass.setEnabled(b);
		this.form.jpnltab_classes.btnEditClass.setEnabled(b);
		this.form.jpnltab_classes.btnNewClass.setEnabled(b);
		this.form.jpnltab_classes.btnShowClass.setEnabled(b);

		for (int i = 1; i < this.form.tabbedPane.getTabCount(); i++) {
			((ViewTabStudentsFromCourse) this.form.tabbedPane.getComponentAt(i)).setEnabled(this.bEnabled);
		}
	}
}