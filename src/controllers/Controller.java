package controllers;

import dao.Emf;
import models.DaoImp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 * Created by sissoko on 11/02/2016.
 */
public abstract class Controller<M extends DaoImp> {

    /**
     * @param id
     * @return
     */
    public M findById(Object id) {
        throw new UnsupportedOperationException("Please implements this class.");
    }

    /**
     * @param q
     * @param args
     * @return
     */
    public Query find(String q, Object... args) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery(q);
        for (int i = 1; i <= args.length; i++) {
            query.setParameter(i, args[i - 1]);
        }
        return query;
    }

    /**
     *
     * @param q
     * @param args
     * @return
     */
    public Query select(String q, Object... args) throws PersistenceException {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select " + q);
        for (int i = 1; i <= args.length; i++) {
            query.setParameter(i, args[i - 1]);
        }
        return query;
    }

    public void delete(String q, Object... args) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("delete " + q);
            for (int i = 1; i <= args.length; i++) {
                query.setParameter(i, args[i - 1]);
            }
            query.executeUpdate();
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public EntityManager getEntityManager() {
        if (null == M.em || !M.em.isOpen()) {
            M.em = Emf.getInstance().createEntityManager();
        }
        return M.em;
    }
}
