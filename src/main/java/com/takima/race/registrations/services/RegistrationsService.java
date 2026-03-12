package com.takima.race.registrations.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.takima.race.races.entities.Race;
import com.takima.race.races.repositories.RacesRepository;
import com.takima.race.registrations.entities.Registration;
import com.takima.race.registrations.repositories.RegistrationsRepository;
import com.takima.race.runner.entities.Runner;
import com.takima.race.runner.repositories.RunnerRepository;

@Service
public class RegistrationsService {

    private final RegistrationsRepository registrationsRepository;
    private final RunnerRepository runnerRepository;
    private final RacesRepository racesRepository;

    public RegistrationsService(RegistrationsRepository registrationsRepository, RunnerRepository runnerRepository, RacesRepository racesRepository) {
        this.registrationsRepository = registrationsRepository;
        this.runnerRepository = runnerRepository;
        this.racesRepository = racesRepository;
    }

    public List<Runner> getByRaceId(Long id) {
        if (racesRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        String.format("Race %s does not exist", id)
                );
        }
        List<Runner> runners = new ArrayList<>();
        List<Registration> registrations = registrationsRepository.findByRaceId(id);
        for (Registration registration:registrations) {
            runners.add(this.runnerRepository.getReferenceById(registration.getRunnerId()));
        }
        return runners;
        
    }

    public List<Race> getByRunnerId(Long id) {
         if (runnerRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        String.format("Runner %s does not exist", id)
                );
        }
        List<Race> racess = new ArrayList<>();
        List<Registration> registrations = registrationsRepository.findByRunnerId(id);
        for (Registration registration:registrations) {
            racess.add(this.racesRepository.getReferenceById(registration.getRaceId()));
        }
        return racess;
    }

    public Registration create(Registration registration, long raceId) {
        Optional<Race> raceOp = racesRepository.findById(raceId);
        if (racesRepository.findById(raceId).isEmpty()) {
            throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        String.format("Race %s does not exist", raceId)
                );
        }
        if (runnerRepository.findById(registration.getRunnerId()).isEmpty()) {
            throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        String.format("Runner %s does not exist", registration.getRunnerId())
                );
        }
        if (registrationsRepository.findByRunnerIdAndRaceId(registration.getRunnerId(), raceId).isPresent()) {
                throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            String.format("Runner %s already registered for this race", registration.getRunnerId() )
                    );
        }
        if (raceOp.get().getMaxParticipants() <= countByRaceId(raceId)) {
                throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            String.format("No places left", registration.getRunnerId() )
                    );
        }
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date date = new java.sql.Date(utilDate.getTime());
        registration.setRaceId(raceId);
        registration.setRegistrationDate(date);
        return registrationsRepository.save(registration);
    }

    public long countByRaceId(long raceId) {
        if (racesRepository.findById(raceId).isEmpty()) {
            throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        String.format("Race %s does not exist", raceId)
                );
        }
        return registrationsRepository.countByRaceId(raceId);
    }

   
    
}
