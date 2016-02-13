import controllers.UserController;
import models.User;

import java.util.List;

/**
 * Created by sissoko on 11/02/2016.
 */
public class Poulailler {
    public static void main(String[] args) {
        User user = new User();
        user.setEmail("cheickm.sissoko@gmail.com");
        user.setFirst_name("Cheick Mahady");
        user.setLast_name("SISSOKO");
        user.save();
//        Bande bande = new Bande();
//        bande.setArrived_date(new Date());
//        bande.setInitial_count(100);
//        bande.setUser(user);
//        bande.save();



        List<User> users = UserController.getInstance().find("select u from User u where u.email = ?1",
                "cheickm.sissoko@gmail.com").getResultList();
        System.out.println(users);
        User cheick = UserController.getInstance().findById(1L);
        System.out.println(cheick);

    }
}
