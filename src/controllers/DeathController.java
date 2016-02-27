package controllers;

import models.Death;

import java.util.List;

/**
 * Created by sissoko on 11/02/2016.
 */

public class DeathController extends Controller<Death> {

    private static DeathController instance;

    private DeathController() {
    }

    public static DeathController getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new DeathController();
        return instance;
    }

    @Override
    public Death findById(Object id) {
        List<Death> deaths = find("select o from Death o where o.id = ?1", id).getResultList();
        if (deaths.isEmpty()) {
            return null;
        }
        return deaths.get(0);
    }

}
