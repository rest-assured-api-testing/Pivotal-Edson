import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import configuration.Before;
import entities.Epic;
import entities.Project;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EpicTest extends Before {

    @Test(groups = {"PostRequest", "CreateDeleteProject"})
    public void createEpic() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("Epic Test");

        apiRequest.endpoint("/projects/{projectId}/epics")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .body(new ObjectMapper().writeValueAsString(epic));
        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();

    }

    @Test(groups = {"DeleteRequest", "CreateDeleteProject", "CreateEpic"})
    public void deleteEpic() {
        apiRequest.method(ApiMethod.DELETE)
                .endpoint("/projects/{projectId}/epics/{epicId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("epicId", createdEpic.getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"GetRequest", "CreateDeleteProject"})
    public void getAllEpicsOfAProject() {
        apiRequest.endpoint("/projects/{projectId}/epics")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString());
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"GetRequest", "CreateDeleteProject", "CreateEpic"})
    public void getAnEpicOfAProject() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/epics/{epicId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("epicId", createdEpic.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.getResponse().then().log().body();
        Epic epic = apiResponse.getBody(Epic.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(epic.getKind(), "epic");
    }

    @Test(groups = {"PutRequest", "CreateDeleteProject", "CreateEpic"})
    public void updateAnEpicToAProject() throws JsonProcessingException {
        Epic epic = new Epic();
        epic.setName("Epic Updated");
        apiRequest.method(ApiMethod.PUT)
                .endpoint("/projects/{projectId}/epics/{epicId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("epicId", createdEpic.getId().toString())
                .body(new ObjectMapper().writeValueAsString(epic));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Epic createdEpic = apiResponse.getBody(Epic.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(createdEpic.getName(), "Epic Updated");
    }
}
