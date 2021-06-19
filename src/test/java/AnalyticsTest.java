import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import configuration.Before;
import entities.Analytics;
import entities.Project;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AnalyticsTest extends Before {

    @Test(groups = {"GetRequest", "CreateDeleteProject"})
    public void getAnalyticsOfAIterationsOfAProject() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/iterations/{number}/analytics")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("number", "1");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"GetRequest", "CreateDeleteProject"})
    public void getAnalyticsWithDetailsOfAIterationsOfAProject() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/iterations/{number}/analytics/cycle_time_details")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("number", "1");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"GetRequest", "CreateDeleteProject"})
    public void ItShouldKindAnalytics() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/iterations/{number}/analytics")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("number", "1");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Analytics analytics = apiResponse.getBody(Analytics.class);

        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(analytics.getKind(), "analytics");
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"GetRequest", "CreateDeleteProject"})
    public void ItShouldAnalyticsInZeroForANewProject() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/iterations/{number}/analytics")
                .addPathParam("projectId", apiResponse.getBody(Project.class).getId().toString())
                .addPathParam("number", "1");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Analytics analytics = apiResponse.getBody(Analytics.class);

        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(analytics.getStories_accepted(), 0);
        Assert.assertEquals(analytics.getBugs_created(), 0);
        Assert.assertEquals(analytics.getCycle_time(), 0);
        Assert.assertEquals(analytics.getRejection_rate(), 0);
        apiResponse.getResponse().then().log().body();
    }
}
