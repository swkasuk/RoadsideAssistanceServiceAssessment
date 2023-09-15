package com.assignment.ras.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.ras.persistence.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

}
