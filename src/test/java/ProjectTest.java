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

    @Test(groups = {"GetRequest", "CreateProject", "DeleteProject"})
    public void getAProject() {
        apiRequest.endpoint("/projects/{projectId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Project project = apiResponse.getBody(Project.class);

        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(project.getKind(), "project");
        apiResponse.validateBodySchema("schemas/project.json");
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"GetRequest", "CreateProject", "DeleteProject"})
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
        apiRequest.endpoint("/projects")
                .body(new ObjectMapper().writeValueAsString(sendProject));

        apiResponse = ApiManager.executeWithBody(apiRequest);
        Project project = apiResponse.getBody(Project.class);

        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(project.getKind(), "project");
        apiResponse.validateBodySchema("schemas/project.json");
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"DeleteRequest", "CreateProject"})
    public void deleteAnProject() {
        apiRequest.endpoint("/projects/{projectId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
        apiResponse.getResponse().then().log().body();
    }
}
