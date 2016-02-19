package controllers;

import models.Depense;

import java.util.List;

/**
 * Created by sissoko on 11/02/2016.
 */
public class DepenseController extends Controller<Depense> {
    private static DepenseController instance;

    private DepenseController() {
    }

    public static DepenseController getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new DepenseController();
        return instance;
    }

    @Override
    public Depense findById(Object id) {
        List<Depense> clients = select("o from Depense o where o.id = ?1", id).getResultList();
        if (clients.isEmpty()) {
            return null;
        }
        return clients.get(0);
    }
}
