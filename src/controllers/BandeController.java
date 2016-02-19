package controllers;

import models.Bande;

import java.util.List;

/**
 * Created by sissoko on 11/02/2016.
 */

public class BandeController extends Controller<Bande> {

    private static BandeController instance;

    private BandeController() {
    }

    public static BandeController getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new BandeController();
        return instance;
    }

    @Override
    public Bande findById(Object id) {
        List<Bande> bandes = find("select o from Bande o where o.id = ?1", id).getResultList();
        if (bandes.isEmpty()) {
            return null;
        }
        return bandes.get(0);
    }

}
