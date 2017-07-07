package com.devprofiler;

import com.devprofiler.entities.Profile;
import com.devprofiler.entity.controller.ProfileJpaController;
import com.devprofiler.entities.UserManagement;
import com.devprofiler.entity.controller.UserManagementJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author PRanjan3
 */
public class CreateTestData {

    private UserManagementJpaController umjpactrl;
    private ProfileJpaController pjpactrl;

    public CreateTestData() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("devprofiler_pu");
        umjpactrl = new UserManagementJpaController(emf);
        pjpactrl = new ProfileJpaController(emf);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void createTestDatabase() {

        UserManagement um = new UserManagement();
        um.setPassword("ranjan");
        um.setUsername("ranjan");
        um.setEmail("test@test.com");
        Profile p = new Profile();
        p.setFirstName("priya");
        p.setLastName("ranjan");

        um.setProfile(p);

        umjpactrl.create(um);

    }
}
