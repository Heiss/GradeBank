package de.netzmuffel.bank.tablemodel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.Course;
import de.netzmuffel.bank.model.CourseStudent;
import de.netzmuffel.bank.model.Student;
import de.netzmuffel.bank.model.enumerator.Error;

/**
 * The Class ShowStudentsTableModel represents the data structure behind a
 * jTable.
 */
public class TableModelShowStudents extends AbstractTableModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The db. */
	private Database db;

	/** The students. */
	public List<CourseStudent> courseStudents = new ArrayList<CourseStudent>();
	public List<Student> students = new ArrayList<Student>();

	/** The course. */
	private Course course = null;

	public TableModelShowStudents(Database db) {
		this.db = db;
		refresh();
	}

	/**
	 * Instantiates a new table model show students.
	 *
	 * @param db
	 *            the db
	 * @param course
	 *            the course
	 */
	public TableModelShowStudents(Database db, Course course) {
		this.db = db;
		this.course = course;
		refresh();
	}

	/**
	 * Refresh.
	 */
	public void refresh() {
		try {
			if (course != null) {
				courseStudents = this.db.getCourseStudentDAO().queryForEq("course_id", course);
				for (Iterator<CourseStudent> iterator = courseStudents.iterator(); iterator.hasNext();) {
					CourseStudent courseStudent = (CourseStudent) iterator.next();
					students.add(courseStudent.getStudent());
				}
			} else {
				students = this.db.getStudentDAO().queryForAll();
			}
		} catch (SQLException e) {
			Error.TABLEMODEL_SHOWSTUDENTS_REFRESH.showDialog();
			e.printStackTrace();
			
			courseStudents = new ArrayList<CourseStudent>();
			students = new ArrayList<Student>();
		}
		this.fireTableDataChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return students.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return 4;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		Student student = students.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return student.getName();
		case 1:
			return student.getForename();
		case 2:
			return student.getMail();
		default:
			return student.getTelephone();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return I18n.get().name();
		case 1:
			return I18n.get().forename();
		case 2:
			return I18n.get().mail();
		default:
			return I18n.get().telephone();
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
