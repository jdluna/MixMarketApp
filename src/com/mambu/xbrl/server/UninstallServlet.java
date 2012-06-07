package com.mambu.xbrl.server;

import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mambu.xbrl.server.util.ParamUtils;
import com.mambu.xbrl.shared.TenantSettings;

public class UninstallServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(UninstallServlet.class.getName());
    
	private static final long serialVersionUID = 4166785295861425857L;

	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {

		Map<String, String[]> params = req.getParameterMap();
		String tenantID = ParamUtils.get("TENANT_ID", params);
		String appKey = ParamUtils.get("APP_KEY", params);
				
		//do some valiation
		if (tenantID == null || appKey == null) {
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
			log.info("Deleted tenant " + tenantID);
			
		} finally {
			pm.close();
		}

	}
}
