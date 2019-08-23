package hello.service;

import hello.model.Question;
import hello.model.Survey;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SurveyService {
    private static List<Survey> surveys = new ArrayList<>();
    static {
        Question question1 = new Question("Question1",
                "Largest Country in the World", "Russia", Arrays.asList(
                "India", "Russia", "United States", "China"));
        Question question2 = new Question("Question2",
                "Most Populus Country in the World", "China", Arrays.asList(
                "India", "Russia", "United States", "China"));
        Question question3 = new Question("Question3",
                "Highest GDP in the World", "United States", Arrays.asList(
                "India", "Russia", "United States", "China"));
        Question question4 = new Question("Question4",
                "Second largest english speaking country", "India", Arrays
                .asList("India", "Russia", "United States", "China"));

        List<Question> questions = new ArrayList<>(Arrays.asList(question1,
                question2, question3, question4));

        Survey survey = new Survey("Survey1", "My Favorite Survey",
                "Description of the Survey", questions);

        surveys.add(survey);
    }

    public List<Survey> retrieveAllSurveys() {
        return surveys;
    }

    public Survey retrieveSurvey(String surveyId) {
        for (Survey survey : surveys) {
            if (survey.getId().equals(surveyId)) {
                return survey;
            }
        }
        return null;
    }

    public List<Question> retrieveQuestions(String surveyId) {
        Survey survey = retrieveSurvey(surveyId);

        if (survey == null) {
            return null;
        }

        return survey.getQuestions();
    }

    public void upsertQuestion(String surveyId, String questionId, Question newQuestion) {
        Survey survey = retrieveSurvey(surveyId);

        if (survey == null) {
            return;
        }

        for (Question question : survey.getQuestions()) {
            if (question.getId().equals(questionId)) {
                question.copy(newQuestion);
                return;
            }
        }

        addQuestion(surveyId, questionId, newQuestion);
    }

    public Question retrieveQuestion(String surveyId, String questionId) {
        Survey survey = retrieveSurvey(surveyId);

        if (survey == null) {
            return null;
        }

        for (Question question : survey.getQuestions()) {
            if (question.getId().equals(questionId)) {
                return question;
            }
        }

        return null;
    }

    private SecureRandom random = new SecureRandom();

    public Question addQuestion(String surveyId, String questionId, Question question) {
        Survey survey = retrieveSurvey(surveyId);

        if (survey == null) {
            return null;
        }

        if (questionId != null) {
            question.setId(questionId);
        } else {
            String randomId = new BigInteger(130, random).toString(32);
            question.setId(randomId);
        }
        survey.getQuestions().add(question);

        return question;
    }

    public int deleteQuestion(String surveyId, String questionId) {
        Survey survey = retrieveSurvey(surveyId);

        if (survey == null) {
            return -1;
        }

        List<Question> newQuestions = new ArrayList<>();
        int counter = 0;
        for (Question question : survey.getQuestions()) {
            if (question.getId().equals(questionId)) {
                counter++;
            } else {
                newQuestions.add(question);
            }
        }
        survey.setQuestions(newQuestions);
        return counter;
    }
}