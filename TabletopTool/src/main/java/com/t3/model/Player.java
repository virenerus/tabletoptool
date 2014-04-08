/*
 * Copyright (c) 2014 tabletoptool.com team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     rptools.com team - initial implementation
 *     tabletoptool.com team - further development
 */
package com.t3.model;

/**
 * @author trevor
 */
public class Player {
	public enum Role {
		PLAYER,
		GM
	}

	private String name; // Primary Key
	private Role role;
	private String password;

	public Player() {
		// For serialization
	}

	public Player(String name, Role role, String password) {
		this.name = name;
		this.role = role;
		this.password = password;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Player)) {
			return false;
		}
		return name.equals(((Player) obj).name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	public boolean isGM() {
		return getRole() == Role.GM;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * @return Returns the role.
	 */
	public Role getRole() {
		return role;
	}

	@Override
	public String toString() {
		return name + " " + (getRole() == Role.PLAYER ? "(Player)" : "(GM)");
	}
}
