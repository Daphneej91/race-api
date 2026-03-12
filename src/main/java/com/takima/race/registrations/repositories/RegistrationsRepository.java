package com.takima.race.registrations.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takima.race.registrations.entities.Registration;

@Repository
public interface RegistrationsRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByRaceId(Long id);
    List<Registration> findByRunnerId(Long id);
    long countByRaceId(Long raceId);
    Optional<Registration> findByRunnerIdAndRaceId(long runnerId, long raceId);
}