package controllers;

import models.Remember;

import java.util.List;

/**
 * Created by sissoko on 11/02/2016.
 */

public class RememberController extends Controller<Remember> {

    private static RememberController instance;

    private RememberController() {
    }

    public static RememberController getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new RememberController();
        return instance;
    }

    @Override
    public Remember findById(Object id) {
        List<Remember> remembers = find("select o from Remember o where o.id = ?1", id).getResultList();
        if (remembers.isEmpty()) {
            return null;
        }
        return remembers.get(0);
    }

}
