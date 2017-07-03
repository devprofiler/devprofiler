package com.devprofiler;

import com.devprofiler.entities.ProfileJpaController;
import com.devprofiler.entities.UserManagementJpaController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.wicket.Application;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.WebPage;


public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    public HomePage() {
        super();
            add(new Link<Void>("goToAuthenticatedPage") {
                
                @Override
                public void onClick() {
                    setResponsePage(ProfilePage.class);
                }
                
            });
            
                 

         add(new Link<Void>("logOut") {

            @Override
            public void onClick() {
                AuthenticatedWebSession.get().invalidate();
                setResponsePage(getApplication().getHomePage());
            }

        });
            
    }

    private UserManagementJpaController getUserManagementJPAController() {
        WicketApplication app = (WicketApplication) this.getApplication();
        return app.getUserManagementJPAController();
    }

    private ProfileJpaController getProfileJpaController() {
        WicketApplication app = (WicketApplication) this.getApplication();
        return app.getProfileJPAController();
    }



}
