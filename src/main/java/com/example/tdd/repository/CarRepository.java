package com.example.tdd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tdd.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

	public Car findByName(String name);
	
}
