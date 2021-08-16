
package fr.nico.plugin.favorite.server;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Lists;

import sailpoint.api.SailPointContext;
import sailpoint.object.CertificationItem;
import sailpoint.object.Filter;
import sailpoint.object.QueryOptions;
import sailpoint.server.BasePluginService;
import sailpoint.tools.GeneralException;

/**
 *
 *
 * @author Nicolas LEBEC
 */
public class RecertificationUpdateTask extends BasePluginService {


	/**
	 * The log.
	 */
	private static final Log LOG = LogFactory.getLog(RecertificationUpdateTask.class);

	/**
	 * Constructor.
	 */
	public RecertificationUpdateTask() {
	}

	/**
	 * {@inheritDoc}
	 */
	public String getPluginName() {
		return "Nico's plugin";
	}

	/**
	 * {@inheritDoc}
	 */
	public void configure(SailPointContext context) throws GeneralException {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(SailPointContext context) throws GeneralException {

	}

	public String getRandom(String[] array) {
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}

}
