package org.jboss.ddoyle.brms.cdi;

import java.io.File;
import java.io.IOException;

import org.jboss.ddoyle.brms.cep.ha.drools.session.SessionManager;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Small reproducer test which shows the ClassCastException problem when persisting a CDI (Weld) proxied KieSession.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class CdiSessionPersistenceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CdiSessionPersistenceTest.class);

	@Test
	public void testCdiSessionPersistence() throws IOException {
		Weld weld = new Weld();
		try {
			// Initialize Weld.
			WeldContainer weldContainer = weld.initialize();

			// Create a temp file in which we want to try to persist the KieSession.
			TemporaryFolder tempFolder = new TemporaryFolder();
			tempFolder.create();
			File tempFile = tempFolder.newFile("simpleMessageKieSessionWithSaveAndLoad.sks");
			if (!tempFile.exists()) {
				tempFile.createNewFile();
			}
			String fileName = tempFile.getCanonicalPath();
			System.out.println("File location: " + tempFile.getCanonicalPath());

			SessionManager sessionManager = weldContainer.instance().select(SessionManager.class).get();
			// As this is gonna fail anyway, better grab the throwable and log the stacktrace.
			try {
				sessionManager.saveKieSession(fileName);
			} catch (Throwable t) {
				String errorMessage = "Error saving KieSession.";
				LOGGER.error(errorMessage, t);
				throw new RuntimeException(errorMessage, t);
			}
		} finally {
			// And shutdown Weld.
			weld.shutdown();
		}
	}
}
