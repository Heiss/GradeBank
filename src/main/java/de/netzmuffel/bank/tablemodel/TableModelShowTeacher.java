package de.netzmuffel.bank.tablemodel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import de.netzmuffel.bank.Config;
import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.Teacher;

/**
 * The Class ShowCoursesTableModel represents the data structure behind a
 * jTable.
 */
public class TableModelShowTeacher extends AbstractTableModel {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The teacher. */
	private List<Teacher> teacher;
	
	/** The db. */
	private Database db;

	/**
	 * Instantiates a new table model show teacher.
	 *
	 * @param db the db
	 */
	public TableModelShowTeacher(Database db) {
		this.db = db;
		teacher = null;
		refresh();
	}

	/**
	 * Refresh.
	 */
	public void refresh() {
		try {
			// h ere is a Nullpointer!!!!
			teacher = db.getTeacherDAO().queryForAll();
		} catch (SQLException e) {
			Config.logger.error("There was an critical error in {}.refresh()", this.getClass());
			e.printStackTrace();
			this.teacher = new ArrayList<Teacher>();
		}
		this.fireTableDataChanged();
	}

	/**
	 * Gets the teacher.
	 *
	 * @return the teacher
	 */
	public List<Teacher> getTeacher() {
		return teacher;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return teacher.size();
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
		Teacher t = teacher.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return t.getName();
		case 1:
			return t.getForename();
		case 2:
			return t.getMail();
		case 3:
			return t.getTelephone();
		default:
			return t.getCourses().size();
		}
	}

	/* (non-Javadoc)
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
		case 3:
			return I18n.get().telephone();
		default:
			return I18n.get().sizeCourses();
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
