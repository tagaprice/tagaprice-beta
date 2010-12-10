/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License.
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
 */

/**
 * Project: tagaprice
 * Filename: Unit.java
 * Date: 19.05.2010
 */
package org.tagaprice.shared.entities;

/**
 * Represents a unit to measure {@link Product}s properties.
 */
public class Unit extends Entity {
	private static final long serialVersionUID = 1L;

	private Long _standardId = null;
	private double _factor = 0;

	/**
	 * default constructor (needed for serialization)
	 */
	public Unit() {
		super();
	}

	/**
	 * Constructor for querying a Unit's current revision.
	 * 
	 * @param id
	 *            {@link Unit} ID
	 */
	public Unit(long id) {
		super(id);
	}

	/**
	 * Constructor for querying a specific Unit revision.
	 * 
	 * @param id
	 *            {@link Unit} ID
	 * @param rev
	 *            {@link Unit} revision
	 */
	public Unit(long id, int rev) {
		super(id, rev);
	}

	/**
	 * Constructor for saving a new {@link Unit}.
	 * 
	 * @param title
	 *            descriptive {@link Unit} name
	 * @param localeId
	 *            current locale
	 * @param creatorId
	 *            currently logged in user
	 * @param standardId
	 *            standard (SI) Unit ID (may be null if this unit is a standard-SI)
	 * @param factor
	 *            factor to calculate between the standard Unit and this one
	 */
	public Unit(String title, int localeId, long creatorId, Long standardId, double factor) {
		super(title, localeId, creatorId);
		_standardId = standardId;
		_factor = factor;
	}

	/**
	 * Constructor for saving an existing {@link Unit}.
	 * 
	 * @param id
	 *            {@link Unit} ID
	 * @param rev
	 *            current revision
	 * @param title
	 *            descriptive {@link Unit} name
	 * @param creatorId
	 *            currently logged in user
	 * @param standardId
	 *            standard (SI) Unit ID (may be null)
	 * @param factor
	 *            factor to calculate between the standard Unit and this one
	 */
	public Unit(long id, int rev, String title, long creatorId, Long standardId, double factor) {
		super(id, rev, title, creatorId);
		_standardId = standardId;
		_factor = factor;
	}

	/**
	 * @return standard (SI) Unit ID (may be null)
	 */
	public Long getStandardId() {
		return _standardId;
	}

	/**
	 * @param standardId
	 *            set the standard (SI) Unit ID (may be null)
	 */
	public void _setStandardId(Long standardId) {
		_standardId = standardId;
	}

	/**
	 * @return factor to calculate between the standard Unit and this one
	 */
	public double getFactor() {
		return _factor;
	}

	/**
	 * @param factor
	 *            factor to calculate between the standard Unit and this one
	 */
	public void _setFactor(double factor) {
		_factor = factor;
	}

	@Override
	public String getSerializeName() {
		return "unit";
	}

	@Override
	public boolean equals(Object o) {
		boolean rc = super.equals(o);

		if (rc && o instanceof Unit) {
			Unit u = (Unit) o;
			if (getFactor() != u.getFactor())
				rc = false;
		}
		return rc;
	}

	/**
	 * TODO implement this method
	 */
	@Override
	public <T extends Entity> T newRevision() {
		// TODO Auto-generated method stub
		return null;
	}
}