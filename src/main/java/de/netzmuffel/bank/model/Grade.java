package de.netzmuffel.bank.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * The Class Grade.
 */
public class Grade {
	
	/** The id. */
	@DatabaseField(generatedId = true)
	private int id;

	/** The course student. */
	@DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 3)
	private CourseStudent courseStudent;

	/** The point. */
	@DatabaseField(canBeNull = false)
	private int point;

	/** The topic. */
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
	private Topic topic;

	/**
	 * Instantiates a new grade.
	 */
	public Grade() {
		point = -1;
		courseStudent = new CourseStudent();
		topic = new Topic();
	}

	/**
	 * Instantiates a new grade.
	 *
	 * @param p the p
	 * @param cs the cs
	 * @param t the t
	 */
	public Grade(int p, CourseStudent cs, Topic t) {
		point = p;
		courseStudent = cs;
		topic = t;
	}

	/**
	 * Gets the point.
	 *
	 * @return the point
	 */
	public int getPoint() {
		return (point >= 0) ? point : -1;
	}

	/**
	 * Sets the point.
	 *
	 * @param point the new point
	 */
	public void setPoint(int point) {
		this.point = point;
	}

	/**
	 * Gets the course student.
	 *
	 * @return the course student
	 */
	public CourseStudent getCourseStudent() {
		return courseStudent;
	}

	/**
	 * Sets the course student.
	 *
	 * @param courseStudent the new course student
	 */
	public void setCourseStudent(CourseStudent courseStudent) {
		this.courseStudent = courseStudent;
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

		Grade o = (Grade) other;

		return (this.getPoint() == o.getPoint() && this.getCourseStudent().equals(o.getCourseStudent()) && this
				.getTopic().equals(o.getTopic()));
	}

	/**
	 * Gets the topic.
	 *
	 * @return the topic
	 */
	public Topic getTopic() {
		return topic;
	}

	/**
	 * Sets the topic.
	 *
	 * @param t the new topic
	 */
	public void setTopic(Topic t) {
		topic = t;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Grade: Points " + point + " in topic " + topic + " | " + courseStudent.toString();
	}

}
