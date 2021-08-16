package fr.nico.plugin.favorite.model;

public class Identity {
	private String name;

	private String displayName;
	private String nameAndDisplayName; 
	public Identity(String name, String displayName) {
		super();
		this.name = name;
		this.displayName = displayName;
		this.nameAndDisplayName = name + " " + displayName ;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getNameAndDisplayName() {
		return nameAndDisplayName;
	}

	public void setNameAndDisplayName(String nameAndDisplayName) {
		this.nameAndDisplayName = nameAndDisplayName;
	}


}
