package fr.nico.plugin.favorite.util;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import sailpoint.object.ApprovalItem;
import sailpoint.object.ApprovalSet;
import sailpoint.object.Identity;
import sailpoint.object.WorkItem;
import sailpoint.object.WorkItem.Level;
import sailpoint.object.WorkItem.State;
import sailpoint.object.WorkItem.Type;

public class WorkitemUtil {

	public static WorkItem generateWorkitem(Identity loggedUser, Identity owner, List<ApprovalItem> items,
			String workitemId) {
		WorkItem workitem = new WorkItem();
		workitem.setRequester(loggedUser);
		workitem.setOwner(owner);
		workitem.setName(workitemId);
		workitem.setType(Type.Approval);
		workitem.setState(State.Pending);
		workitem.setLevel(Level.Normal);
		workitem.setDescription(getWorkitemDescription(loggedUser));
		workitem.setExpiration(DateUtils.addDays(new Date(), 20));
		ApprovalSet approvalSet = new ApprovalSet();
		approvalSet.setItems(items);
		workitem.setApprovalSet(approvalSet);
		return workitem;
	}

	private static String getWorkitemDescription(Identity loggedUser) {
		StringBuilder sb = new StringBuilder("Responsability Transfer initiated by ");
		sb.append(loggedUser.getDisplayName()).append(" (").append(loggedUser.getName()).append(")");
		return sb.toString();
	}
}
