import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import configuration.Before;
import entities.Project;
import entities.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StoryTest extends Before {

    @Test(groups = {"PostRequest", "CreateDeleteProject"})
    public void createAStoryToAProject() throws JsonProcessingException {
        Story story = new Story();
        story.setName("Story Test");
        apiRequest.endpoint("/projects/{projectId}/stories")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .body(new ObjectMapper().writeValueAsString(story));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"GetRequest", "CreateDeleteProject", "CreateAStoryToAProject"})
    public void getAllStoriesOfAProject() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/stories")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"GetRequest", "CreateDeleteProject", "CreateAStoryToAProject"})
    public void getAStoryOfAProject() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/stories/{storyId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = {"PutRequest", "CreateDeleteProject", "CreateAStoryToAProject"})
    public void updateAStoryToAProject() throws JsonProcessingException {
        Story story = new Story();
        story.setName("Story Updated");
        apiRequest.method(ApiMethod.PUT)
                .endpoint("/projects/{projectId}/stories/{storyId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString())
                .body(new ObjectMapper().writeValueAsString(story));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = {"DeleteRequest", "CreateDeleteProject", "CreateAStoryToAProject"})
    public void deleteAStoryToAProject() {
        apiRequest.method(ApiMethod.DELETE)
                .endpoint("/projects/{projectId}/stories/{storyId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @Test(groups = {"PostRequest", "CreateDeleteProject"})
    public void ItShouldCreateAStoryWithTheNameSpecified() throws JsonProcessingException {
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
    public void ItShouldGetAStoryKindStory() {
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
    public void ItShouldUpdatedAStoryWithTheNameSpecified() throws JsonProcessingException {
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

    //BUG
    @Test(groups = {"DeleteRequest", "CreateDeleteProject", "CreateAStoryToAProject"})
    public void ItShouldFailDeleteStoryWitheTheStoryIdWrongWithTheCode400() {
        apiRequest.method(ApiMethod.DELETE)
                .endpoint("/projects/{projectId}/stories/{storyId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString() + "123");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 500);
    }
}
