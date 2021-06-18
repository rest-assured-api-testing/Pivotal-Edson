import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static configuration.Before.*;

public class MeTest {

    @BeforeClass
    public void loadRequestSpecification() {
        createRequestSpecification();
    }

    @Test(groups = "GetRequest")
    public void getMe() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("me");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }
}
