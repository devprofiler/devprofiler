package com.devprofiler;

import com.devprofiler.entity.controller.ProfileJpaController;
import com.devprofiler.entities.Updates;
import com.devprofiler.entity.controller.UpdatesJpaController;
import com.devprofiler.entities.UserManagement;
import com.devprofiler.entity.controller.UserManagementJpaController;
import com.devprofiler.utils.Configuration;
import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Bytes;

public class UpdatesPage extends HomePage {

    private String username = null;
    Form updateForm = null;
    private FileUploadField fileUpload;
    private String fileName;

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

        List<Updates> updates = um == null || um.getProfile() == null || um.getProfile().getUpdates() == null ? new ArrayList<Updates>() : um.getProfile().getUpdates();
        ListView updateLV = new ListView("updateList", updates) {
            @Override
            protected void populateItem(ListItem li) {
                final Updates up = (Updates) li.getModelObject();
//                 li.add(new Label("updateTitle", up.getUpdateTitle()));
                li.add(new Label("updateText", up.getUpdateText()));
                String title = up.getUpdateTitle() == "" ? "no title" : up.getUpdateTitle();
                li.add(new Link("updateTitle", new Model(title)) {
                    @Override
                    public void onClick() {

                        updateForm.setDefaultModel(new CompoundPropertyModel(up));

                    }

                });
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

        // ======================================================================
        Form mediaForm = new Form("mediaForm") {

            @Override
            protected void onSubmit() {
                super.onSubmit();
                final FileUpload uploadedFile = fileUpload.getFileUpload();
                if (uploadedFile != null) {

                    // write to a new file
                    File newFile = new File(Configuration.getFileStoragePath()
                            + uploadedFile.getClientFileName());
                    if (!newFile.isDirectory()) {
                        newFile.mkdirs();
                    }

                    if (newFile.exists()) {
                        newFile.delete();
                    }

                    try {
                        newFile.createNewFile();
                        uploadedFile.writeTo(newFile);

                        info("saved file: " + uploadedFile.getClientFileName());
                        fileName = uploadedFile.getClientFileName();
                    } catch (Exception e) {
                        throw new IllegalStateException("Error");
                    }
                }

            }

        };
        mediaForm.setMultiPart(true);
        // max upload size, 512k
        mediaForm.setMaxSize(Bytes.megabytes(5));
        mediaForm.add(fileUpload = new FileUploadField("mediaFile"));
        add(mediaForm);

        // ========================================================================
        updateForm = new Form("updateForm") {
            @Override
            protected void onSubmit() {
                Updates up = (Updates) getModelObject();

                try {
                    if (up.getId() != null && up.getId() > 0) {
                        up.setMediaFileName(fileName);
                        getUpdatesJPAController().edit(up);
                    } else {
                        up.setMediaFileName(fileName);
                        um.getProfile().add(up);
                        getProfileJPAController().edit(um.getProfile());
                        setResponsePage(UpdatesPage.class);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(UpdatesPage.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        };

        updateForm.setDefaultModel(new CompoundPropertyModel(new Updates()));

        TextArea updateTitleTA = new TextArea("updateTitle");
        TextArea updateTextTA = new TextArea("updateText");

        updateForm.add(updateTitleTA).add(updateTextTA);
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
