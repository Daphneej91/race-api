package com.takima.race.runner.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takima.race.races.entities.Race;
import com.takima.race.registrations.services.RegistrationsService;
import com.takima.race.runner.entities.Runner;
import com.takima.race.runner.services.RunnerService;

@RestController
@RequestMapping("/runners")
public class RunnerController {
    private final RunnerService runnerService;
    private final RegistrationsService registrationsService;

    public RunnerController(RunnerService runnerService, RegistrationsService registrationsService) {
        this.runnerService = runnerService;
        this.registrationsService = registrationsService;
    }

    @GetMapping
    public List<Runner> getAll() {
        return runnerService.getAll();
    }

    @GetMapping("/{id}")
    public Runner getById(@PathVariable Long id) {
        return runnerService.getById(id);
    }

    @GetMapping("/{runnerId}/races")
    public List<Race> getByRunnerId(@PathVariable Long runnerId) {
        return registrationsService.getByRunnerId(runnerId);
    }

    @PostMapping
    public Runner create(
        @RequestBody Runner runner
    ) {
        return runnerService.create(runner);
    }

    @DeleteMapping("/{id}")
    public void put(@PathVariable Long id) {
        runnerService.delete(id);
    }

    @PutMapping("/{id}")
    public Runner update(@RequestBody Runner runner, @PathVariable long id){
        return runnerService.update(runner, id);
    }
}
