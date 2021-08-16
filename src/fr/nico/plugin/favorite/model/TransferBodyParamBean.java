package fr.nico.plugin.favorite.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class TransferBodyParamBean {
	
	@XmlAttribute
	private String newUid;

	@XmlAttribute(name = "responsabilities" )
	private List<Responsability> responsabilities;
	
	public TransferBodyParamBean() {
	}
	
	public TransferBodyParamBean(List<Responsability> responsabilities) {
		super();
		this.responsabilities = responsabilities;
	}
	
	public String getNewUid() {
		return newUid;
	}

	public void setNewUid(String newUid) {
		this.newUid = newUid;
	}

	public List<Responsability> getResponsabilities() {
		return responsabilities;
	}

	public void setResponsabilities(List<Responsability> responsabilities) {
		this.responsabilities = responsabilities;
	}

	
	
}
