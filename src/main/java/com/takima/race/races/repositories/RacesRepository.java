package com.takima.race.races.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takima.race.races.entities.Race;


@Repository
public interface  RacesRepository extends JpaRepository<Race, Long> {
    List<Race> findByLocationIgnoreCase(String location);
}
