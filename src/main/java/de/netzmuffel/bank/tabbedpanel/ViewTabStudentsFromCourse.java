package de.netzmuffel.bank.tabbedpanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.j256.ormlite.stmt.DeleteBuilder;

import de.netzmuffel.bank.Config;
import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.controller.CtrlCourseGradesShow;
import de.netzmuffel.bank.controller.CtrlCourseGradesStudentEdit;
import de.netzmuffel.bank.controller.CtrlCourseStudentAddEdit;
import de.netzmuffel.bank.controller.CtrlCourseStudentAddExists;
import de.netzmuffel.bank.controller.CtrlStart;
import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.Course;
import de.netzmuffel.bank.model.CourseStudent;
import de.netzmuffel.bank.model.Student;
import de.netzmuffel.bank.model.structure.AbstractModelCtrl;
import de.netzmuffel.bank.model.structure.AbstractModelTabbedPanel;
import de.netzmuffel.bank.tablemodel.TableModelShowStudents;

/**
 * The Class ViewTabStudentsFromClass create a tab and you can use it in a
 * tabPanel.
 */
public class ViewTabStudentsFromCourse extends AbstractModelTabbedPanel {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The panel. */
	private JPanel panel;

	/** The btn new student. */
	public JButton btnStudentNew;

	/** The table. */
	public JTable table;

	/** The scroll pane. */
	public JScrollPane scrollPane;

	/** The table model. */
	public TableModelShowStudents tableModel;

	/** The btn close course. */
	public JButton btnCloseCourse;

	/** The btn student delete. */
	public JButton btnStudentDelete;

	/** The btn student edit. */
	public JButton btnStudentEdit;

	/** The btn student add. */
	public JButton btnStudentAdd;

	/** The btn grades show. */
	public JButton btnGradesShow;

	/** The btn student grade edit. */
	public JButton btnStudentGradeEdit;

	private ViewTabStudentsFromCourse vtsfc;

	private AbstractModelCtrl current;

	private Course course;

	/**
	 * Instantiates a new view tab students from class.
	 *
	 * @param db
	 *            the db
	 * @param course
	 *            the course
	 * @param current
	 *            the current
	 */
	public ViewTabStudentsFromCourse(final Database db, final Course course, final AbstractModelCtrl current) {
		this.setLayout(new BorderLayout(0, 0));

		this.vtsfc = this;
		this.current = current;
		this.db = db;
		this.course = course;

		this.panel = new JPanel();
		this.add(this.panel, BorderLayout.WEST);
		this.panel.setLayout(new GridLayout(0, 1, 0, 0));

		this.btnGradesShow = new JButton(I18n.get().showGrades());
		this.panel.add(this.btnGradesShow);

		this.btnStudentGradeEdit = new JButton(I18n.get().singleStudentGradeEdit());
		this.panel.add(this.btnStudentGradeEdit);

		// TODO Delete this when its implemented
		this.btnStudentGradeEdit.setEnabled(false);

		this.btnStudentNew = new JButton(I18n.get().createStudent());
		this.panel.add(this.btnStudentNew);

		this.btnStudentAdd = new JButton(I18n.get().addStudent());
		this.panel.add(this.btnStudentAdd);

		this.btnStudentEdit = new JButton(I18n.get().editStudent());
		this.panel.add(this.btnStudentEdit);

		this.btnStudentDelete = new JButton(I18n.get().deleteStudent());
		this.panel.add(this.btnStudentDelete);

		this.btnCloseCourse = new JButton(I18n.get().closeCourse());
		this.panel.add(this.btnCloseCourse);

		this.loadTableModel();
		this.scrollPane = new JScrollPane(this.table);
		this.add(this.scrollPane, BorderLayout.CENTER);

		this.addListenersToComponents();
	}

	private JTable loadTableModel() {
		try {
			this.tableModel = new TableModelShowStudents(this.db,
					this.db.getCourseDAO().queryForMatching(this.course).get(0));
		} catch (SQLException e) {
			Config.logger.info("There was an error in {} witch course {}", this.getClass().getSimpleName(),
					this.course);
			e.printStackTrace();
		}
		this.table = new JTable(this.tableModel);
		return this.table;
	}

