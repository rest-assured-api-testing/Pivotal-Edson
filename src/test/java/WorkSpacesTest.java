import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import configuration.Before;
import entities.Project;
import entities.WorkSpaces;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class WorkSpacesTest extends Before {
    public WorkSpaces createdWorkSpaces;

    @BeforeMethod(onlyForGroups = "CreateAWorkspace")
    public void beforeCreateWorkSpace() throws JsonProcessingException {
        WorkSpaces storyComment = new WorkSpaces();
        List<Integer> list = new ArrayList<Integer>();
        list.add(Integer.parseInt(apiResponse.getBody(Project.class).getId().toString()));
        storyComment.setName("WorkSpace Test Before");
        storyComment.setProject_ids(list);
        apiRequest.method(ApiMethod.POST)
                .endpoint("/my/workspaces")
                .body(new ObjectMapper().writeValueAsString(storyComment));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        createdWorkSpaces = apiResponse.getBody(WorkSpaces.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @AfterMethod(onlyForGroups = "DeleteAWorkspace")
    public void beforeDeleteAWorkSpace() {
        apiRequest.method(ApiMethod.DELETE)
                .endpoint("/my/workspaces/{workspace_id}")
                .addPathParam("workspace_id", createdWorkSpaces.getId());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"PostRequest", "CreateDeleteProject", "DeleteAWorkspace"})
    public void createWorkSpace() throws JsonProcessingException {
        WorkSpaces workSpace = new WorkSpaces();
        List<Integer> list = new ArrayList<Integer>();
        list.add(Integer.parseInt(apiResponse.getBody(Project.class).getId().toString()));
        workSpace.setName("WorkSpace Test");
        workSpace.setProject_ids(list);
        apiRequest.method(ApiMethod.POST)
                .endpoint("/my/workspaces")
                .body(new ObjectMapper().writeValueAsString(workSpace));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        createdWorkSpaces = apiResponse.getBody(WorkSpaces.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"GetRequest"})
    public void getAllWorkSpaces() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/my/workspaces");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"GetRequest", "CreateDeleteProject", "CreateAWorkspace", "DeleteAWorkspace"})
    public void getAWorkSpace() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/my/workspaces/{workspace_id}")
                .addPathParam("workspace_id", createdWorkSpaces.getId());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"DeleteRequest", "CreateDeleteProject", "CreateAWorkspace"})
    public void deleteAWorkSpace() {
        apiRequest.method(ApiMethod.DELETE)
                .endpoint("/my/workspaces/{workspace_id}")
                .addPathParam("workspace_id", createdWorkSpaces.getId());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
        apiResponse.getResponse().then().log().body();
    }
}
