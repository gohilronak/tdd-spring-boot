package com.example.tdd.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.example.tdd.model.Car;
import com.example.tdd.repository.CarRepository;

/**
 * This is an integration test.
 * 
 * References:
 * https://stackoverflow.com/questions/29340286/how-to-autowire-field-in-static-beforeclass
 * https://dzone.com/articles/enhancing-spring-test
 * 
 * @SpringBootTest will load full spring web application.
 * @author Ronak
 *
 */
@RunWith(SpringIntegrationTestClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class IntegrationTest implements IntegrationTestClassListener {

	private Logger logger = LoggerFactory.getLogger(IntegrationTest.class);

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private CarRepository carRepository;

	private List<Car> cars = new ArrayList<>();

	@Override
	public void beforeClassSetup() {
		logger.info("@Before Setting up database");
		cars = Arrays.asList(new Car("prius", "hybrid"));
		cars = carRepository.saveAll(cars);
		carRepository.flush();
	}

	@Override
	public void afterClassSetup() {
		logger.info("@After cleaning database");
		carRepository.deleteAll(cars);
		carRepository.flush();
	}

	/**
	 * Tests the happy path. It should make HTTP GET request to "/cars/prius"
	 * end-point and get Car object in response with 200 OK http response status.
	 */
	@Test
	public void getCar() {
		logger.info("@Test Running actual test");
		// arrange
//		carRepository.saveAndFlush(new Car("prius", "hybrid"));
		// act
		ResponseEntity<Car> response = restTemplate.getForEntity("/cars/prius", Car.class);

		logger.info("@Test Calling API complete");
		// assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()
				.getName()).isEqualTo("prius");
		assertThat(response.getBody()
				.getType()).isEqualTo("hybrid");
		logger.info("@Test Running actual test end");

	}

	@Test
	public void test2() {
		logger.info("Test2 running");
	}

}
