package views;

import models.Model;

/**
 * Created by sissoko on 14/02/2016.
 */
public interface ModelListener {
    /**
     *
     * @param model
     */
    void fireModel(Model model);

    /**
     *
     * @param link
     */
    void fireEvent(String link);
}
