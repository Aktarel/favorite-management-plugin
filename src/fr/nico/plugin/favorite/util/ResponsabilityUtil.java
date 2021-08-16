package fr.nico.plugin.favorite.util;

import fr.nico.plugin.favorite.model.Responsability;
import sailpoint.object.Identity;
import sailpoint.object.ManagedAttribute;

public class ResponsabilityUtil {

	public static Responsability map(ManagedAttribute entitlement,Identity identity) {
		Responsability r = new Responsability();
		r.setDescription("Entitlement owner on application : " + entitlement.getApplication().getName() +" - entitlement : "+ entitlement.getDisplayName());
		r.setName("Approver " + entitlement.getApplication().getName() + " - " + entitlement.getDisplayName());
		r.setOwner(identity.getDisplayName());
		r.setType("Entitlement Owner");
		return r;
	}
}
