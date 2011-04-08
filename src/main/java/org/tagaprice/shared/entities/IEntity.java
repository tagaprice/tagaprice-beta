package org.tagaprice.shared.entities;

public interface IEntity  {

	/**
	 * Returns the Title of the {@link AEntity}, if Title is NULL you get NULL back
	 * @return Returns the Title of the {@link AEntity} or null.
	 */
	public String getTitle();

	/**
	 * This method can be used by the client and the server.
	 * @param title Sets the entity title
	 */
	public void setTitle(String title);


}
