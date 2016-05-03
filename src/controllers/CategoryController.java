package controllers;

import models.Category;

import java.util.List;

/**
 * Created by sissoko on 11/02/2016.
 */

public class CategoryController extends Controller<Category> {

    private static CategoryController instance;

    private CategoryController() {
    }

    public static CategoryController getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new CategoryController();
        return instance;
    }

    @Override
    public Category findById(Object id) {
        List<Category> categorys = find("select o from Category o where o.id = ?1", id).getResultList();
        if (categorys.isEmpty()) {
            return null;
        }
        return categorys.get(0);
    }

}
