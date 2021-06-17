import api.ApiManager;
import api.ApiMethod;
import api.ApiRequest;
import api.ApiResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class Me {
    private ApiRequest apiRequest;

    @BeforeClass
    public void createRequestSpecification() {
        apiRequest = new ApiRequest()
                .baseUri("https://www.pivotaltracker.com/services/v5")
                .addHeader("X-TrackerToken","9f399d45911b218b5d0538cbf4d626ad");
    }

    @Test
    public void getMe() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/me");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

}
