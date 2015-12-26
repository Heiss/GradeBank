package de.netzmuffel.bank.model;

import com.j256.ormlite.field.DatabaseField;
import de.netzmuffel.bank.model.enumerator.Subject;

/**
 * The Class Course represent a course.
 */
public class Course {
	
	/** The id. */
	@DatabaseField(generatedId = true)
	private int id;

	/** The subject. */
	@DatabaseField(canBeNull = false)
	/** The subject. */
	private Subject subject;

	/** The course name. */
	@DatabaseField(canBeNull = false)
	private String courseName;

	/** The teacher. */
	@DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 3)
	private Teacher teacher;

	/** The b highschool course. */
	@DatabaseField(canBeNull = false)
	private boolean bHighschoolCourse;

	/**
	 * Instantiates a new course.
	 */
	public Course() {
		id = 0;
		subject = Subject.Art;
		courseName = "";
		bHighschoolCourse = false;
		teacher = new Teacher();
	}

	/**
	 * Instantiates a new course.
	 *
	 * @param c the c
	 * @param t the t
	 * @param s the s
	 * @param isHighschoolCourse the is highschool course
	 */
	public Course(String c, Teacher t, Subject s, boolean isHighschoolCourse) {
		courseName = c;
		subject = s;
		teacher = t;
		bHighschoolCourse = isHighschoolCourse;
	}

	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	public Subject getSubject() {
		return subject;
	}

	/**
	 * Sets the subject.
	 *
	 * @param subject
	 *            the new subject
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	/**
	 * Gets the course name.
	 *
	 * @return the course name
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Sets the course name.
	 *
	 * @param courseName the new course name
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Gets the teacher.
	 *
	 * @return the teacher
	 */
	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * Sets the teacher.
	 *
	 * @param teacher the new teacher
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	/**
	 * Checks if is b highschool course.
	 *
	 * @return true, if is b highschool course
	 */
	public boolean isbHighschoolCourse() {
		return bHighschoolCourse;
	}

	/**
	 * Sets the b highschool course.
	 *
	 * @param bHighschoolCourse the new b highschool course
	 */
	public void setbHighschoolCourse(boolean bHighschoolCourse) {
		this.bHighschoolCourse = bHighschoolCourse;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Course))
			return false;
		if (obj == this)
			return true;

		Course o = (Course) obj;
		return (this.subject.equals(o.getSubject()) && this.courseName.equals(o.getCourseName())
				&& this.teacher.equals(o.getTeacher()) && this.bHighschoolCourse == o.isbHighschoolCourse());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Course: name: " + courseName + ", isHighschoolCourse: " + bHighschoolCourse + ", teacher: " + teacher
				+ ", subject: " + subject.toString();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getID() {
		return id;
	}
}
