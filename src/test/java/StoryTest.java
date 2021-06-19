import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import configuration.Before;
import entities.Project;
import entities.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StoryTest extends Before {
    public Story createdStory;

    @BeforeMethod(onlyForGroups = "CreateAStoryToAProject")
    public void beforeCreateAStoryToAProject() throws JsonProcessingException {
        Story story = new Story();
        story.setName("Before Story Test");
        apiRequest.method(ApiMethod.POST)
                .endpoint("/projects/{projectId}/stories")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .body(new ObjectMapper().writeValueAsString(story));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        createdStory = apiResponse.getBody(Story.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(createdStory.getName(), "Before Story Test");
    }

    @Test(groups = {"PostRequest", "CreateDeleteProject"})
    public void createAStoryToAProject() throws JsonProcessingException {
        Story story = new Story();
        story.setName("Story Test");
        apiRequest.endpoint("/projects/{projectId}/stories")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .body(new ObjectMapper().writeValueAsString(story));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        createdStory = apiResponse.getBody(Story.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(createdStory.getName(), "Story Test");
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"GetRequest", "CreateDeleteProject", "CreateAStoryToAProject"})
    public void getAllStoriesOfAProjectTest() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/stories")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"GetRequest", "CreateDeleteProject", "CreateAStoryToAProject"})
    public void getAStoryOfAProjectTest() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/stories/{storyId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Story story = apiResponse.getBody(Story.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(story.getKind(), "story");
    }

    @Test(groups = {"PutRequest", "CreateDeleteProject", "CreateAStoryToAProject"})
    public void updateAStoryToAProjectTest() throws JsonProcessingException {
        Story story = new Story();
        story.setName("Story Updated");
        apiRequest.method(ApiMethod.PUT)
                .endpoint("/projects/{projectId}/stories/{storyId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString())
                .body(new ObjectMapper().writeValueAsString(story));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Story createdStory = apiResponse.getBody(Story.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(createdStory.getName(), "Story Updated");
    }

    @Test(groups = {"DeleteRequest", "CreateDeleteProject", "CreateAStoryToAProject"})
    public void deleteAStoryToAProjectTest() {
        apiRequest.method(ApiMethod.DELETE)
                .endpoint("/projects/{projectId}/stories/{storyId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }
}
