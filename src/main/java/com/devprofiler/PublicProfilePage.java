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
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
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
addUpdateList();
    }

       private void addUpdateList() {
 

        System.out.println(username);
        final UserManagement um = getUserManagementJPAController().findUserManagementUserName(username.toString());
        
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
