package dao;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 
 * @author Sissoko
 * @date 8 févr. 2014 05:01:23
 */
public class Emf {

	private static EntityManagerFactory instance;

	/**
     *
     */
	private Emf() {
	}

	public static EntityManagerFactory getInstance() {
		if (null == instance || !instance.isOpen()) {
			instance = Persistence.createEntityManagerFactory("default");
		}
		return instance;
	}
}
