import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import configuration.Before;
import entities.Project;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProjectTest extends Before {

    @Test(groups = "GetRequest")
    public void getProject() {
        apiRequest.endpoint("projects");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"GetRequest", "CreateDeleteProject"})
    public void getAProject() {
        apiRequest.endpoint("/projects/{projectId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"GetRequest", "CreateDeleteProject"})
    public void getPeopleInProject() {
        apiRequest.endpoint("/my/people")
                .addQueryParam("project_id", apiResponse.getBody(Project.class).getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"PostRequest", "DeleteProject"})
    public void createAProject() throws JsonProcessingException {
        Project sendProject = new Project();
        sendProject.setName("ApiTesting2");
        apiRequest.method(ApiMethod.POST)
                .endpoint("/projects")
                .body(new ObjectMapper().writeValueAsString(sendProject));

        apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"DeleteRequest", "CreateProject"})
    public void deleteAnProject() {
        apiRequest.method(ApiMethod.DELETE)
                .endpoint("/projects/{projectId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"GetRequest", "CreateDeleteProject"})
    public void ItShouldGetAProjectKindProject() {
        apiRequest.endpoint("/projects/{projectId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Project project = apiResponse.getBody(Project.class);

        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(project.getKind(), "project");
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"GetRequest", "CreateDeleteProject"})
    public void ItShouldGetAProjectAndValidateTheSchema() {
        apiRequest.endpoint("/projects/{projectId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.validateBodySchema("schemas/project.json");
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"GetRequest", "CreateDeleteProject"})
    public void ItShouldFailByUsePluralInPeopleWithTheCode404() {
        apiRequest.endpoint("/my/peoples")
                .addQueryParam("project_id", apiResponse.getBody(Project.class).getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"PostRequest", "DeleteProject"})
    public void ItShouldCreateAProjectKindProject() throws JsonProcessingException {
        Project sendProject = new Project();
        sendProject.setName("ApiTesting2");
        apiRequest.method(ApiMethod.POST)
                .endpoint("/projects")
                .body(new ObjectMapper().writeValueAsString(sendProject));

        apiResponse = ApiManager.executeWithBody(apiRequest);
        Project project = apiResponse.getBody(Project.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(project.getKind(), "project");
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"PostRequest", "DeleteProject"})
    public void ItShouldCreateAProjectAndValidateSchema() throws JsonProcessingException {
        Project sendProject = new Project();
        sendProject.setName("ApiTesting2");
        apiRequest.method(ApiMethod.POST)
                .endpoint("/projects")
                .body(new ObjectMapper().writeValueAsString(sendProject));

        apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.validateBodySchema("schemas/project.json");
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"DeleteRequest", "CreateProject"})
    public void ItShouldDeleteAProjectWithAddNumbersZeroToLeft() {
        apiRequest.method(ApiMethod.DELETE)
                .endpoint("/projects/{projectId}")
                .addPathParam("projectId", "00" + apiResponse.getBody(Project.class).getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
        apiResponse.getResponse().then().log().body();
    }
}
