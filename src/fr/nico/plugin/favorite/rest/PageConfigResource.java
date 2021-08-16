
package fr.nico.plugin.favorite.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.web.bind.annotation.PathVariable;

import gmcclientapp.ws.Application;
import sailpoint.object.Capability;
import sailpoint.object.Identity;
import sailpoint.object.Identity.CapabilityManager;
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
public class PageConfigResource extends BasePluginResource {

	/**
	 * Class holding the page configuration data.
	 */
	private static class PageConfig {

		private List<Section> sections;

		public List<Section> getSections() {
			return sections;
		}

		public void setSections(List<Section> sections) {
			this.sections = sections;
		}

	}

	/**
	 * Class holding the page configuration data.
	 */
	private static class Section {

		private String name;
		private String description;
		private String glyphName;
		private String url;
		private String parentName;
		
		public Section(String name, String description, String glyphName, String url, String parentName) {
			super();
			this.name = name;
			this.description = description;
			this.glyphName = glyphName;
			this.url = url;
			this.parentName = parentName;
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

		public String getGlyphName() {
			return glyphName;
		}

		public void setGlyphName(String glyphName) {
			this.glyphName = glyphName;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getParentName() {
			return parentName;
		}

		public void setParentName(String parentName) {
			this.parentName = parentName;
		}

	}

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
	@Path("pageConfig")
	@AllowAll
	public PageConfig getPageConfig() throws GeneralException {
		Identity identity = getLoggedInUser();
		if (identity == null) {
			throw new GeneralException("Unable to load identity for logged in user.");
		}
		PageConfig pageConfig = new PageConfig();
		pageConfig.sections = new ArrayList<PageConfigResource.Section>();
		Section br = new Section("Business Roles", "Test roles !","glyphicon-user","#","");
		Section application = new Section("Applications", "Test applications!","glyphicon-random","#","");
		Section sharedrive = new Section("Sharedrives", "Test 2!","glyphicon-hdd","#","");
		Section sharepoint = new Section("Sharepoint", "Test 2!","glyphicon-th-large","#","");
		Section favorite = new Section("Favorites", "Test 2!","glyphicon-star","#","");
		Section others = new Section("Others", "Test 2!","glyphicon-list-alt","#","");
		
		pageConfig.sections.add(br);
		pageConfig.sections.add(application);
		pageConfig.sections.add(sharedrive);
		pageConfig.sections.add(sharepoint);
		pageConfig.sections.add(favorite);
		pageConfig.sections.add(others);
		
	
	
		

		return pageConfig;
	}

}
