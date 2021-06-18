import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static configuration.Before.apiRequest;
import static configuration.Before.createRequestSpecification;
import static io.restassured.RestAssured.given;

public class ProjectTest {

    @BeforeClass
    public void loadRequestSpecification() {
        createRequestSpecification();
    }

    @Test
    public void getProject() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("projects");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void getAProject() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}")
                .addPathParam("projectId", "2504464");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Project project = apiResponse.getBody(Project.class);

        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(project.getKind(), "project");
        apiResponse.validateBodySchema("schemas/project.json");
    }

    @Test
    public void getPeopleInProject() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/my/people")
                .addQueryParam("project_id", "2504464");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void createAProject() throws JsonProcessingException {
        Project sendProject = new Project();
        sendProject.setName("ApiTesting2");
        apiRequest.method(ApiMethod.POST)
                .endpoint("/projects")
                .body(new ObjectMapper().writeValueAsString(sendProject));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Project project = apiResponse.getBody(Project.class);

        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(project.getKind(), "project");
        apiResponse.validateBodySchema("schemas/project.json");
    }

    @Test
    public void deleteAProject() {
        apiRequest.method(ApiMethod.DELETE)
                .endpoint("/projects/{projectId}")
                .addPathParam("projectId", "2505570");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

}
