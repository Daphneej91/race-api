package com.takima.race.runner.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takima.race.runner.entities.Runner;

@Repository
public interface RunnerRepository extends JpaRepository<Runner, Long> {
     List<Runner> findByFirstName(String firstName);
}