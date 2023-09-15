package com.assignment.ras.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.ras.persistence.entity.AssistantEntity;

public interface AssistantRepository extends JpaRepository<AssistantEntity, Integer> {

	public Optional<AssistantEntity> findByAssistantID(int id);

}
