import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import configuration.Before;
import entities.Project;
import entities.StoryComment;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StoryCommentsTest extends Before {
    public StoryComment createdStoryComment;

    @BeforeMethod(onlyForGroups = "CreateACommentOfAStory")
    public void beforeCreateACommentOfAStory() throws JsonProcessingException {
        StoryComment storyComment = new StoryComment();
        storyComment.setText("Comment Story Test");
        apiRequest.method(ApiMethod.POST)
                .endpoint("/projects/{projectId}/stories/{storyId}/comments")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString())
                .body(new ObjectMapper().writeValueAsString(storyComment));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        createdStoryComment = apiResponse.getBody(StoryComment.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(createdStoryComment.getText(), "Comment Story Test");
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"PostRequest", "CreateDeleteProject", "CreateAStoryToAProject", "CreateACommentOfAStory"})
    public void createACommentOfAStory() throws JsonProcessingException {
        StoryComment storyComment = new StoryComment();
        storyComment.setText("Comment Story Test");
        apiRequest.method(ApiMethod.POST)
                .endpoint("/projects/{projectId}/stories/{storyId}/comments")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString())
                .body(new ObjectMapper().writeValueAsString(storyComment));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        createdStoryComment = apiResponse.getBody(StoryComment.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(createdStoryComment.getText(), "Comment Story Test");
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"DeleteRequest", "CreateDeleteProject", "CreateAStoryToAProject", "CreateACommentOfAStory", "CreateACommentOfAStory"})
    public void deleteACommentOfAStory() {
        apiRequest.method(ApiMethod.DELETE)
                .endpoint("/projects/{projectId}/stories/{storyId}/comments/{commentId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString())
                .addPathParam("commentId", createdStoryComment.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"PutRequest", "CreateDeleteProject", "CreateAStoryToAProject", "CreateACommentOfAStory", "CreateACommentOfAStory"})
    public void updateACommentOfAStory() throws JsonProcessingException {
        StoryComment storyComment = new StoryComment();
        storyComment.setText("Comment Story Test Updated");
        apiRequest.method(ApiMethod.PUT)
                .endpoint("/projects/{projectId}/stories/{storyId}/comments/{commentId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString())
                .addPathParam("commentId", this.createdStoryComment.getId().toString())
                .body(new ObjectMapper().writeValueAsString(storyComment));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        StoryComment createdStoryComment = apiResponse.getBody(StoryComment.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(createdStoryComment.getText(), "Comment Story Test Updated");
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"PutRequest", "CreateDeleteProject", "CreateAStoryToAProject", "CreateACommentOfAStory", "CreateACommentOfAStory"})
    public void getAllCommentsOfAStory() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/stories/{storyId}/comments")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"PutRequest", "CreateDeleteProject", "CreateAStoryToAProject", "CreateACommentOfAStory", "CreateACommentOfAStory"})
    public void getACommentOfAStory() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/stories/{storyId}/comments/{commentId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("storyId", createdStory.getId().toString())
                .addPathParam("commentId", createdStoryComment.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        StoryComment storyComment = apiResponse.getBody(StoryComment.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(storyComment.getKind(), "comment");
        apiResponse.getResponse().then().log().body();
    }
}
