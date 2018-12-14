package com.example.tdd.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.tdd.exceptions.CarNotFoundException;
import com.example.tdd.model.Car;
import com.example.tdd.repository.CarRepository;

@Service
public class CarService {

	CarRepository carRepository;

	public CarService(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	@Cacheable("cars")
	public Car getCarDetails(String name) {
		Car car = carRepository.findByName(name);
		if (car == null) {
			throw new CarNotFoundException();
		}
		return car;
	}

}
