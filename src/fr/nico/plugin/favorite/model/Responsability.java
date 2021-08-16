package fr.nico.plugin.favorite.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Responsability {
	@XmlAttribute
	private String name;
	@XmlAttribute
	private String description;
	@XmlAttribute
	private String type;
	@XmlAttribute
	private String owner;
	
	public Responsability() {
	}
	
	public Responsability(String name, String description, String type, String owner) {
		super();
		this.name = name;
		this.description = description;
		this.type = type;
		this.owner = owner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
}
