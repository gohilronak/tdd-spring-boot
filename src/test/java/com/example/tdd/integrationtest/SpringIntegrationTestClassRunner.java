package com.example.tdd.integrationtest;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class SpringIntegrationTestClassRunner extends SpringJUnit4ClassRunner {

	private IntegrationTestClassListener integrationTestClassListener;

	public SpringIntegrationTestClassRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}

	@Override
	protected Object createTest() throws Exception {
		Object test = super.createTest();
		// Note that JUnit4 will call this createTest() multiple times for each
		// test method, so we need to ensure to call "beforeClassSetup" only once.
		if (test instanceof IntegrationTestClassListener && integrationTestClassListener == null) {
			integrationTestClassListener = (IntegrationTestClassListener) test;
			integrationTestClassListener.beforeClassSetup();
		}
		return test;
	}

	@Override
	public void run(RunNotifier notifier) {
		super.run(notifier);
		if (integrationTestClassListener != null)
			integrationTestClassListener.afterClassSetup();
	}

}
