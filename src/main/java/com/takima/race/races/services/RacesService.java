package com.takima.race.races.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.takima.race.races.entities.Race;
import com.takima.race.races.repositories.RacesRepository;

@Service
public class RacesService {
    private final RacesRepository racesRepository;

    public RacesService(RacesRepository racesRepository) {
        this.racesRepository = racesRepository;
    }

    public List<Race> getAll() {
        return this.racesRepository.findAll();
    }

    public List<Race> getByLocation(String location) {
        return this.racesRepository.findByLocationIgnoreCase(location);
    }

    public Race getById(long id) {
        return racesRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Race %s not found", id)
                )
        );
    }

    public Race create(Race races) {
        return racesRepository.save(races);
    }

    public Race update(Race race, long id){
        getById(id);
        Race oldRace = racesRepository.getReferenceById(id);
        oldRace.setLocation(race.getLocation());
        oldRace.setDate(race.getDate());
        oldRace.setMaxParticipants(race.getMaxParticipants());
        oldRace.setName(race.getName());
        return racesRepository.save(oldRace);
    }

}
