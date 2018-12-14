package com.example.tdd.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.tdd.model.Car;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CarRepositoryTest {

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	public void getCar_returnsCarDetails() throws Exception {
		Car savedCar = testEntityManager.persistFlushFind(new Car("prius", "hybrid"));
		Car car = carRepository.findByName("prius");
		assertEquals(car.getName(), savedCar.getName());
		assertEquals(car.getType(), savedCar.getType());
		assertNotNull(car.getId());
	}
}
