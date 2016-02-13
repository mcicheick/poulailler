package controllers;

import models.Client;

import java.util.List;

/**
 * Created by sissoko on 11/02/2016.
 */
public class ClientController extends Controller<Client> {
    private static ClientController instance;

    private ClientController() {
    }

    public static ClientController getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new ClientController();
        return instance;
    }

    @Override
    public Client findById(Object id) {
        List<Client> clients = find("select o from Client o where o.id = ?1", id).getResultList();
        if (clients.isEmpty()) {
            return null;
        }
        return clients.get(0);
    }
}
