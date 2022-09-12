package com.works.repositories;

import com.works.entities.Fault;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaultRepository extends JpaRepository<Fault, Long> {
}