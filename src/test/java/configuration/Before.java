package configuration;

import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Account;
import entities.AccountArray;
import entities.Epic;
import entities.Project;
import io.github.cdimascio.dotenv.Dotenv;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Arrays;
import java.util.List;

public class Before {
    public static Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load();
    public ApiRequest apiRequest;
    public ApiResponse apiResponse;
    public Project project;
    public Account account;
    public Epic createdEpic;

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
    public void JustCreateProject() throws JsonProcessingException {
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

    @BeforeMethod(onlyForGroups = "CreateDeleteProject" )
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

    @AfterMethod(onlyForGroups = "CreateDeleteProject" )
    public void deleteAProject() {
        apiRequest.clearPathParam();
        apiRequest.clearQueryParam();
        apiRequest.method(ApiMethod.DELETE)
                .endpoint("/projects/{projectId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @AfterMethod(onlyForGroups = "DeleteProject" )
    public void JustDeleteAProject() {
        apiRequest.clearPathParam();
        apiRequest.clearQueryParam();
        apiRequest.method(ApiMethod.DELETE)
                .endpoint("/projects/{projectId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @BeforeMethod(onlyForGroups = "GetAccounts" )
    public void GetAccounts() {
        apiRequest.clearPathParam();
        apiRequest.clearQueryParam();
        apiRequest.method(ApiMethod.GET)
                .endpoint("/accounts");

        apiResponse = ApiManager.execute(apiRequest);
        apiResponse.getResponse().then().log().body();

        List<Account> accountList = apiResponse.getBodyList(Account.class);
        account = accountList.get(accountList.size()-1);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @BeforeMethod(onlyForGroups = "CreateEpic")
    public void beforeCreateEpic() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("Before Epic Test");
        apiRequest.method(ApiMethod.POST)
                .endpoint("/projects/{projectId}/epics")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .body(new ObjectMapper().writeValueAsString(epic));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        createdEpic = apiResponse.getBody(Epic.class);
        apiResponse.getResponse().then().log().body();
    }
}
