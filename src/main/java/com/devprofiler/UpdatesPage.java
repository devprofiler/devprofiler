package com.devprofiler;

import com.devprofiler.entities.ProfileJpaController;
import com.devprofiler.entities.Updates;
import com.devprofiler.entities.UpdatesJpaController;
import com.devprofiler.entities.UserManagement;
import com.devprofiler.entities.UserManagementJpaController;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;

public class UpdatesPage extends HomePage {

    private String username = null;

    @Override
    protected void onConfigure() {
        AuthenticatedWebApplication app = (AuthenticatedWebApplication) AuthenticatedWebApplication.get();

        if (!AuthenticatedWebSession.get().isSignedIn()) {
            app.restartResponseAtSignInPage();
        } else {
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

    }

    public UpdatesPage() {
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
        addUpdateList();
    }

    private void addUpdateList() {
        if (Session.get() != null && Session.get().getAttribute("username") != null) {
            username = Session.get().getAttribute("username").toString();
        }

        System.out.println(username);
        final UserManagement um = getUserManagementJPAController().findUserManagementUserName(username);
//        ListView<Updates> updateLV = new ListView<Updates>("updateList", Model.ofList(um.getProfile().getUpdates())) {
//            @Override
//            protected void populateItem(ListItem li) {
//                Updates up = (Updates) li.getModelObject();
//                li.add(new Label("updateText", up.getUpdate()));
//            }
//
//        };

        
        List<Updates> updates =  um == null || um.getProfile() == null || um.getProfile().getUpdates() == null ? new ArrayList<Updates>() : um.getProfile().getUpdates();
        ListView updateLV = new ListView("updateList",updates){
             @Override
            protected void populateItem(ListItem li) {
                Updates up = (Updates) li.getModelObject();
                li.add(new Label("updateText", up.getUpdate()));
            }
        };
        add(updateLV);
    }

    private void addProfileForm() {
        if (Session.get() != null && Session.get().getAttribute("username") != null) {
            username = Session.get().getAttribute("username").toString();
        }

        System.out.println(username);
        final UserManagement um = getUserManagementJPAController().findUserManagementUserName(username);
        final Updates updates = new Updates();
        StatelessForm updateForm = new StatelessForm("updateForm") {
            @Override
            protected void onSubmit() {
                try {

                    um.getProfile().add(updates);
                    getProfileJPAController().edit(um.getProfile());
                    setResponsePage(UpdatesPage.class);
                } catch (Exception ex) {
                    Logger.getLogger(UpdatesPage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        updateForm.setDefaultModel(new CompoundPropertyModel(updates));

        TextArea updatesTA = new TextArea("update");

        updateForm.add(updatesTA);
        add(updateForm);

    }

    private ProfileJpaController getProfileJPAController() {
        WicketApplication app = (WicketApplication) this.getApplication();
        return app.getProfileJPAController();
    }

    private UserManagementJpaController getUserManagementJPAController() {
        WicketApplication app = (WicketApplication) this.getApplication();
        return app.getUserManagementJPAController();
    }

    private UpdatesJpaController getUpdatesJPAController() {
        WicketApplication app = (WicketApplication) this.getApplication();
        return app.getUpdatesJPAController();
    }

}
