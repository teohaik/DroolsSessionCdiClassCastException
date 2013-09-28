package org.jboss.ddoyle.brms.cep.ha.drools.session;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CDI producer which produces the {@link KieSession} for this application.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
@ApplicationScoped
public class SessionProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(SessionProducer.class);

	private KieSession kieSession;
	
	@PostConstruct
	public void postConstruct() {
		KieServices kieServices = KieServices.Factory.get();
		LOGGER.debug("Bootstrapping JBoss BRMS KIE services.");
		KieContainer kieContainer = kieServices.getKieClasspathContainer();
		kieSession = kieContainer.newKieSession();
	}
	
	@Produces
	@EventSession
	@ApplicationScoped
	public KieSession getKieSession() {
		return kieSession;
	}
	

}
