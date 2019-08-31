package api_gateway.controller;

import api_gateway.model.Question;
import api_gateway.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
class SurveyController {
    @Autowired
    private SurveyService surveyService;

    // Get all questions of surveyId
    @GetMapping("/surveys/{surveyId}/questions")
    public List<Question> retrieveQuestions(@PathVariable String surveyId) {
        return surveyService.retrieveQuestions(surveyId);
    }

    // Get specific question (questionId) of surveyId
    @GetMapping("/surveys/{surveyId}/questions/{questionId}")
    public Question retrieveDetailsForQuestion(@PathVariable String surveyId,
                                               @PathVariable String questionId) {
        return surveyService.retrieveQuestion(surveyId, questionId);
    }

    // Update specific question (questionId) of surveyId
    // Create if not found.
    @PutMapping("/surveys/{surveyId}/questions/{questionId}")
    public ResponseEntity<Void> updateQuestionToSurvey(
            @PathVariable String surveyId, @PathVariable String questionId, @RequestBody Question newQuestion) {

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{id}").buildAndExpand(questionId).toUri();

        Question q = surveyService.upsertQuestion(surveyId, questionId, newQuestion);


        // Status
        if (q == null) {
            return ResponseEntity.noContent().build();
        } else if (q.getId() == null) {
            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    // Create a new question
    @PostMapping("/surveys/{surveyId}/questions")
    public ResponseEntity<Void> addQuestionToSurvey(
            @PathVariable String surveyId, @RequestBody Question newQuestion) {

        Question question = surveyService.addQuestion(surveyId, null, newQuestion);

        if (question == null)
            return ResponseEntity.noContent().build();

        // Success - URI of the new resource in Response Header
        // Status - created
        // URI -> /surveys/{surveyId}/questions/{questionId}
        // question.getQuestionId()
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{id}").buildAndExpand(question.getId()).toUri();

        // Status
        return ResponseEntity.created(location).build();
    }

    // delete specific question (questionId) of surveyId
    @DeleteMapping("/surveys/{surveyId}/questions/{questionId}")
    public Response updateQuestionToSurvey(
            @PathVariable String surveyId, @PathVariable String questionId) {
        int deletedNum = surveyService.deleteQuestion(surveyId, questionId);
        String response = "No question deleted";
        if (deletedNum > 0) {
            response = String.valueOf(deletedNum);
        }
        return new Response(response);
    }

}