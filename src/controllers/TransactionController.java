package controllers;

import models.Transaction;

import java.util.List;

/**
 * Created by sissoko on 11/02/2016.
 */
public class TransactionController extends Controller<Transaction> {
    private static TransactionController instance;

    private TransactionController() {
    }

    public static TransactionController getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new TransactionController();
        return instance;
    }

    @Override
    public Transaction findById(Object id) {
        List<Transaction> transactions = find("select o from Transaction o where o.id = ?1", id).getResultList();
        if (transactions.isEmpty()) {
            return null;
        }
        return transactions.get(0);
    }
}
