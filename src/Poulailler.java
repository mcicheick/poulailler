import javax.persistence.PersistenceException;

import jobs.EveryMinuteJob;
import views.ModelViewer;
import controllers.UserController;

/**
 * Created by sissoko on 11/02/2016.
 */
public class Poulailler {
	public static void main(String[] args) {
		try {
			java.lang.System.setProperty("apple.laf.useScreenMenuBar", "true");
		} catch (Exception e) {
			// try the older menu bar property
			java.lang.System.setProperty("com.apple.macos.useScreenMenuBar",
					"true");
		}
		
		try {
			checkDatabase();
		} catch (PersistenceException e1) {
			HibernateDDLGenerator.generateTables();
		}
		 ModelViewer frame = new ModelViewer();
		 frame.pack();
		 frame.setBounds(20, 20, 900, 580);
		 frame.setVisible(true);
		 
		 new EveryMinuteJob().every("1d");
	}

	static void checkDatabase() throws PersistenceException {
		UserController.getInstance().select("u from User u").getResultList();
	}
}
