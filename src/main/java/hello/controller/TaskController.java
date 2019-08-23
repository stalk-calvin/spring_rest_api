package hello.controller;

import hello.model.TaskDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    @PostMapping("/task/details")
    public ResponseEntity<Void> processTask(@RequestBody TaskDetails task) {

        if(Math.random() < 0.8) {
            LOGGER.info("Erroring Task {}", task);
            throw new RuntimeException("Error Task");
        }

        LOGGER.info("Processing Task {}", task);

        return ResponseEntity.ok().build();//200
    }
}
