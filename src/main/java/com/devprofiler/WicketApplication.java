package com.devprofiler;




import com.devprofiler.entity.controller.ProfileJpaController;
import com.devprofiler.entity.controller.UpdatesJpaController;
import com.devprofiler.entity.controller.UserManagementJpaController;
import com.devprofiler.utils.Configuration;
import javax.persistence.Persistence;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;

public class WicketApplication extends AuthenticatedWebApplication
{    	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<HomePage> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() 
	{		
		return BasicAuthenticationSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() 
	{
		return SignInPage.class;
	}
	
	@Override
	protected void init()
	{   
            mountPage("publicprofile", PublicProfilePage.class);
             mountPage("article", Article.class);
	}
        
        
        public ProfileJpaController getProfileJPAController(){
            return new ProfileJpaController(Persistence.createEntityManagerFactory(Configuration.getPU()));
        }
        
        public UserManagementJpaController getUserManagementJPAController(){
            return new UserManagementJpaController(Persistence.createEntityManagerFactory(Configuration.getPU()));
        }
        public UpdatesJpaController getUpdatesJPAController(){
            return new UpdatesJpaController(Persistence.createEntityManagerFactory(Configuration.getPU()));
        }
}
