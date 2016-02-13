package controllers;

import models.Payment;

import java.util.List;

/**
 * Created by sissoko on 11/02/2016.
 */
public class PaymentController extends Controller<Payment> {
    private static PaymentController instance;

    private PaymentController() {
    }

    public static PaymentController getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new PaymentController();
        return instance;
    }

    @Override
    public Payment findById(Object id) {
        List<Payment> clients = select("o from Payment o where o.id = ?1", id).getResultList();
        if (clients.isEmpty()) {
            return null;
        }
        return clients.get(0);
    }
}
