package controllers;

import models.Observation;

import java.util.List;

/**
 * Created by sissoko on 11/02/2016.
 */

public class ObservationController extends Controller<Observation> {

    private static ObservationController instance;

    private ObservationController() {
    }

    public static ObservationController getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new ObservationController();
        return instance;
    }

    @Override
    public Observation findById(Object id) {
        List<Observation> observations = find("select o from Observation o where o.id = ?1", id).getResultList();
        if (observations.isEmpty()) {
            return null;
        }
        return observations.get(0);
    }

}
