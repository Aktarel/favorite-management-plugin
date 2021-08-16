
package fr.nico.plugin.favorite.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Lists;

import fr.nico.plugin.favorite.model.Responsability;
import fr.nico.plugin.favorite.model.TransferBodyParamBean;
import sailpoint.object.Filter;
import sailpoint.object.Filter.MatchMode;
import sailpoint.object.Identity;
import sailpoint.object.ManagedAttribute;
import sailpoint.object.QueryOptions;
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
@Consumes("application/json")
public class IdentityResource extends BasePluginResource {

	Logger log = Logger.getLogger(IdentityResource.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPluginName() {
		return "TransferResponsabilities";
	}

	/**
	 * Return identities that matches throught displayName or name for autocomplete
	 * @return The page configuration.
	 * @throws GeneralException
	 */
	@GET
	@Path("identities")
	@AllowAll
	public List<fr.nico.plugin.favorite.model.Identity> getIdentities(@QueryParam(value = "q") String q)
			throws GeneralException {
		QueryOptions certQueryOptions = new QueryOptions();
		certQueryOptions.add(new Filter[] { Filter.or(Filter.like("displayName", q, MatchMode.ANYWHERE),
				Filter.like("name", q, MatchMode.ANYWHERE)) });
		List<fr.nico.plugin.favorite.model.Identity> results = new ArrayList<>();
		List<Identity> identities = Lists.newArrayList(this.getContext().search(Identity.class, certQueryOptions));
		for (Identity i : identities) {
			results.add(new fr.nico.plugin.favorite.model.Identity(i.getName(), i.getDisplayName()));
		}
		return results;
	}

}
