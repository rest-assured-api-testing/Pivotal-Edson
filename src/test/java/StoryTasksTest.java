import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import configuration.Before;
import entities.Project;
import entities.StoryTask;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StoryTasksTest extends Before {
    public StoryTask createdStoryTask;

    @BeforeMethod(onlyForGroups = "CreateAStoryTaskToAProject")
    public void beforeCreateAStoryTaskToAProject() throws JsonProcessingException {
        StoryTask storyTask = new StoryTask();
        storyTask.setDescription("Story Task Test");
        apiRequest.method(ApiMethod.POST)
                .endpoint("/projects/{projectId}/stories/{storyId}/tasks")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString())
                .body(new ObjectMapper().writeValueAsString(storyTask));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        createdStoryTask = apiResponse.getBody(StoryTask.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(createdStoryTask.getDescription(), "Story Task Test");
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"PostRequest", "CreateDeleteProject", "CreateAStoryToAProject"})
    public void createAStoryTaskToAProject() throws JsonProcessingException {
        StoryTask storyTask = new StoryTask();
        storyTask.setDescription("Story Task Test");
        apiRequest.method(ApiMethod.POST)
                .endpoint("/projects/{projectId}/stories/{storyId}/tasks")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString())
                .body(new ObjectMapper().writeValueAsString(storyTask));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"DeleteRequest", "CreateDeleteProject", "CreateAStoryToAProject", "CreateAStoryTaskToAProject"})
    public void deleteAStoryTaskToAProject() {
        apiRequest.method(ApiMethod.DELETE)
                .endpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString())
                .addPathParam("taskId", createdStoryTask.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"UpdateRequest", "CreateDeleteProject", "CreateAStoryToAProject", "CreateAStoryTaskToAProject"})
    public void updateAStoryTaskToAProject() throws JsonProcessingException {
        StoryTask storyTask = new StoryTask();
        storyTask.setDescription("Story Task Test Updated");
        apiRequest.method(ApiMethod.PUT)
                .endpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString())
                .addPathParam("taskId", createdStoryTask.getId().toString())
                .body(new ObjectMapper().writeValueAsString(storyTask));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"UpdateRequest", "CreateDeleteProject", "CreateAStoryToAProject", "CreateAStoryTaskToAProject"})
    public void getAllStoryTasksOfAProject() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/stories/{storyId}/tasks")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"UpdateRequest", "CreateDeleteProject", "CreateAStoryToAProject", "CreateAStoryTaskToAProject"})
    public void getAStoryTaskOfAProject() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString())
                .addPathParam("taskId", createdStoryTask.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"PostRequest", "CreateDeleteProject", "CreateAStoryToAProject"})
    public void ItShouldCreateAStoryWithTheDescriptionSpecified() throws JsonProcessingException {
        StoryTask storyTask = new StoryTask();
        storyTask.setDescription("Story Task Test");
        apiRequest.method(ApiMethod.POST)
                .endpoint("/projects/{projectId}/stories/{storyId}/tasks")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString())
                .body(new ObjectMapper().writeValueAsString(storyTask));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        createdStoryTask = apiResponse.getBody(StoryTask.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(createdStoryTask.getDescription(), "Story Task Test");
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"DeleteRequest", "CreateDeleteProject", "CreateAStoryToAProject", "CreateAStoryTaskToAProject"})
    public void ItShouldFailDeleteAStoryWithWrongTaskIdWithTheCode400() {
        apiRequest.method(ApiMethod.DELETE)
                .endpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString())
                .addPathParam("taskId", createdStoryTask.getId().toString() + "123");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"UpdateRequest", "CreateDeleteProject", "CreateAStoryToAProject", "CreateAStoryTaskToAProject"})
    public void ItShouldUpdatedStoryWithTheDescriptionSpecified() throws JsonProcessingException {
        StoryTask storyTask = new StoryTask();
        storyTask.setDescription("Story Task Test Updated");
        apiRequest.method(ApiMethod.PUT)
                .endpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString())
                .addPathParam("taskId", createdStoryTask.getId().toString())
                .body(new ObjectMapper().writeValueAsString(storyTask));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        StoryTask createdStoryTask = apiResponse.getBody(StoryTask.class);

        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(createdStoryTask.getDescription(), "Story Task Test Updated");
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"UpdateRequest", "CreateDeleteProject", "CreateAStoryToAProject", "CreateAStoryTaskToAProject"})
    public void ItShouldFailWithALetterOfParamInUpperCase() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/stories/{storyId}/Tasks")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"UpdateRequest", "CreateDeleteProject", "CreateAStoryToAProject", "CreateAStoryTaskToAProject"})
    public void ItShouldGetAStoryKindTask() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/stories/{storyId}/tasks/{taskId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString())
                .addPathParam("taskId", createdStoryTask.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        StoryTask storyTask = apiResponse.getBody(StoryTask.class);

        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(storyTask.getKind(), "task");
        apiResponse.getResponse().then().log().body();
    }
}
