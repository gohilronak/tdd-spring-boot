package com.example.tdd.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.tdd.exceptions.CarNotFoundException;
import com.example.tdd.model.Car;
import com.example.tdd.service.CarService;

/**
 * @WebMvcTest annotation will load only a small part of spring web application
 *             context instead of full spring application context as
 *             in @SpringBootTest
 * @author Ronak
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerTest {

	@Autowired
	private MockMvc mockMvc;

	/**
	 * mock CarService object. This will create a CarService object with all it's
	 * fields values set to their default values. (nulls & 0s)
	 */
	@MockBean
	private CarService carService;

	@Test
	public void getCar_shouldReturnCar() throws Exception {

		// Mock carService.getCarDetails() method which will return a fixed response.
		when(carService.getCarDetails("prius")).thenReturn(new Car("prius", "hybrid"));

		// Make request to controller through spring only.
		// This does not involve tomcat or any network call.
		ResultActions andExpect = mockMvc.perform(MockMvcRequestBuilders.get("/cars/prius"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value("prius"))
				.andExpect(jsonPath("type").value("hybrid"));
		verify(carService).getCarDetails("prius");

	}
	
	@Test
	public void getCar_notFound() throws Exception {
		when(carService.getCarDetails("prius")).thenThrow(new CarNotFoundException());
		ResultActions andExpect = mockMvc.perform(MockMvcRequestBuilders.get("/cars/prius"))
				.andExpect(status().isNotFound());
	}

}
