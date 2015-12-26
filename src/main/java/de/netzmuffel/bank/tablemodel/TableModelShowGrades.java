package de.netzmuffel.bank.tablemodel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.Course;
import de.netzmuffel.bank.model.CourseStudent;
import de.netzmuffel.bank.model.Grade;
import de.netzmuffel.bank.model.Student;
import de.netzmuffel.bank.model.Topic;
import de.netzmuffel.bank.model.enumerator.Error;

/**
 * The Class TableModelShowGrades.
 */
public class TableModelShowGrades extends AbstractTableModel {

	/** The b enabled. */
	private Boolean bEnabled;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The db. */
	private Database db;

	/** The course. */
	public Course course;

	/** The course students. */
	public List<CourseStudent> courseStudents;

	/** The topics. */
	public ArrayList<Topic> topics = new ArrayList<Topic>();

	/**
	 * Instantiates a new table model show grades.
	 *
	 * @param course
	 *            the course
	 * @param db
	 *            the db
	 */
	public TableModelShowGrades(Course course, Database db) {
		this.db = db;
		this.course = course;
		this.bEnabled = true;

		refresh();
	}

	/**
	 * Refresh.
	 */
	public void refresh() {
		try {
			refreshCourseStudents();
			refreshTopics();
		} catch (SQLException e) {
			Error.TABLEMODEL_SHOWGRADES_REFRESH.showDialog();
			e.printStackTrace();
			
			courseStudents = new ArrayList<CourseStudent>();
		}
		this.fireTableDataChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return topics.size() + 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return courseStudents.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			Student student = courseStudents.get(rowIndex).getStudent();
			return I18n.get().studentPrint(student.getName(), student.getForename());
		} else {
			int point = getGrade(rowIndex, columnIndex).getPoint();
			return (point >= 0) ? point : I18n.get().studentNotEvaluate();
		}
	}

	/**
	 * Gets the grade.
	 *
	 * @param rowIndex
	 *            the row index
	 * @param columnIndex
	 *            the column index
	 * @return the grade
	 */
	public Grade getGrade(int rowIndex, int columnIndex) {
		try {
			List<Grade> g = db.getGradeDAO().queryBuilder().where().eq("courseStudent_id", courseStudents.get(rowIndex))
					.and().eq("topic_id", topics.get(columnIndex - 1)).query();
			if (!g.isEmpty()) {
				return g.get(0);
			} else {
				return new Grade();
			}
		} catch (SQLException e) {
			Error.TABLEMODEL_SHOWGRADES_GETGRADE_QUERY.showDialog();
			e.printStackTrace();
			return new Grade();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		if (column == 0) {
			return I18n.get().student();
		} else {
			return topics.get(column - 1).getName();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object,
	 * int, int)
	 */
	@Override
	public void setValueAt(Object val, int rowIndex, int columnIndex) {
		if (!bEnabled) {
			return;
		}

		String value = val.toString();
		if (columnIndex == 0) {
			try {
				Topic top = topics.get(rowIndex);
				top.setName(value);
				db.getTopicDAO().update(top);
			} catch (SQLException e) {
				Error.TABLEMODEL_SHOWGRADES_SETVALUEAT_TOPIC_UPDATE.showDialog();
				e.printStackTrace();
			}
		} else {
			Grade grade = getGrade(rowIndex, columnIndex);

			if (value.equals(I18n.get().gradeNotValued()) || Integer.valueOf(value) < 0) {
				grade.setPoint(-1);
			} else {
				try {
					grade.setPoint(Integer.parseInt(value));
				} catch (NumberFormatException e) {
					Error.TABLEMODEL_SHOWGRADES_SETVALUEAT_NOVALIDVALUE.showDialog();
				}
			}

			try {
				db.getGradeDAO().update(grade);
			} catch (SQLException e) {
				Error.TABLEMODEL_SHOWGRADES_SETVALUEAT_GRADE_UPDATE.showDialog();
				e.printStackTrace();
			}
		}
		refresh();
	}

	/**
	 * Refresh course students.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	private void refreshCourseStudents() throws SQLException {
		this.courseStudents = db.getCourseStudentDAO().queryBuilder().orderBy("student_id", true).where()
				.eq("course_id", course).query();
	}

	/**
	 * Refresh topics.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	private void refreshTopics() throws SQLException {
		HashSet<Topic> to = new HashSet<Topic>();
		for (CourseStudent courseStudent : courseStudents) {
			for (Grade grade : db.getGradeDAO().queryBuilder().groupBy("topic_id").selectColumns("topic_id").where()
					.eq("courseStudent_id", courseStudent).query()) {
				to.add(grade.getTopic());
			}
		}
		for (Topic topic : to) {
			if (!topics.contains(topic)) {
				topics.add(topic);
			}
		}

		topics.sort(new Comparator<Topic>() {
			public int compare(Topic o1, Topic o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
	}

	/**
	 * Sets the enabled.
	 *
	 * @param b
	 *            the new enabled
	 */
	public void setEnabled(boolean b) {
		bEnabled = b;
	}

	/**
	 * Checks if is enabled.
	 *
	 * @return true, if is enabled
	 */
	public boolean isEnabled() {
		return bEnabled;
	}
}
