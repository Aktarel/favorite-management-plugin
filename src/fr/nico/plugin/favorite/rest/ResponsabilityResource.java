
package fr.nico.plugin.favorite.rest;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.mysql.cj.protocol.Resultset;

import fr.nico.plugin.favorite.model.Responsability;
import fr.nico.plugin.favorite.model.TransferBodyParamBean;
import fr.nico.plugin.favorite.util.ApprovalSetUtil;
import fr.nico.plugin.favorite.util.ResponsabilityUtil;
import fr.nico.plugin.favorite.util.WorkitemUtil;
import sailpoint.integration.RestClient;
import sailpoint.object.Application;
import sailpoint.object.ApprovalItem;
import sailpoint.object.ApprovalSet;
import sailpoint.object.Comment;
import sailpoint.object.Filter;
import sailpoint.object.Identity;
import sailpoint.object.ManagedAttribute;
import sailpoint.object.QueryOptions;
import sailpoint.object.WorkItem;
import sailpoint.object.WorkItem.Level;
import sailpoint.object.WorkItem.State;
import sailpoint.object.WorkItem.Type;
import sailpoint.rest.plugin.AllowAll;
import sailpoint.rest.plugin.BasePluginResource;
import sailpoint.tools.GeneralException;

/**
 * The REST resource which exposes the todo list page configuration based on the
 * currently logged in user.
 *
 * @author Dustin Dobervich <dustin.dobervich@sailpoint.com>
 */
@Path("TransferResponsabilities")
@Produces("application/json")
public class ResponsabilityResource extends BasePluginResource {

	Logger log = Logger.getLogger(ResponsabilityResource.class);
	ObjectMapper mapper = new ObjectMapper();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPluginName() {
		return "TransferResponsabilities";
	}

	/**
	 * Gets the todo list page configuration for the current user.
	 *
	 * @return The page configuration.
	 * @throws GeneralException
	 */
	@GET
	@Path("responsability")
	@AllowAll
	public List<Responsability> getAllResponsabilities() throws GeneralException {
		List<Responsability> responsabilities = new ArrayList<>();
		Identity identity = getLoggedInUser();
		QueryOptions entitlementsQueryOpt = new QueryOptions();
		entitlementsQueryOpt.add(new Filter[] { Filter.eq("owner.id", identity.getId()) });
		List<ManagedAttribute> entitlements = Lists
				.newArrayList(this.getContext().search(ManagedAttribute.class, entitlementsQueryOpt));

		for (ManagedAttribute entitlement : entitlements) {
			responsabilities.add(ResponsabilityUtil.map(entitlement, identity));
		}
		return responsabilities;
	}

	@POST
	@Path("responsability")
	@AllowAll
	public void launchTransfer(String json) throws GeneralException, IOException, SQLException {
		Identity identity = getLoggedInUser();
//		TransferBodyParamBean param = mapper.readValue(json, TransferBodyParamBean.class);
//		List<ApprovalItem> items = new ArrayList<>();
//		for (Responsability r : param.getResponsabilities()) {
//			items.add(ApprovalSetUtil.generateApprovalItem(r));
//		}
//		QueryOptions identityQueryOpt = new QueryOptions();
//		identityQueryOpt.add(new Filter[] { Filter.eq("name", param.getNewUid()) });
//		List<Identity> identities = Lists.newArrayList(this.getContext().search(Identity.class, identityQueryOpt));
//		
//		PreparedStatement stmtSeq = prepareStatement(getConnection(), "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = \"identityiqplugin\" AND TABLE_NAME = \"RESPONSABILITIES_TRANSFERT_REQUEST\"");
//		String workitemID = String.valueOf(Math.random());
//		ResultSet rs = stmtSeq.executeQuery();
//		RestClient rc = new RestClient();
//		Application aa = new Application();
//		while(rs.next()) {
//		    workitemID = String.valueOf(rs.getInt("AUTO_INCREMENT"));
//		}
//		System.out.println(workitemID);
//		PreparedStatement insert = prepareStatement(getConnection(),
//				"INSERT INTO RESPONSABILITIES_TRANSFERT_REQUEST(application_id,owner_id,created) VALUES (?,?,?)",
//				"", "", new java.sql.Date(Calendar.getInstance().getTime().getTime()));
//		insert.executeUpdate();
//
//		WorkItem workitem = WorkitemUtil.generateWorkitem(getLoggedInUser(), identities.get(0), items, "TFR-" + workitemID);
//		getContext().saveObject(workitem);
//		getContext().commitTransaction();
	}
}
