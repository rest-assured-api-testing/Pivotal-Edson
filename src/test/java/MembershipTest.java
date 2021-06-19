import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import configuration.Before;
import entities.Membership;
import entities.Project;
import entities.ProjectMembership;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;


public class MembershipTest extends Before {
    public Membership membership;

    @BeforeMethod(onlyForGroups = "GetMembersOfAProject")
    public void beforeGetAllMembersOfAProject() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/memberships")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);

        List<Membership> memberships = apiResponse.getBodyList(Membership.class);
        membership = memberships.get(memberships.size() - 1);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }


    @Test(groups = {"GetRequest", "CreateDeleteProject"})
    public void getAllMembersOfAProject() {
        apiRequest.endpoint("/projects/{projectId}/memberships")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);

        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"GetRequest", "CreateDeleteProject", "GetMembersOfAProject"})
    public void getAMember() {
        apiRequest.endpoint("/projects/{projectId}/memberships/{memberId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("memberId", membership.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Membership membership = apiResponse.getBody(Membership.class);

        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(membership.getKind(), "project_membership");
    }

    @Test(groups = {"PostRequest", "CreateDeleteProject"})
    public void addAMemberToAProject() throws JsonProcessingException {
        ProjectMembership projectMembership = new ProjectMembership();
        projectMembership.setEmail("emailtest@gmail.com");
        projectMembership.setRole("member");
        apiRequest.endpoint("/projects/{projectId}/memberships")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .body(new ObjectMapper().writeValueAsString(projectMembership));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @BeforeMethod(onlyForGroups = "AddAMemberOfAProject")
    public void BeforeAddAMemberToAProject() throws JsonProcessingException {
        ProjectMembership projectMembership = new ProjectMembership();
        projectMembership.setEmail("beforeemailtest@gmail.com");
        projectMembership.setRole("member");
        apiRequest.method(ApiMethod.POST)
                .endpoint("/projects/{projectId}/memberships")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .body(new ObjectMapper().writeValueAsString(projectMembership));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"PutRequest", "CreateDeleteProject", "AddAMemberOfAProject", "GetMembersOfAProject"})
    public void updateAMemberOfAProject() throws JsonProcessingException {
        ProjectMembership projectMembership = new ProjectMembership();
        projectMembership.setRole("viewer");
        apiRequest.method(ApiMethod.PUT)
                .endpoint("/projects/{projectId}/memberships/{memberId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("memberId", membership.getId().toString())
                .body(new ObjectMapper().writeValueAsString(projectMembership));

        ApiResponse apiResponse = ApiManager.executeWithBody(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"DeleteRequest", "CreateDeleteProject", "AddAMemberOfAProject", "GetMembersOfAProject"})
    public void deleteAMemberOfAProject() {
        apiRequest.method(ApiMethod.DELETE)
                .endpoint("/projects/{projectId}/memberships/{memberId}")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("memberId", membership.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }
}
