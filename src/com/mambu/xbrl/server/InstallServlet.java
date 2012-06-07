package com.mambu.xbrl.server;

import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mambu.xbrl.server.util.ParamUtils;
import com.mambu.xbrl.shared.TenantSettings;

public class InstallServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(InstallServlet.class.getName());
    
	private static final long serialVersionUID = 4166785295861425857L;

	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {

		Map<String, String[]> params = req.getParameterMap();
		String tenantID = ParamUtils.get("TENANT_ID", params);
		String appKey = ParamUtils.get("APP_KEY", params);
		String userName = ParamUtils.get("USERNAME", params);
		String pword = ParamUtils.get("PASSWORD", params);
		String hostname = ParamUtils.get("HOSTNAME", params);
		
		hostname = hostname == null ? req.getRemoteHost() : hostname; // hostname
		
		//do some valiation
		if (tenantID == null ||  userName == null || pword == null) {
			log.severe("Undeclared params" + params.toString());
			return;
		}

		// create tenant
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			TenantSettings tenant = new TenantSettings();
			tenant.setAppKey(appKey);
			tenant.setTenantID(tenantID);
			tenant.setUsername(userName);
			tenant.setPassword(pword);
			tenant.setDomain(hostname);
			
			pm.makePersistent(tenant);	
			
			log.info("Stored data");
			
			
		} finally {
			pm.close();
		}

	}
}
