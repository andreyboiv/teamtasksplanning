package com.boivalenko.businessapp.web.app.controller;

import com.boivalenko.businessapp.web.app.entity.Task;
import com.boivalenko.businessapp.web.app.search.TaskSearchValues;
import com.boivalenko.businessapp.web.app.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/task")
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/save")
    public ResponseEntity<Task> save(@RequestBody Task task) {
        return this.taskService.save(task);
    }

    @PutMapping("/update")
    public ResponseEntity<Task> update(@RequestBody Task task) {
        return this.taskService.update(task);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Task> deleteById(@PathVariable("id") Long id) {
        return this.taskService.deleteById(id);
    }

    @PostMapping("/findById")
    public ResponseEntity<Task> findById(@RequestBody Long id) {
        return this.taskService.findById(id);
    }

    @PostMapping("/findAll")
    public ResponseEntity<List<Task>> findAll() {
        return this.taskService.findAll();
    }

    @PostMapping("/findAllByEmail")
    public ResponseEntity<List<Task>> findAllByEmail(@RequestBody String email) {
        return this.taskService.findAllByEmail(email);
    }

    @PostMapping("/findAllByEmailQuery")
    public ResponseEntity<List<Task>> findAllByEmailQuery(@RequestBody TaskSearchValues taskSearchValues) {
        return this.taskService.findAllByEmailQuery(taskSearchValues.getTitle(), taskSearchValues.getEmail());
    }

    // Suche nach beliebigen Parameter
    @PostMapping("/search")
    public ResponseEntity<Page<Task>> search(@RequestBody TaskSearchValues taskSearchValues) throws ParseException {
        return this.taskService.findByParams(taskSearchValues);
    }

}
