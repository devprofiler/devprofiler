package com.devprofiler;

import com.devprofiler.entities.Profile;
import com.devprofiler.entities.UserManagement;
import com.devprofiler.entity.controller.UserManagementJpaController;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.EmailTextField;

import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.string.Strings;

public class SignInPage extends HomePage {

    private String username;
    private String password;
    private String email;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        StatelessForm form = new StatelessForm("form") {
            @Override
            protected void onSubmit() {
                if (Strings.isEmpty(username) || Strings.isEmpty(password)) {
                    return;
                }

                boolean authResult = AuthenticatedWebSession.get().signIn(username, password);

                if (authResult) {
                    Session.get().setAttribute("username", username);
                    continueToOriginalDestination();
                    
                }
            }
        };

        form.setDefaultModel(new CompoundPropertyModel(this));

        form.add(new TextField("username"));
        form.add(new PasswordTextField("password"));

        add(form);

        StatelessForm registerForm = new StatelessForm("register_form") {
            @Override
            protected void onSubmit() {
                if (Strings.isEmpty(username) || Strings.isEmpty(password) || Strings.isEmpty(email)) {
                    return;
                }

                UserManagement um = new UserManagement();
                um.setUsername(username);
                um.setPassword(password);
                um.setEmail(email);
                um.setProfile(new Profile());
                getUserManagementJPAController().create(um);
                setResponsePage(SignInPage.class);
            }
        };

        registerForm.setDefaultModel(new CompoundPropertyModel(this));

        registerForm.add(new TextField("username"));
        registerForm.add(new PasswordTextField("password"));
        registerForm.add(new EmailTextField("email"));

        add(registerForm);
    }

    private UserManagementJpaController getUserManagementJPAController() {
        WicketApplication app = (WicketApplication) this.getApplication();
        return app.getUserManagementJPAController();
    }
}
