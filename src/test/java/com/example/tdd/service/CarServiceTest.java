package com.example.tdd.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.tdd.exceptions.CarNotFoundException;
import com.example.tdd.model.Car;
import com.example.tdd.repository.CarRepository;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

	@Mock
	private CarRepository carRepository;

	private CarService carService;

	@Before
	public void setup() throws Exception {
		carService = new CarService(carRepository);
	}

	@Test
	public void getCarDetails_returnsCarInfo() {
		when(carRepository.findByName("prius")).thenReturn(new Car("prius", "hybrid"));

		Car car = carService.getCarDetails("prius");

		assertEquals("prius", car.getName());
		assertEquals("hybrid", car.getType());
	}

	@Test(expected = CarNotFoundException.class)
	public void getCarDetails_carNotFound() {
		when(carRepository.findByName("prius")).thenReturn(null);

		Car car = carService.getCarDetails("prius");
	}
}
