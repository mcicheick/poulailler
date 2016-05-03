package controllers;

import models.User;

import java.util.List;

/**
 * Created by sissoko on 11/02/2016.
 */

public class UserController extends Controller<User> {

    private static UserController instance;

    private UserController() {
    }

    public static UserController getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new UserController();
        return instance;
    }

    @Override
    public User findById(Object id) {
        List<User> users = select("o from Client o where o.id = ?1", id).getResultList();
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    /**
     *
     * @param telephone
     * @return
     */
    public User findByTelephone(String telephone) {
        List<User> users = select("u from User u where u.telephone = ?1", telephone).getResultList();
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

}
