package org.jboss.ddoyle.brms.cep.ha.drools.session;

import java.io.File;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.ddoyle.brms.cep.ha.management.FileKieSessionLoader;
import org.jboss.ddoyle.brms.cep.ha.management.KieSessionLoader;
import org.kie.api.runtime.KieSession;

/**
 * Manages the BRMS CEP KieSession.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
@ApplicationScoped
public class SessionManager {

	@Inject @EventSession
	private KieSession kieSession;

	public synchronized void saveKieSession(String fileName) {
		File file = new File(fileName);
		// Persist the session ...
		KieSessionLoader loader = new FileKieSessionLoader(file);
		loader.save(kieSession);		
	}

}
