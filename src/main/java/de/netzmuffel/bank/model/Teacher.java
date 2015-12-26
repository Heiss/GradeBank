package de.netzmuffel.bank.model;

import java.util.Collection;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

/**
 * The Class Teacher represents a teacher.
 */
public class Teacher {

	/** The id. */
	@DatabaseField(generatedId = true)
	private int id;

	/** The name. */
	@DatabaseField(canBeNull = false)
	private String name;

	/** The forename. */
	@DatabaseField(canBeNull = false)
	private String forename;

	/** The mail. */
	@DatabaseField(canBeNull = true)
	private String mail;

	/** The telephone. */
	@DatabaseField(canBeNull = true)
	private String telephone;

	/** The courses. */
	@ForeignCollectionField(eager = false)
	private ForeignCollection<Course> courses;

	/**
	 * Create a new teacher with empty variables.
	 */
	public Teacher() {
		id = 0;
		name = "";
		forename = "";
		mail = "";
		telephone = "";
	}

	/**
	 * Create a new teacher.
	 *
	 * @param name
	 *            the name of teacher
	 * @param forename
	 *            the forename of teacher
	 * @param mail
	 *            the mail-adress of teacher
	 * @param telephone
	 *            the telephone-number (also mobile) of teacher
	 */
	public Teacher(String name, String forename, String mail, String telephone) {
		this.name = name;
		this.forename = forename;
		this.mail = mail;
		this.telephone = telephone;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            set a new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the forename.
	 *
	 * @return the forename
	 */
	public String getForename() {
		return forename;
	}

	/**
	 * Sets the forename.
	 *
	 * @param forename
	 *            set a new forename
	 */
	public void setForename(String forename) {
		this.forename = forename;
	}

	/**
	 * Gets the mail.
	 *
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Sets the mail.
	 *
	 * @param mail
	 *            set a new mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * Gets the telephone.
	 *
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * Sets the telephone.
	 *
	 * @param telephone
	 *            set a new telephone number (also mobile)
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * Gets the courses.
	 *
	 * @return the courses
	 */
	public Collection<Course> getCourses() {
		return courses;
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

		Teacher o = (Teacher) other;

		return (this.name.equals(o.name) && this.forename.equals(o.forename) && this.mail.equals(o.mail) && this.telephone
				.equals(o.telephone));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return name + ", " + forename;
	}
}
