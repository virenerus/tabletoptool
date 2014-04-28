package com.t3.model.chat;

public abstract class Speaker {
	private final String identity;
	
	public Speaker(String identity) {
		this.identity=identity;
	}
	
	public String getIdentity() {
		return identity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identity == null) ? 0 : identity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Speaker other = (Speaker) obj;
		if (identity == null) {
			if (other.identity != null)
				return false;
		} else if (!identity.equals(other.identity))
			return false;
		return true;
	}

	public abstract String toHTML();
	
	@Override
	public abstract String toString();
}