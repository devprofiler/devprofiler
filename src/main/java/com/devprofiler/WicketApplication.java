package com.devprofiler;




import com.devprofiler.entities.ProfileJpaController;
import com.devprofiler.entities.UpdatesJpaController;
import com.devprofiler.entities.UserManagementJpaController;
import javax.persistence.Persistence;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see org.wicketTutorial.basicauth.Start#main(String[])
 */
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
            return new ProfileJpaController(Persistence.createEntityManagerFactory("devprofiler_pu"));
        }
        
        public UserManagementJpaController getUserManagementJPAController(){
            return new UserManagementJpaController(Persistence.createEntityManagerFactory("devprofiler_pu"));
        }
        public UpdatesJpaController getUpdatesJPAController(){
            return new UpdatesJpaController(Persistence.createEntityManagerFactory("devprofiler_pu"));
        }
}
