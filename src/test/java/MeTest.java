import api.ApiManager;
import api.ApiResponse;
import configuration.Before;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class MeTest extends Before {

    @Test(groups = "GetRequest")
    public void getMe() {
        apiRequest.endpoint("me");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void GetListAllProjects() {
        given()
                .header("X-TrackerToken", "9f399d45911b218b5d0538cbf4d626ad")
                .when()
                .get("https://www.pivotaltracker.com/services/v5/me")
                .then()
                .assertThat()
                .log().body();
    }
}
