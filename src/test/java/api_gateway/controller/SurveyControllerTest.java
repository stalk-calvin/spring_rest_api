package api_gateway.controller;

import api_gateway.model.Question;
import api_gateway.service.SurveyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = SurveyController.class, secure = false)
public class SurveyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SurveyService surveyService;

    @Test
    public void shouldGetQuestionsForSurvey() throws Exception {
        String surveyID = "Survey1";

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

        List<Question> mockQuestions = new ArrayList<>(Arrays.asList(question1,
                question2, question3, question4));
        when(surveyService.retrieveQuestions(eq(surveyID))).thenReturn(mockQuestions);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/surveys/" + surveyID + "/questions").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        String expected = "[{\"id\":\"Question1\"},{\"id\":\"Question2\"},{\"id\":\"Question3\"},{\"id\":\"Question4\"}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void shouldNotGetQuestionsForSurvey() throws Exception {
        String surveyID = "Survey1";

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

        List<Question> mockQuestions = new ArrayList<>(Arrays.asList(question1,
                question2, question3, question4));
        when(surveyService.retrieveQuestions(eq(surveyID))).thenReturn(mockQuestions);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/surveys/xxx/questions").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        String expected = "[]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void shouldGetASpecificQuestion() throws Exception {
        String surveyID = "Survey1";
        String questionID = "Question1";

        Question mockQuestion = new Question("Question1",
                "Largest Country in the World", "Russia", Arrays.asList(
                "India", "Russia", "United States", "China"));
        when(surveyService.retrieveQuestion(eq(surveyID), eq(questionID))).thenReturn(mockQuestion);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/surveys/Survey1/questions/Question1").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        String expected = "{\"id\":\"Question1\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void shouldNotGetASpecificQuestion() throws Exception {
        String surveyID = "Survey1";
        String questionID = "Question1";

        Question mockQuestion = new Question("Question1",
                "Largest Country in the World", "Russia", Arrays.asList(
                "India", "Russia", "United States", "China"));
        when(surveyService.retrieveQuestion(eq(surveyID), eq(questionID))).thenReturn(mockQuestion);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/surveys/Survey1/questions/xxx").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        assertEquals("", result.getResponse().getContentAsString());
    }

    @Test
    public void shouldCreateANewQuestion() throws Exception {
        String surveyID = "Survey1";

        String createJSON = new StringBuilder()
                .append("{\n")
                .append("    \"description\": \"Calvin Lee\",\n")
                .append("    \"correctAnswer\": \"is a badass\",\n")
                .append("    \"options\": [\n")
                .append("        \"India\",\n")
                .append("        \"Russia\",\n")
                .append("        \"United States\",\n")
                .append("        \"China\"\n")
                .append("    ]\n")
                .append("}").toString();

        Question mockQuestion = new Question(null,
                "Calvin Lee", "is a badass", Arrays.asList(
                "India", "Russia", "United States", "China"));
        when(surveyService.addQuestion(eq(surveyID), eq(null), eq(mockQuestion))).thenReturn(mockQuestion);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/surveys/" + surveyID + "/questions")
                .accept(MediaType.APPLICATION_JSON).content(createJSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(201, result.getResponse().getStatus()); // 201 Created
        assertEquals("http://localhost/surveys/Survey1/questions/", result.getResponse().getHeader("location"));
    }

    @Test
    public void shouldNotCreateANewQuestion() throws Exception {
        String surveyID = "Survey1";

        String createJSON = new StringBuilder()
                .append("{\n")
                .append("    \"description\": \"Calvin Lee\",\n")
                .append("    \"correctAnswer\": \"is a badass\",\n")
                .append("    \"options\": [\n")
                .append("        \"India\",\n")
                .append("        \"Russia\",\n")
                .append("        \"United States\",\n")
                .append("        \"China\"\n")
                .append("    ]\n")
                .append("}").toString();

        Question mockQuestion = new Question(null,
                "Calvin Lee", "is a badass", Arrays.asList(
                "India", "Russia", "United States", "China"));
        when(surveyService.addQuestion(eq(surveyID), eq(null), eq(mockQuestion))).thenReturn(mockQuestion);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/surveys/xxx/questions")
                .accept(MediaType.APPLICATION_JSON).content(createJSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(204, result.getResponse().getStatus()); // 204 No Content
        assertEquals(null, result.getResponse().getHeader("location"));
    }

    @Test
    public void shouldCreateQuestionUsingPUT() throws Exception {
        String surveyID = "Survey1";
        String questionID = "Question1";

        String createJSON = new StringBuilder()
                .append("{\n")
                .append("    \"description\": \"Calvin Lee\",\n")
                .append("    \"correctAnswer\": \"is a badass\",\n")
                .append("    \"options\": [\n")
                .append("        \"India\",\n")
                .append("        \"Russia\",\n")
                .append("        \"United States\",\n")
                .append("        \"China\"\n")
                .append("    ]\n")
                .append("}").toString();

        Question mockQuestion = new Question(null,
                "Calvin Lee", "is a badass", Arrays.asList(
                "India", "Russia", "United States", "China"));
        when(surveyService.upsertQuestion(eq(surveyID), eq(questionID), eq(mockQuestion))).thenReturn(mockQuestion);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/surveys/"+surveyID+"/questions/"+questionID)
                .accept(MediaType.APPLICATION_JSON).content(createJSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(201, result.getResponse().getStatus()); // 201 Created
        assertEquals("http://localhost/surveys/Survey1/questions/Question1/Question1", result.getResponse().getHeader("location"));
    }

    @Test
    public void shouldUpdateQuestionUsingPUT() throws Exception {
        String surveyID = "Survey1";
        String questionID = "Question1";

        String createJSON = new StringBuilder()
                .append("{\n")
                .append("   \"id\":\"Question1\",")
                .append("    \"description\": \"Calvin Lee\",\n")
                .append("    \"correctAnswer\": \"is a badass\",\n")
                .append("    \"options\": [\n")
                .append("        \"India\",\n")
                .append("        \"Russia\",\n")
                .append("        \"United States\",\n")
                .append("        \"China\"\n")
                .append("    ]\n")
                .append("}").toString();

        Question mockQuestion = new Question("Question1",
                "Calvin Lee", "is a badass", Arrays.asList(
                "India", "Russia", "United States", "China"));
        when(surveyService.upsertQuestion(eq(surveyID), eq(questionID), eq(mockQuestion))).thenReturn(mockQuestion);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/surveys/"+surveyID+"/questions/"+questionID)
                .accept(MediaType.APPLICATION_JSON).content(createJSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(200, result.getResponse().getStatus()); // 200 OK
    }
}

