package com.takima.race.races.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.takima.race.races.entities.Race;
import com.takima.race.races.services.RacesService;
import com.takima.race.registrations.entities.Registration;
import com.takima.race.registrations.services.RegistrationsService;
import com.takima.race.runner.entities.Runner;

@RestController
@RequestMapping("/races")
public class RacesController {
    private final RacesService racesService;
    private final RegistrationsService registrationsService;

    public RacesController(RacesService racesService, RegistrationsService registrationsService) {
        this.racesService = racesService;
        this.registrationsService = registrationsService;
    }

    @GetMapping
    public List<Race> getAll(@RequestParam(required = false) String location) {
        if (location != null) {
            return racesService.getByLocation(location);
        }

        return racesService.getAll();
    }

    @GetMapping("/{id}") 
    public Race getById(@PathVariable Long id) {
        return racesService.getById(id);
    }

    @PostMapping
    public Race create(@RequestBody Race races) {
        return racesService.create(races);
    }

    @GetMapping("/{id}/participants/count")
    public long get(@PathVariable Long id) {
        return registrationsService.countByRaceId(id);
    }

    @GetMapping("/{raceId}/registrations")
    public List<Runner> getByRacesId(@PathVariable long raceId) {
        return registrationsService.getByRaceId(raceId);
    }

    @PostMapping("/{raceId}/registrations")
    public Registration create(@RequestBody Registration registration, @PathVariable long raceId) {
        return registrationsService.create(registration, raceId);
    }

    @PutMapping("/{raceId}")
    public Race update(@RequestBody Race race, @PathVariable long raceId) {
        return racesService.update(race, raceId);
    }
}
