package com.devprofiler;

import com.devprofiler.entities.UserManagement;
import com.devprofiler.entity.controller.UserManagementJpaController;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

public class BasicAuthenticationSession extends AuthenticatedWebSession {

	public BasicAuthenticationSession(Request request) {
		super(request);		
	}

	@Override
	public boolean authenticate(String username, String password) {
		UserManagement umg = getUserManagementJPAController()
                        .findUserManagementUidPwd(username, password);
                if(umg != null){
                    return true;
                }
                return false;
	}

	@Override
	public Roles getRoles() {
		return null;
	}

    private UserManagementJpaController getUserManagementJPAController() {
        WicketApplication app = (WicketApplication) this.getApplication();
        return app.getUserManagementJPAController();
    }
}
