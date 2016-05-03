import org.hibernate.cfg.Configuration;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HibernateDDLGenerator {
	
	public static void main(String[] args) {
		generateTables();
	}

	public static void generateTables() {

		String persistenceUnitName = "default";

		// Ejb3Configuration cfig = new Ejb3Configuration();
		Ejb3Configuration configured = new Ejb3Configuration().configure(
				persistenceUnitName, null);
		Configuration cfg = configured.getHibernateConfiguration();
		SchemaExport schemaExport = new SchemaExport(cfg);
		schemaExport.setDelimiter(";");

		File dir = new File("db/evolutions");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		int i = 1;
		while (new File(dir, i + ".sql").exists()) {
			i++;
		}
		schemaExport.setOutputFile(new File(dir, i + ".sql").getPath());
		boolean consolePrint = true;
		boolean exportInDatabase = true;
		boolean justDrop = false;
		boolean justCreate = false;
		schemaExport.execute(consolePrint, exportInDatabase, justDrop,
				justCreate);
		int j = 0;
		if (i > 1) {
			j = i - 1;
		}

		if (j > 0) {
			File fi = new File(dir, i + ".sql");
			File fj = new File(dir, j + ".sql");
			try {
				MessageDigest mdis = MessageDigest.getInstance("MD5");
				DigestInputStream dis = new DigestInputStream(
						new FileInputStream(fi), mdis);
				byte[] bytes = new byte[128];
				while (dis.read(bytes, 0, bytes.length) != -1) {
					;
				}
				String hashi = new String(dis.getMessageDigest().digest());
				MessageDigest mdjs = MessageDigest.getInstance("MD5");
				DigestInputStream djs = new DigestInputStream(
						new FileInputStream(fj), mdjs);
				bytes = new byte[128];
				while (djs.read(bytes, 0, bytes.length) != -1) {
					;
				}

				String hashj = new String(djs.getMessageDigest().digest());

				if (hashi.equals(hashj)) {
					fi.delete();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
