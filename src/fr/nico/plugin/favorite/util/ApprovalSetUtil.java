package fr.nico.plugin.favorite.util;

import fr.nico.plugin.favorite.model.Responsability;
import sailpoint.object.ApprovalItem;
import sailpoint.object.Comment;

public class ApprovalSetUtil {

	public static ApprovalItem generateApprovalItem(Responsability r) {
		ApprovalItem item = new ApprovalItem();
		item.setOperation("Modify");
		item.setDisplayValue(r.getName());
		item.setAttribute("responsabilityType",r.getType());
		item.setAttribute("responsabilityName", r.getName());
		item.add(new Comment("Please validate within 10 days before it expires","The administrator"));
		return item;
	}
}