	private void addListenersToComponents() {
		this.btnStudentGradeEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ViewTabStudentsFromCourse.this.table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, I18n.get().errorNoSelectionOnTable(I18n.get().student()));
					return;
				}

				new CtrlCourseGradesStudentEdit(ViewTabStudentsFromCourse.this.current,
						ViewTabStudentsFromCourse.this.db, ViewTabStudentsFromCourse.this.tableModel.courseStudents
								.get(ViewTabStudentsFromCourse.this.table.getSelectedRow()));
			}
		});

		this.btnGradesShow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CtrlCourseGradesShow(ViewTabStudentsFromCourse.this.current, ViewTabStudentsFromCourse.this.db,
						ViewTabStudentsFromCourse.this.course);
			}
		});

		this.btnStudentNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CtrlCourseStudentAddEdit(ViewTabStudentsFromCourse.this.current, ViewTabStudentsFromCourse.this.db,
						ViewTabStudentsFromCourse.this.tableModel);
			}
		});

		this.btnStudentAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CtrlCourseStudentAddExists(ViewTabStudentsFromCourse.this.current,
						ViewTabStudentsFromCourse.this.db, ViewTabStudentsFromCourse.this.course,
						ViewTabStudentsFromCourse.this.tableModel);
			}
		});

		this.btnStudentEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ViewTabStudentsFromCourse.this.table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, I18n.get().errorNoSelectionOnTable(I18n.get().student()));
					return;
				}
				new CtrlCourseStudentAddEdit(ViewTabStudentsFromCourse.this.current, ViewTabStudentsFromCourse.this.db,
						ViewTabStudentsFromCourse.this.tableModel.students
								.get(ViewTabStudentsFromCourse.this.table.getSelectedRow()),
						ViewTabStudentsFromCourse.this.tableModel);
			}
		});

		this.btnStudentDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ViewTabStudentsFromCourse.this.table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, I18n.get().errorNoSelectionOnTable(I18n.get().student()));
					return;
				}

				try {
					Student student = ViewTabStudentsFromCourse.this.tableModel.students
							.get(ViewTabStudentsFromCourse.this.table.getSelectedRow());

					int iRemove = JOptionPane.showConfirmDialog(null,
							I18n.get().confirmRemoveStudentFromCourse(student.getName(), student.getForename()),
							I18n.get().confirmation(), JOptionPane.YES_NO_OPTION);
					if (iRemove == JOptionPane.OK_OPTION) {
						DeleteBuilder<CourseStudent, Integer> rb = ViewTabStudentsFromCourse.this.db
								.getCourseStudentDAO().deleteBuilder();
						rb.where().eq("student_id", student).and().eq("course_id",
								ViewTabStudentsFromCourse.this.course);
						rb.delete();
						ViewTabStudentsFromCourse.this.tableModel.refresh();
					}
				} catch (SQLException e1) {
					Config.logger.info("There was an error in the query for the student with the name: {}",
							ViewTabStudentsFromCourse.this.table
									.getValueAt(ViewTabStudentsFromCourse.this.table.getSelectedRow(), 0));
					e1.printStackTrace();
				}
			}
		});

		this.btnCloseCourse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Config.logger.debug("Remove the course-tab {} from tabbedPane",
						ViewTabStudentsFromCourse.this.course.getCourseName());
				((CtrlStart) ViewTabStudentsFromCourse.this.current).form.tabbedPane
						.remove(ViewTabStudentsFromCourse.this.vtsfc);
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * de.netzmuffel.bank.model.structure.AbstractModelTabbedPanel#setEnabled
	 * (boolean)
	 */
	@Override
	public void setEnabled(boolean b) {
		this.btnStudentNew.setEnabled(b);
		this.btnStudentAdd.setEnabled(b);
		this.btnStudentEdit.setEnabled(b);
		this.btnCloseCourse.setEnabled(b);
		this.btnStudentDelete.setEnabled(b);
		this.btnGradesShow.setEnabled(b);
		// btnStudentGradeEdit.setEnabled(b);
	}
}
