package models;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import dao.Emf;
import exceptions.NonexistentEntityException;

import javax.persistence.*;

/**
 * @author Sissoko
 * @date 8 févr. 2014 05:01:30
 */
@MappedSuperclass
public abstract class DaoImp {

    public static EntityManager em;


    /**
     * @return the id
     */
    public abstract Object getId();

    public static EntityManager getEntityManager() {
        if (null == em || !em.isOpen()) {
            em = Emf.getInstance().createEntityManager();
        }
        return em;
    }

    public <T extends DaoImp> void create() {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(this);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersistenceException(e);
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public <T extends DaoImp> void edit() {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(this);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends DaoImp> void destroy() throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            T t;
            try {
                t = (T) em.getReference(getClass(), getId());
                t.getId();
            } catch (EntityNotFoundException enfe) {
                em.getTransaction().rollback();
                throw new NonexistentEntityException("The "
                        + getClass().getCanonicalName() + " with id " + getId()
                        + " no longer exists.", enfe);
            }
            em.remove(t);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends DaoImp> T save() {
        if (getId() == null) {
            create();
            return (T) this;
        } else {
            try {
                edit();
                return (T) this;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new PersistenceException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends DaoImp> T delete() {
        try {
            destroy();
        } catch (NonexistentEntityException e) {
            throw new PersistenceException(e);
        }
        return (T) this;
    }

    public static <T extends DaoImp> T findById(Class<T> clazz, Object id) {
        EntityManager em = getEntityManager();
        try {
            return (T) em.find(clazz, id);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DaoImp)) return false;

        DaoImp daoImp = (DaoImp) o;

        return getId() != null ? getId().equals(daoImp.getId()) : daoImp.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }


}