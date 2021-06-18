import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Membership;
import entities.ProjectMembership;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static configuration.Before.*;

public class MembershipTest {

    @BeforeClass
    public void loadRequestSpecification() {
        createRequestSpecification();
    }

    @Test
    public void getAllMembersOfAProject() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/memberships")
                .addPathParam("projectId", "2504464");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);

        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test
    public void getAMember() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/memberships/{memberId}")
                .addPathParam("projectId", "2504464")
                .addPathParam("memberId", "10933892");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Membership membership = apiResponse.getBody(Membership.class);

        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(membership.getKind(), "project_membership");
        apiResponse.validateBodySchema("schemas/membership.json");
    }

    @Test
    public void addAMemberToAProject() throws JsonProcessingException {
        ProjectMembership projectMembership = new ProjectMembership();
        projectMembership.setEmail("emailtest@gmail.com");
        projectMembership.setRole("member");
        apiRequest.method(ApiMethod.POST)
                .endpoint("/projects/{projectId}/memberships")
                .addPathParam("projectId", "2504464")
                .body(new ObjectMapper().writeValueAsString(projectMembership));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test
    public void updateAMemberOfAProject() throws JsonProcessingException {
        ProjectMembership projectMembership = new ProjectMembership();
        projectMembership.setRole("viewer");
        apiRequest.method(ApiMethod.PUT)
                .endpoint("/projects/{projectId}/memberships/{memberId}")
                .addPathParam("projectId", "2504464")
                .addPathParam("memberId", "10936079")
                .body(new ObjectMapper().writeValueAsString(projectMembership));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test
    public void deleteAMemberOfAProject() {
        apiRequest.method(ApiMethod.DELETE)
                .endpoint("/projects/{projectId}/memberships/{memberId}")
                .addPathParam("projectId", "2504464")
                .addPathParam("memberId", "10936079");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }
}
