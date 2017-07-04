
package com.devprofiler;

import com.devprofiler.entities.ProfileJpaController;
import com.devprofiler.entities.UserManagement;
import com.devprofiler.entities.UserManagementJpaController;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

/**
 *
 * @author PRanjan3
 */
public class Article extends WebPage{

    
    public Article(PageParameters parameters) {
        super(parameters);
        StringValue username = parameters.get("username");
        UserManagement um = getUserManagementJPAController().findUserManagementUserName(username.toString());
//        if(um == null){
//            add(new Label("profile","User does not exists"));
//        }else{
//            add(new Label("profile",um.getProfile().toString()));
//        }
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
