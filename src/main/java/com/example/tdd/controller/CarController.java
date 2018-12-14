package com.example.tdd.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.tdd.model.Car;
import com.example.tdd.service.CarService;

@RestController
public class CarController {

	private CarService carService;

	public CarController(CarService carService) {
		this.carService = carService;
	}

	@GetMapping("cars/{name}")
	public Car getCar(@PathVariable("name") String name) {
		return carService.getCarDetails(name);
	}

}
