package com.devprofiler;

import com.devprofiler.entities.Profile;
import com.devprofiler.entity.controller.ProfileJpaController;
import com.devprofiler.entities.Updates;
import com.devprofiler.entity.controller.UpdatesJpaController;
import com.devprofiler.entities.UserManagement;
import com.devprofiler.entity.controller.UserManagementJpaController;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.BufferedDynamicImageResource;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.string.StringValue;

/**
 *
 * @author PRanjan3
 */
public class PublicProfilePage extends WebPage {

    private StringValue username;

    public PublicProfilePage(PageParameters parameters) {
        super(parameters);
        username = parameters.get("username");
        UserManagement um = getUserManagementJPAController().findUserManagementUserName(username.toString());
//        if(um == null){
//            add(new Label("profile","User does not exists"));
//        }else{
//            add(new Label("profile",um.getProfile().toString()));
//        }
        addProfileDetails();
        addUpdateList();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setStatelessHint(true);
    }

    private void addProfileDetails() {
        final UserManagement um = getUserManagementJPAController().findUserManagementUserName(username.toString());

        Profile p = um.getProfile();
        add(new Label("fullName", p.getFullName()));
        add(new Label("email", p.getEmail()));
        add(new Label("location", p.getLocation()));
        add(new Label("about", p.getAbout()));
        add(new Label("overview", p.getOverview()));
        add(new Label("technologies", p.getTechnologies()));

    }

    /**
     * @return Gets shared image component
     */
    public DynamicImageResource getImageResource(final byte[] mediaFile) {

        return new DynamicImageResource() {
            @Override
            protected byte[] getImageData(IResource.Attributes atrbts) {
                return mediaFile;

            }
        };

    }

    private void addUpdateList() {

        final UserManagement um = getUserManagementJPAController().findUserManagementUserName(username.toString());

        List<Updates> updates = um == null || um.getProfile() == null || um.getProfile().getUpdates() == null ? new ArrayList<Updates>() : um.getProfile().getUpdates();
        ListView updateLV = new ListView("updateList", updates) {
            @Override
            protected void populateItem(ListItem li) {
                try {
                    Updates up = (Updates) li.getModelObject();
                    li.add(new Label("updateTitle", up.getUpdateTitle()));
                    li.add(new Label("updateText", up.getUpdateText()));
                    if (up.getMediaFileName() == null || up.getMediaFileName().isEmpty()) {
                        Image img = new Image("mediaFile", new Model());
                        img.setVisible(false);
                        li.add(img);

                    } else if (up.getMediaFileName() != null && up.getMediaFile() != null) {
                        System.out.println("*********************+++++++++++++++++++++++++++++++++++++++++++*****************");
                        li.add(new Image("mediaFile", getImageResource(up.getMediaFile())));
                    }
//                       li.add(new Image("mediaFile",new Model("logo.png")));

                    li.add(new Label("timeAgo", up.getTimeAgo()));
                    li.add(new Label("timesLiked", up.getTimesLiked()));
                    li.add(new Label("timesFavorited", up.getTimesFavorited()));
                } catch (Exception ex) {
                    Logger.getLogger(PublicProfilePage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        add(updateLV);
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
