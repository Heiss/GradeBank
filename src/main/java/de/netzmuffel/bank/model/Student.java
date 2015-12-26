package de.netzmuffel.bank.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * The Class Student represents a student.
 */
public class Student {
	
	/** The id. */
	@DatabaseField(generatedId = true)
	private int id;

	/** The name. */
	@DatabaseField(canBeNull = false)
	/** The forename. */
	private String name;

	/** The forename. */
	@DatabaseField(canBeNull = false)
	private String forename;

	/** The mail. */
	@DatabaseField(canBeNull = true)
	/** The telephone. */
	private String mail;

	/** The telephone. */
	@DatabaseField(canBeNull = true)
	private String telephone;

	/**
	 * Instantiates a new student.
	 */
	public Student() {
		name = "";
		forename = "";
		mail = "";
		telephone = "";
	}

	/**
	 * Instantiates a new student.
	 *
	 * @param n the n
	 * @param f the f
	 * @param m the m
	 * @param t the t
	 */
	public Student(String n, String f, String m, String t) {
		name = n;
		forename = f;
		mail = m;
		telephone = t;
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
	 *            the new name
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
	 *            the new forename
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
	 *            the new mail
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
	 *            the new telephone
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return name + ", " + forename + " (" + mail + ", " + telephone + ")";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Student))
			return false;

		Student o = (Student) obj;

		return (this.forename.equals(o.getForename()) && this.name.equals(o.getName()) && this.mail.equals(o.getMail()) && this.telephone
				.equals(o.getTelephone()));
	}
}
