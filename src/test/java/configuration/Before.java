package configuration;

import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Project;
import io.github.cdimascio.dotenv.Dotenv;
import org.testng.Assert;
import org.testng.annotations.*;

public class Before {
    public static Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load();
    public ApiRequest apiRequest;
    public ApiResponse apiResponse;
    public Project project;

    @BeforeSuite
    public void createRequestSpecification() {
        apiRequest = new ApiRequest()
                .baseUri(dotenv.get("URI"))
                .addHeader("X-TrackerToken", dotenv.get("TOKEN"));
    }

    @BeforeMethod(onlyForGroups = "GetRequest")
    public void GetRequest() {
        apiRequest.method(ApiMethod.GET);
        apiRequest.clearPathParam();
        apiRequest.clearQueryParam();
    }

    @BeforeMethod(onlyForGroups = "PostRequest")
    public void PostRequest() {
        apiRequest.method(ApiMethod.POST);
        apiRequest.clearPathParam();
        apiRequest.clearQueryParam();
    }

    @BeforeMethod(onlyForGroups = "PutRequest")
    public void PutRequest() {
        apiRequest.method(ApiMethod.PUT);
        apiRequest.clearPathParam();
        apiRequest.clearQueryParam();
    }

    @BeforeMethod(onlyForGroups = "DeleteRequest")
    public void DeleteRequest() {
        apiRequest.method(ApiMethod.DELETE);
        apiRequest.clearPathParam();
        apiRequest.clearQueryParam();
    }

    @BeforeMethod(onlyForGroups = "CreateProject" )
    public void CreateProject() throws JsonProcessingException {
        Project sendProject = new Project();
        sendProject.setName("BeforeApiTesting");
        apiRequest.clearPathParam();
        apiRequest.clearQueryParam();
        apiRequest.method(ApiMethod.POST)
                .endpoint("/projects")
                .body(new ObjectMapper().writeValueAsString(sendProject));
        apiResponse = ApiManager.executeWithBody(apiRequest);
        project = apiResponse.getBody(Project.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @AfterMethod(onlyForGroups = "DeleteProject" )
    public void deleteAProject() {
        apiRequest.clearPathParam();
        apiRequest.clearQueryParam();
        apiRequest.method(ApiMethod.DELETE)
                .endpoint("/projects/{projectId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }
}
