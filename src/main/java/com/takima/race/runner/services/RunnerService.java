package com.takima.race.runner.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.takima.race.runner.entities.Runner;
import com.takima.race.runner.repositories.RunnerRepository;

@Service
public class RunnerService {

    private final RunnerRepository runnerRepository;

    public RunnerService(RunnerRepository runnerRepository) {
        this.runnerRepository = runnerRepository;
    }

    public List<Runner> getAll() {
        return runnerRepository.findAll();
    }

    public Runner getById(Long id) {
        return runnerRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Runner %s not found", id)
                )
        );
    }

    public Runner create(Runner runner) {
        if(runner.getEmail().contains("@") ==false) {
            throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        String.format("invalid email")
                );
        }
        return runnerRepository.save(runner);
    }

    public void  delete(long id) {
        getById(id);
        runnerRepository.deleteById(id);
    }

    public Runner update(Runner runner, long id){
        getById(id);
        if(runner.getEmail().contains("@") ==false) {
            throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        String.format("invalid email")
                );
        }
        Runner oldRunner = runnerRepository.getReferenceById(id);
        oldRunner.setFirstName(runner.getFirstName());
        oldRunner.setAge(runner.getAge());
        oldRunner.setEmail(runner.getEmail());
        oldRunner.setLastName(runner.getLastName());
        return runnerRepository.save(oldRunner);
    }
    
}
