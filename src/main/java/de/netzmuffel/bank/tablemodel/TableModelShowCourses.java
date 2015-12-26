package de.netzmuffel.bank.tablemodel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.Course;
import de.netzmuffel.bank.model.enumerator.Error;

/**
 * The Class ShowCoursesTableModel represents the data structure behind a
 * jTable.
 */
public class TableModelShowCourses extends AbstractTableModel {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The courses. */
	private List<Course> courses;
	
	/** The db. */
	private Database db;

	/**
	 * Instantiates a new table model show courses.
	 *
	 * @param db the db
	 */
	public TableModelShowCourses(Database db) {
		this.db = db;
		courses = null;
		refresh();
	}

	/**
	 * Refresh.
	 */
	public void refresh() {
		try {
			this.courses = this.db.getCourseDAO().queryForAll();
		} catch (SQLException e) {
			Error.TABLEMODEL_SHOWCOURSES_REFRESH.showDialog();
			e.printStackTrace();
			
			this.courses = new ArrayList<Course>();
		}
		this.fireTableDataChanged();
	}

	/**
	 * Gets the courses.
	 *
	 * @return the courses
	 */
	public List<Course> getCourses() {
		return courses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return courses.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return 5;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		Course course = courses.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return course.getCourseName();
		case 1:
			return course.getTeacher().getName();
		case 2:
			return I18n.getEnumText(course.getSubject());
		case 3:
			if (!course.isbHighschoolCourse()) {
				return I18n.get().middleSchool();
			} else {
				return I18n.get().highSchool();
			}
		default:
			try {
				return db.getCourseStudentDAO().queryForEq("course_id", courses.get(rowIndex)).size();
			} catch (SQLException e) {
				e.printStackTrace();
				Error.TABLEMODEL_SHOWCOURSES_GETVALUEAT_STUDENTCOUNT.showDialog();
				
				return 0;
			}
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return I18n.get().course();
		case 1:
			return I18n.get().teacher();
		case 2:
			return I18n.get().subject();
		case 3:
			return I18n.get().courseType();
		default:
			return I18n.get().studentCount();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}
