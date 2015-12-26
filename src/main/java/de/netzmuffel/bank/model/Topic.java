package de.netzmuffel.bank.model;

import java.util.Date;
import java.util.GregorianCalendar;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

/**
 * The Class Topic.
 */
public class Topic {

	/** The id. */
	@DatabaseField(generatedId = true)
	private int id;

	/** The name. */
	@DatabaseField(canBeNull = false)
	private String name;

	/** The added. */
	@DatabaseField(canBeNull = false, dataType = DataType.DATE)
	private Date added;

	/** The grades. */
	@ForeignCollectionField(eager = false)
	private ForeignCollection<Grade> grades;

	/**
	 * Instantiates a new topic.
	 */
	public Topic() {
		name = "";
		added = GregorianCalendar.getInstance().getTime();
	}

	/**
	 * Instantiates a new topic.
	 *
	 * @param n the n
	 */
	public Topic(String n) {
		name = n;
		added = GregorianCalendar.getInstance().getTime();
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
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the added.
	 *
	 * @return the added
	 */
	public Date getAdded() {
		return added;
	}

	/**
	 * Sets the added.
	 *
	 * @param added the new added
	 */
	public void setAdded(Date added) {
		this.added = added;
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
	 * Checks if is equal.
	 *
	 * @param obj the obj
	 * @return true, if is equal
	 */
	public boolean isEqual(Object obj) {
		if (obj == null || !(obj instanceof Topic))
			return false;
		if (obj == this)
			return true;

		Topic o = (Topic) obj;
		
		if(o.getGrades() == null) {
			return (name.equals(o.getName()) && added.equals(o.getAdded()) && grades.size() == 0);			
		}
		return (name.equals(o.getName()) && added.equals(o.getAdded()) && grades.size() == o.getGrades().size());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return isEqual(obj);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return name;
	}
}
