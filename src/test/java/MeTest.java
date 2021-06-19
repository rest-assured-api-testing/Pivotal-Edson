import api.ApiManager;
import api.ApiResponse;
import configuration.Before;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MeTest extends Before {

    @Test(groups = "GetRequest")
    public void getMe() {
        apiRequest.endpoint("me");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

}
