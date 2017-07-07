
package com.devprofiler.entity.controller;

import javax.persistence.EntityManagerFactory;

public class ServiceController {
    private EntityManagerFactory emf = null;
    private ProfileJpaController profileCtrl;
    private UpdatesJpaController updatesCtrl;
    private UserManagementJpaController userMgmtCtrl;
    
    public ServiceController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private ProfileJpaController getProfileCtrl() {
        return new ProfileJpaController(emf);
        
    }

    private UpdatesJpaController getUpdatesCtrl() {
        return new UpdatesJpaController(emf);
        
    }

    private UserManagementJpaController getUserMgmtCtrl() {
        return new UserManagementJpaController(emf);
    }

    
}
