package com.example.tdd.integrationtest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.tdd.model.Car;
import com.example.tdd.repository.CarRepository;
import com.example.tdd.service.CarService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
// @AutoConfigureTestDatabase will use embedded H2 database 
@AutoConfigureTestDatabase
public class CachingTest {

	@Autowired
	private CarService carService;

	@MockBean
	private CarRepository carRepository;

	@Test
	public void testCache() {

		when(carRepository.findByName("prius")).thenReturn(new Car("prius", "hybrid"));

		carService.getCarDetails("prius");
		carService.getCarDetails("prius");
		carService.getCarDetails("prius");
		carService.getCarDetails("prius");
		carService.getCarDetails("prius");
		carService.getCarDetails("prius");

		verify(carRepository, times(1)).findByName("prius");

	}

}
