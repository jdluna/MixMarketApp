package com.mambu.xbrl.server;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mambu.xbrl.server.util.MambuRequestParser;
import com.mambu.xbrl.server.util.PMF;
import com.mambu.xbrl.shared.TenantSettings;

/**
 * Uninstalls the app
 * 
 * @author edanilkis
 * 
 */
public class UninstallServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(UninstallServlet.class.getName());
    
	private static final long serialVersionUID = 4166785295861425857L;

	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {

		MambuRequestParser parser = new MambuRequestParser(req);

		// check if is valid
		if (!parser.isValidRequest(Constants.SECRET_KEY)) {
			log.severe("Request is not valid: " + parser.isValidRequest(Constants.SECRET_KEY));
			return;
		}
		
		//now get the params
		HashMap<String, String> payloadMap = parser.getPayloadMap();
		
		Map<String, String[]> params = req.getParameterMap();
		String tenantID = payloadMap.get("TENANT_ID");
				
		//do some validation
		if (tenantID == null) {
			log.severe("Undeclared params" + params.toString());
			return;
		}

		// create tenant
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			
			Query query = pm.newQuery(TenantSettings.class,"tenantID == tenantIDParam");
			query.declareParameters("String tenantIDParam");
			query.setUnique(true);
			TenantSettings settings = (TenantSettings) query.execute(tenantID);
			pm.deletePersistent(settings);
			log.info("Deleted tenant settings " + tenantID);
			
		} finally {
			pm.close();
		}

	}
}
