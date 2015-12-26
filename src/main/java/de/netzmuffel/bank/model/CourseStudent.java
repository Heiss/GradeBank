package de.netzmuffel.bank.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

/**
 * The Class CourseStudent.
 */
public class CourseStudent {
	
	/** The id. */
	@DatabaseField(generatedId = true)
	private int id;

	/** The course. */
	@DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 3)
	private Course course;

	/** The student. */
	@DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 3)
	private Student student;

	/** The grades. */
	@ForeignCollectionField(eager = false)
	private ForeignCollection<Grade> grades;

	/**
	 * Instantiates a new course student.
	 */
	public CourseStudent() {
		course = null;
		student = null;
	}

	/**
	 * Instantiates a new course student.
	 *
	 * @param c the c
	 * @param s the s
	 */
	public CourseStudent(Course c, Student s) {
		course = c;
		student = s;
	}

	/**
	 * Gets the course.
	 *
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * Sets the course.
	 *
	 * @param course the new course
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * Gets the student.
	 *
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * Sets the student.
	 *
	 * @param student the new student
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * Gets the grades.
	 *
	 * @return the grades
	 */
	public ForeignCollection<Grade> getGrades() {
		return grades;
	}

	/**
	 * Sets the grades.
	 *
	 * @param grades the new grades
	 */
	public void setGrades(ForeignCollection<Grade> grades) {
		this.grades = grades;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null || other.getClass() != getClass()) {
			return false;
		}
		if (other == this)
			return true;

		CourseStudent o = (CourseStudent) other;

		return (this.getCourse().equals(o.getCourse()) && this.getStudent().equals(o.getStudent()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "CourseStudent: " + getCourse() + " | " + getStudent();
	}
}
