package com.devprofiler;

import com.devprofiler.entities.Profile;
import com.devprofiler.entities.ProfileJpaController;
import com.devprofiler.entities.UserManagement;
import com.devprofiler.entities.UserManagementJpaController;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WicketServlet;

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
