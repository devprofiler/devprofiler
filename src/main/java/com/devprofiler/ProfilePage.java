package com.devprofiler;

import com.devprofiler.entities.Profile;
import com.devprofiler.entities.ProfileJpaController;
import com.devprofiler.entities.UserManagement;
import com.devprofiler.entities.UserManagementJpaController;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;

public class ProfilePage extends HomePage {

    private String firstName, lastName;
    private String overview;
    private String technologies;
    private String education;
    private String employment;
    private String experience;
    private String personnelProjects;
    private String updates;

    private String username = null;
    @Override
    protected void onConfigure() {
        AuthenticatedWebApplication app = (AuthenticatedWebApplication) AuthenticatedWebApplication.get();
        
        if (!AuthenticatedWebSession.get().isSignedIn()) {
            app.restartResponseAtSignInPage();
        }else{
                    }
    }
    
    @Override
    protected void onInitialize() {
        super.onInitialize();
        
    }
    
    public ProfilePage() {
//        add(new Link<Void>("goToHomePage") {
//            
//            @Override
//            public void onClick() {
//                setResponsePage(getApplication().getHomePage());
//            }
//        });
//        
//        add(new Link<Void>("logOut") {
//            
//            @Override
//            public void onClick() {
//                AuthenticatedWebSession.get().invalidate();
//                setResponsePage(getApplication().getHomePage());
//            }
//            
//        });
        addProfileForm();
    }
    
    private void addProfileForm() {
        if(Session.get() != null && Session.get().getAttribute("username") != null){
            username = Session.get().getAttribute("username").toString();
        }
        
        System.out.println(username);
        UserManagement um = getUserManagementJPAController().findUserManagementUserName(username);
        final Profile profile = username == null || um == null ||  um.getProfile() == null ? new Profile() : um.getProfile();
        System.out.println(profile);
        StatelessForm form = new StatelessForm("profileForm") {
            @Override
            protected void onSubmit() {
                try {
                  
                    getProfileJPAController().edit(profile);
                } catch (Exception ex) {
                    Logger.getLogger(ProfilePage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        
        form.setDefaultModel(new CompoundPropertyModel(profile));
        
        TextField firstName = new TextField("firstName");
        TextField lastName = new TextField("lastName");
        TextArea overview = new TextArea("overview");
        TextArea technologies = new TextArea("technologies");
        TextArea education = new TextArea("education");
        TextArea employment = new TextArea("employment");
         TextArea experience = new TextArea("experience");
        TextArea personnelProjects = new TextArea("personnelProjects");
        
        form.add(firstName)
                .add(lastName)
                .add(overview)
                .add(technologies)
                .add(education)
                .add(employment)
                .add(experience)
                .add(personnelProjects);
        add(form);
        
    }
    
    private ProfileJpaController getProfileJPAController() {
        WicketApplication app = (WicketApplication) this.getApplication();
        return app.getProfileJPAController();
    }
      private UserManagementJpaController getUserManagementJPAController() {
        WicketApplication app = (WicketApplication) this.getApplication();
        return app.getUserManagementJPAController();
    }
}
