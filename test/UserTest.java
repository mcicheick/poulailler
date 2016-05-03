import controllers.UserController;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.PersistenceException;

import static org.junit.Assert.assertNotNull;

/**
 * Created by sissoko on 11/02/2016.
 */
public class UserTest {

    static String[] EMAILS = {
            "cheickm.sissoko@gmail.com",
            "abdoulaye.maiga@gmail.com",
            "yacouba.sagara@gmail.com",
            "mina.mint@gmail.com"
    };

    static String[] NAMES = {
            "Cheick Mahady Sissoko",
            "Abdoulaye Maiga",
            "Yacouba Sagara",
            "Mina Mint Mohamaed"
    };

    UserController controller;

    public static void loadData() {
        for (int i = 0; i < EMAILS.length; i++) {
            User user = new User();
            user.setTelephone(EMAILS[i]);
            user.setFirst_name(NAMES[i]);
            user.setLast_name("");
            user.save();
        }
    }

    @Before
    public void setUp() {
        controller = UserController.getInstance();
        loadData();
    }

    @Test
    public void user_must_be_found_when_email_is_correct() {
        User user = controller.findByTelephone("cheickm.sissoko@gmail.com");
        assertNotNull(user);
    }

    @Test(expected = PersistenceException.class)
    public void an_exception_must_be_thrown_when_duplicated_email() throws PersistenceException {
        User user = new User();
        user.setTelephone("cheickm.sissoko@gmail.com");
        user.save();
    }


    @After
    public void tearDown() {
        controller.delete("User");
    }
}
