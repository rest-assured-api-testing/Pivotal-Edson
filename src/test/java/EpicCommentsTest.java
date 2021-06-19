import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import configuration.Before;
import entities.EpicComment;
import entities.Project;
import entities.StoryComment;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EpicCommentsTest extends Before {
    EpicComment createdEpicComment;

    @BeforeMethod(onlyForGroups = "CreateACommentOfAnEpic")
    public void BeforeCreateACommentOfAnEpic() throws JsonProcessingException {
        StoryComment storyComment = new StoryComment();
        storyComment.setText("Comment Test");
        apiRequest.method(ApiMethod.POST)
                .endpoint("/projects/{projectId}/epics/{epicId}/comments")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("epicId", createdEpic.getId().toString())
                .body(new ObjectMapper().writeValueAsString(storyComment));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        createdEpicComment = apiResponse.getBody(EpicComment.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(createdEpicComment.getText(), "Comment Test");
    }

    @Test(groups = {"PostRequest", "CreateDeleteProject", "CreateEpic"})
    public void createACommentOfAnEpic() throws JsonProcessingException {
        StoryComment storyComment = new StoryComment();
        storyComment.setText("Comment Test");
        apiRequest.method(ApiMethod.POST)
                .endpoint("/projects/{projectId}/epics/{epicId}/comments")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("epicId", createdEpic.getId().toString())
                .body(new ObjectMapper().writeValueAsString(storyComment));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        createdEpicComment = apiResponse.getBody(EpicComment.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = {"DeleteRequest", "CreateDeleteProject", "CreateEpic", "CreateACommentOfAnEpic"})
    public void deleteACommentOfAnEpicTest() {
        apiRequest.method(ApiMethod.DELETE)
                .endpoint("/projects/{projectId}/epics/{epicId}/comments/{commentId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("epicId", createdEpic.getId().toString())
                .addPathParam("commentId", createdEpicComment.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @Test(groups = {"DeleteRequest", "CreateDeleteProject", "CreateEpic", "CreateACommentOfAnEpic"})
    public void updateACommentOfAnEpicTest() throws JsonProcessingException {
        StoryComment storyComment = new StoryComment();
        storyComment.setText("Comment Updated");
        apiRequest.method(ApiMethod.PUT)
                .endpoint("/projects/{projectId}/epics/{epicId}/comments/{commentId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("epicId", createdEpic.getId().toString())
                .addPathParam("commentId", createdEpicComment.getId().toString())
                .body(new ObjectMapper().writeValueAsString(storyComment));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        EpicComment createdStoryComment = apiResponse.getBody(EpicComment.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = {"GetRequest", "CreateDeleteProject", "CreateEpic", "CreateACommentOfAnEpic"})
    public void getAllCommentsOfAnEpicTest() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/epics/{epicId}/comments")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("epicId", createdEpic.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = {"GetRequest", "CreateDeleteProject", "CreateEpic", "CreateACommentOfAnEpic"})
    public void getACommentOfAnEpicTest() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/epics/{epicId}/comments/{commentId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("epicId", createdEpic.getId().toString())
                .addPathParam("commentId", createdEpicComment.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test(groups = {"PostRequest", "CreateDeleteProject", "CreateEpic"})
    public void ItShouldCreateACommentWithTheNameSpecificByOur() throws JsonProcessingException {
        StoryComment storyComment = new StoryComment();
        storyComment.setText("Comment Test");
        apiRequest.method(ApiMethod.POST)
                .endpoint("/projects/{projectId}/epics/{epicId}/comments")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("epicId", createdEpic.getId().toString())
                .body(new ObjectMapper().writeValueAsString(storyComment));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        createdEpicComment = apiResponse.getBody(EpicComment.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(createdEpicComment.getText(), "Comment Test");
    }

    @Test(groups = {"DeleteRequest", "CreateDeleteProject", "CreateEpic", "CreateACommentOfAnEpic"})
    public void ItShouldFailWhenSendAIncorrectID() {
        apiRequest.method(ApiMethod.DELETE)
                .endpoint("/projects/{projectId}/epics/{epicId}/comments/{commentId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("epicId", createdEpic.getId().toString())
                .addPathParam("commentId", createdEpicComment.getId().toString() + "123");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 400);
    }

    @Test(groups = {"DeleteRequest", "CreateDeleteProject", "CreateEpic", "CreateACommentOfAnEpic"})
    public void ItShouldUpdatedWithTheNewParams() throws JsonProcessingException {
        StoryComment storyComment = new StoryComment();
        storyComment.setText("Comment Updated");
        apiRequest.method(ApiMethod.PUT)
                .endpoint("/projects/{projectId}/epics/{epicId}/comments/{commentId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("epicId", createdEpic.getId().toString())
                .addPathParam("commentId", createdEpicComment.getId().toString())
                .body(new ObjectMapper().writeValueAsString(storyComment));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        EpicComment createdStoryComment = apiResponse.getBody(EpicComment.class);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(createdStoryComment.getText(), "Comment Updated");
    }

    @Test(groups = {"GetRequest", "CreateDeleteProject", "CreateEpic", "CreateACommentOfAnEpic"})
    public void ItShouldFailWithTheURLWrongWithError404() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/epics/{epicId}/commentss")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("epicId", createdEpic.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
    }

    @Test(groups = {"GetRequest", "CreateDeleteProject", "CreateEpic", "CreateACommentOfAnEpic"})
    public void ItShouldACommentKindComment() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/epics/{epicId}/comments/{commentId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("epicId", createdEpic.getId().toString())
                .addPathParam("commentId", createdEpicComment.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.getResponse().then().log().body();
        EpicComment storyComment = apiResponse.getBody(EpicComment.class);

        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(storyComment.getKind(), "comment");
    }
}
