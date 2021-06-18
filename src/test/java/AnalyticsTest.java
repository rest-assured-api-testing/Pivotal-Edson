import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import entities.Analytics;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static configuration.Before.*;

public class AnalyticsTest {

    @BeforeClass
    public void loadRequestSpecification() {
        createRequestSpecification();
    }

    @Test
    public void getAnalyticsOfAIterationsOfAProject() {

        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/iterations/{number}/analytics")
                .addPathParam("projectId", "2504464")
                .addPathParam("number", "1");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Analytics analytics = apiResponse.getBody(Analytics.class);

        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(analytics.getKind(), "analytics");
    }

    @Test
    public void getAnalyticsWithDetailsOfAIterationsOfAProject() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/projects/{projectId}/iterations/{number}/analytics/cycle_time_details")
                .addPathParam("projectId", "2504464")
                .addPathParam("number", "1");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }
}
